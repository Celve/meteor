package backend.pass

import backend.basic.*
import backend.controller.ASMBuilder
import backend.controller.ASMVisitor
import backend.helper.RegFactory

class ASMRegisterAllocator : ASMVisitor() {
  val k = RegInfo.assignableRegList.size

  /**
   * Every node is a virtual register.
   * In the design, all nodes are divided into three parts: simplifyWorklist, freezeWorklist, spillWorklist.
   * simplifyWorklist is the worklist that contains all nodes that have degree less than k, and the nodes must have nothing to do with mv.
   * freezeWorklist is the worklist that contains all nodes that have degree less than k, and the nodes must relate to at least one mv.
   * spillWorklist is the worklist that contains all nodes that have degree greater than k.
   * The reason to distinguish the three is that they have different rules.
   * simplifyWorklist is especially used for simplifying the graph, while freezeWorklist contains some nodes we can't use for simplifying.
   */

  /** nodes that have degree < k, pre-colored nodes cannot be simplified */
  val simplifyWorklist = mutableListOf<Register>()

  /** nodes that have degree < k and relates with mv inst */
  val freezeWorklist = mutableListOf<Register>()

  /** nodes that have degree > k */
  val spillWorklist = mutableListOf<Register>()

  /**
   * Compared with the worklist, the suffix "nodes" implies that they are actually result, not worklist.
   * One thing we ought to do is to convert nodes in worklist to nodes in nodes, no matter spilled, coalesced, or colored.
   * When we finish this conversion, we have done the jobs in the current stage.
   */

  /** nodes that would be spilled in current round */
  val spilledNodes = hashSetOf<Register>()

  /** nodes that have been coalesced in current round */
  val coalescedNodes = hashSetOf<Register>()

  /** nodes that have been colored successfully in current round */
  val coloredNodes = hashSetOf<Register>()

  /** a stack that holds temporary registers which is currently deleted from graph */
  val selectedStack = ArrayDeque<Register>()

  val rewrittenNodes = hashSetOf<Register>()

  /**
   * The following three are used for coalescing, especially.
   * All moves, also, could be divided into five categories.
   * During the process, one mv might convert into another kind of mv.
   * For example, coalescedMoves contains all moves that is merged in this round, which should be eliminated immediately. They are converted from worklistMoves.
   * And worklistMoves stands for all moves in the round that haven't been processed and probably could be merged. They are converted from activeMoves and from initialization.
   * activeMoves are identified when judging a move in worklistMoves could be merged or not. If it could be merged, it would be added into coalescedMoves, otherwise, it would be added into activeMoves.
   * A merged move might make an activeMove to be prepared to be merged.
   * Therefore, in the coalescing, we are dealing with the transformation of such states.
   */

  /** mv insts that are merged */
  val coalescedMoves = hashSetOf<ASMInst>()

  /** mv insts that rs conflicts with rd */
  val constrainedMoves = hashSetOf<ASMInst>()

  /** mv insts that no longer consider merge */
  val frozenMoves = hashSetOf<ASMInst>()

  /** mv insts that maybe could be merged */
  val worklistMoves = mutableListOf<ASMMvInst>()

  /** mv insts that is ill-prepared to merge */
  val activeMoves = mutableListOf<ASMMvInst>()

  /** the set of all (u, v) */
  val adjSet = hashSetOf<Pair<Register, Register>>()

  /** adjacent list of node u */
  val adjList = hashMapOf<Register, MutableList<Register>>().withDefault { mutableListOf() }

  /** degree of node u, the degree of pre-colored nodes should be considered as infinity  */
  val degree = hashMapOf<Register, Int>().withDefault { if (it is PhyReg) Int.MAX_VALUE else 0 }

  /** all mv insts that relates to one register */
  val moveList = hashMapOf<Register, MutableList<ASMMvInst>>().withDefault { mutableListOf() }

  /** alias for a merged node v, alias(v) = u*/
  val alias = hashMapOf<Register, Register>().withDefault { it }

  /** the color selected for a register*/
  val color = hashMapOf<Register, Int>().withDefault { if (it is PhyReg) it.id else -1 }

  var regFactory = RegFactory() // it's a temporary init value, which would be replaced in visit(module)
  var originVirRegNum = 0 // it would be updated in visit(module)
  var asmModule = ASMModule() // it would be updated in visit(module)
  var asmFunc = ASMFunc("initial") // it would be updated in visit(func)
  var raVirReg: VirReg? = null

  override fun visit(module: ASMModule) {
    regFactory = module.regFactory
    asmModule = module

    module.funcList.forEach { it.accept(this) }
  }

  private fun processCalleeSaved() {
    val entryBlock = asmFunc.blockList.first()
    val exitBlock = asmFunc.blockList.last()
    for (reg in listOf(1) + RegInfo.calleeSavedRegList) {
      val virReg = regFactory.newVirReg()
      val phyReg = regFactory.getPhyReg(reg)
      if (reg == 1) {
        raVirReg = virReg
      }
      ASMBuilder.setInsertPointBefore(entryBlock.instList[1])
      ASMBuilder.createMvInst(virReg, phyReg)
      ASMBuilder.setInsertPointBefore(exitBlock.instList[exitBlock.instList.size - 2])
      ASMBuilder.createMvInst(phyReg, virReg)
    }
  }

  private fun determineOffset() {
    for (block in asmFunc.blockList) {
      for (inst in block.instList) {
        if (inst is ASMLoadInst && inst.imm is UndeterminedImmediate) {
          inst.imm = (inst.imm as UndeterminedImmediate).get()
        } else if (inst is ASMStoreInst && inst.imm is UndeterminedImmediate) {
          inst.imm = (inst.imm as UndeterminedImmediate).get()
        } else if (inst is ASMArithiInst && inst.imm is UndeterminedImmediate) {
          inst.imm = (inst.imm as UndeterminedImmediate).get()
        }
      }
    }
  }

  private fun eliminateUselessMv() {
    for (block in asmFunc.blockList) {
      val removed = block.instList.filter { it is ASMMvInst && it.rd == it.rs }
      block.instList.removeAll(removed)
    }
  }

  private fun colorInstructions() {
//    println("[color]")
//    println(color)
//    println(color.contains(regFactory.getPhyReg("s1")))
    for (block in asmFunc.blockList) {
      for (inst in block.instList) {
        val rs = inst.getRs()
        val rd = inst.getRd()
        rs.forEach { inst.replaceRs(it, regFactory.getPhyReg(color.getValue(it))) }
        rd.forEach { inst.replaceRd(it, regFactory.getPhyReg(color.getValue(it))) }
      }
    }
  }

  private fun initialize() {
    simplifyWorklist.clear()
    freezeWorklist.clear()
    spillWorklist.clear()
    spilledNodes.clear()
    coalescedNodes.clear()
    coloredNodes.clear()
    selectedStack.clear()
    coalescedMoves.clear()
    constrainedMoves.clear()
    frozenMoves.clear()
    worklistMoves.clear()
    activeMoves.clear()
    adjSet.clear()
    adjList.clear()
    degree.clear()
    moveList.clear()
    alias.clear()
    color.clear()
  }

  private fun main() {
    initialize()
    analyzeLiveness()
    build()
    makeWorklist()
    do {
      if (simplifyWorklist.isNotEmpty()) {
        simplify()
      } else if (worklistMoves.isNotEmpty()) {
        coalesce()
      } else {
        if (freezeWorklist.isNotEmpty()) {
          freeze()
        } else if (spillWorklist.isNotEmpty()) {
          selectSpill()
        }
//        if (asmFunc.name == "gcd2") {
//          Thread.sleep(500)
//          println("simplifyWorklist: $simplifyWorklist")
//          println("worklistMoves: $worklistMoves")
//          println("freezeWorklist: $freezeWorklist")
//          println("spillWorklist: $spillWorklist")
//          println()
//        }
      }

    } while (simplifyWorklist.isNotEmpty() || worklistMoves.isNotEmpty() || freezeWorklist.isNotEmpty() || spillWorklist.isNotEmpty())

    // only after colors are all assigned can we know whether there would be a spill or not
    assignColors()

    if (spilledNodes.isNotEmpty()) {
      rewriteProgram()
      main()
    }
  }

  private fun collectDefAndUse(block: ASMBlock) {
    block.defSet = mutableSetOf()
    block.useSet = mutableSetOf()
    for (inst in block.instList) {
      block.defSet.addAll(inst.getRd())
      // the book misinterpreted this line
      // the use is the set of use that are not defined in the block
      block.useSet.addAll(inst.getRs().subtract(block.defSet))
    }
    block.useSet += regFactory.getPhyReg(0)
//    println("$block's defSet: ${block.defSet}")
//    println("$block's useSet: ${block.useSet}")
  }

  private fun analyzeLiveness() {
    // initialization
    // because the function would be rewritten if a spill occurs, which means that we have to rebuild it every single time
    for (block in asmFunc.blockList) {
      block.liveInSet = mutableSetOf()
      block.liveOutSet = mutableSetOf()
      collectDefAndUse(block)
    }

    // in = use + (out - def)
    // out = union of all in of successors
    // repeat until in and out are fixed
    while (true) {
      var unequal = false
      for (block in asmFunc.blockList) {
        val originLiveInSet = block.liveInSet
        val originLiveOutSet = block.liveOutSet
        block.liveInSet = block.useSet.union(block.liveOutSet.minus(block.defSet)).toMutableSet()
        block.liveOutSet =
          block.succList.fold(setOf<Register>()) { acc, succ -> acc.union(succ.liveInSet) }.toMutableSet()

        // FIXME: This is time-wasting, maybe I should maintain something like finish set?
        if (originLiveInSet != block.liveInSet || originLiveOutSet != block.liveOutSet) {
          unequal = true
        }
      }
      if (!unequal) {
        break
      }
    }
//    for (func in asmModule.funcList) {
//    for (block in asmFunc.blockList) {
//      println("$block's liveInSet: ${block.liveInSet}")
//      println("$block's liveOutSet: ${block.liveOutSet}")
//      println("$block's useSet: ${block.useSet}")
//      println("$block's defSet: ${block.defSet}")
//      println("$block's succList: ${block.succList}")
//    }
//    }
  }

  private fun build() {
//    println("[build]")
    // from the liveOut of block to deduce the liveOut of inst
    for (block in asmFunc.blockList) {
//        println("in block $block")
      val lives = block.liveOutSet
      for (inst in block.instList.reversed()) {
        val uses = inst.getRs()
        val defs = inst.getRd()
        if (inst is ASMMvInst) {
          // from mv especially, we ignore the use of rs, to implement coalesce
          lives.removeAll(inst.getRs().toSet())
          uses.forEach { moveList.getOrPut(it) { mutableListOf() }.add(inst) }
          defs.forEach { moveList.getOrPut(it) { mutableListOf() }.add(inst) }
          worklistMoves.add(inst)
        }

        // connect the two overlapped live ranges only in the latter's definition
        lives.addAll(defs)
//          println("$defs $lives")
        for (def in defs) {
          for (live in lives) {
            addEdge(live, def)
          }
        }

        lives.removeAll(defs.toSet())
        lives.addAll(uses.toSet())
      }
    }
//    println()
  }

  private fun addEdge(lhs: Register, rhs: Register) {
    if (!adjSet.contains(Pair(lhs, rhs)) && lhs != rhs) {
//      println("$lhs <-> $rhs")
      adjSet.add(Pair(lhs, rhs))
      adjSet.add(Pair(rhs, lhs))
      if (lhs !is PhyReg) { // it's useless, because it cannot be simplified, and it can save space
        adjList.getOrPut(lhs) { mutableListOf() }.add(rhs)
        degree[lhs] = degree.getValue(lhs) + 1
      }
      if (rhs !is PhyReg) { // it's useless, and it can save space
        adjList.getOrPut(rhs) { mutableListOf() }.add(lhs)
        degree[rhs] = degree.getValue(rhs) + 1
      }
    }
  }

  private fun makeWorklist() {
    // FIXME: please optimize this part, because every function only a subset of regFactory's generated registers
    for (virReg in (0 until regFactory.virRegId).map { VirReg(it) }) {
      if (degree.getValue(virReg) >= k) {
        spillWorklist.add(virReg)
      } else if (moveRelated(virReg)) {
        freezeWorklist.add(virReg)
      } else {
        simplifyWorklist.add(virReg)
      }
    }
//    println("[makeWorklist]")
//    println("spillWorklist: ${spillWorklist.map { "$it(${degree.getValue(it)})" }}")
//    println("freezeWorklist: ${freezeWorklist.map { "$it(${degree.getValue(it)})" }}")
//    println("simplifyWorklist: ${simplifyWorklist.map { "$it(${degree.getValue(it)})" }}")
//    val asmEmit = ASMEmit()
//    asmEmit.visit(asmModule)
  }

  private fun adjacent(n: Register): List<Register> {
    return adjList.getValue(n).minus(selectedStack.union(coalescedNodes))
  }

  private fun nodeMoves(n: Register): List<ASMMvInst> {
    // The result is not moveList merely, because in the process,
    // moves in coalescedMoves would be eliminated,
    // moves in frozenMoves should not be considered as moves,
    // and moves in constrainedMoves can never be in coalescedMoves.
    // Therefore, the only available moves are moves in activesMoves and worklistMoves, where the two doesn't intersect.
    return moveList.getValue(n).intersect(activeMoves.union(worklistMoves)).toList()
  }

  private fun moveRelated(n: Register): Boolean {
    return nodeMoves(n).isNotEmpty()
  }

  /**
   * The pre-colored nodes should never be simplified. Because it's meaningless.
   */
  private fun simplify() {
//    println("[simplify]")
    val simplified = simplifyWorklist.firstOrNull() ?: return
//    println("$simplified is selected to be removed")
    simplifyWorklist.remove(simplified)
    selectedStack.add(simplified)
    for (related in adjacent(simplified)) {
      decrementDegree(related)
    }
  }

  private fun decrementDegree(reg: Register) {
    val d = degree.getValue(reg)
    degree[reg] = d - 1
    if (d == k) {
      enableMoves(adjacent(reg) + reg)
      spillWorklist.remove(reg)
      if (moveRelated(reg)) {
        freezeWorklist.add(reg)
      } else {
        simplifyWorklist.add(reg)
      }
    }
  }

  private fun enableMoves(regs: List<Register>) {
    for (reg in regs) {
      for (mv in nodeMoves(reg)) {
        if (activeMoves.contains(mv)) {
          activeMoves.remove(mv)
          worklistMoves.add(mv)
        }
      }
    }
  }

  private fun eliminateMv(mv: ASMMvInst, origin: Register, target: Register) {
    val mvBlock = mv.parent!!
    val func = mvBlock.parent
    for (block in func.blockList) {
      val removed = mutableListOf<ASMMvInst>()
      for (inst in block.instList) {
        val rs = inst.getRs()
        val rd = inst.getRd()
        if (rs.any { it == origin }) {
          inst.replaceRs(origin, target)
        }
        if (rd.any { it == origin }) {
          inst.replaceRd(origin, target)
        }
        if (inst is ASMMvInst && inst.rs == inst.rd) {
          removed.add(inst)
        }
      }
      block.instList.removeAll(removed)
    }
  }

  private fun coalesce() {
    val mv = worklistMoves.first()
//    println("[coalesce] mv ${mv.rd}, ${mv.rs}")
    worklistMoves.remove(mv)
    var u = getAlias(mv.rs)
    var v = getAlias(mv.rd)

    // swap u, v
    if (v is PhyReg) {
      u = v.also { v = u }
    }

    if (u == v) { // I think this case occurs when there are redundant mv
      coalescedMoves.add(mv)
      addWorklist(u)
    } else if (v is PhyReg || adjSet.contains(Pair(u, v))) { // when both of them are pre-colored or they conflicts
      constrainedMoves.add(mv)
      addWorklist(u)
      addWorklist(v)
    } else if ((u is PhyReg && adjacent(v).all { ok(it, u) })
      || (u !is PhyReg && conservative(adjacent(u).union(adjacent(v)).toList()))
    ) {
      coalescedMoves.add(mv)
//      eliminateMv(mv, v, u)
      combine(u, v)
      addWorklist(u)
    } else {
      activeMoves.add(mv)
    }
  }

  private fun addWorklist(reg: Register) {
    if (reg !is PhyReg && !moveRelated(reg) && degree.getValue(reg) < k) {
      freezeWorklist.remove(reg) // in the case that it's the result of coalesce
      simplifyWorklist.add(reg)
    }
  }

  /**
   * This is the test function for George rule in coalesce.
   * According to the George rule, the condition that A and B can combine is:
   * for every neighbor t of A, t should either conflict with B or be a lower-degree node.
   * As for t is a physical register, it doesn't take any effect, which means that it could be discarded.
   */
  private fun ok(neighbor: Register, another: Register): Boolean {
    return degree.getValue(neighbor) < k || neighbor is PhyReg || adjSet.contains(Pair(neighbor, another))
  }

  /**
   * This is a really conservative approach to check whether the coalesced node is ok.
   * By coalesce, the neighbors of coalesced node can only decrease,
   * which means that as long as their degrees before coalesce are < k,
   * their degree after coalesce are < k.
   */
  private fun conservative(neighbors: List<Register>): Boolean {
    return neighbors.count { degree.getValue(it) >= k } < k
  }

  private fun getAlias(reg: Register): Register {
    if (alias.contains(reg)) {
      alias[reg] = getAlias(alias[reg]!!)
      return alias[reg]!!
    } else {
      return reg
    }
  }

  private fun combine(u: Register, v: Register) {
//    println("combine $u and $v")
    if (freezeWorklist.contains(v)) {
      freezeWorklist.remove(v)
    } else { // because v must be related with mv inst
      spillWorklist.remove(v)
    }
    coalescedNodes.add(v)
    alias[v] = u
    moveList.getOrPut(u) { mutableListOf() }.addAll(moveList.getValue(v))
    enableMoves(listOf(v))
    for (neighbor in adjacent(v)) {
      addEdge(neighbor, u)
      decrementDegree(neighbor)
    }
    if (degree.getValue(u) >= k && freezeWorklist.contains(u)) {
      freezeWorklist.remove(u)
      spillWorklist.add(u)
    }
  }

  private fun freeze() {
//    println("[freeze]")
    val frozen = freezeWorklist.firstOrNull() ?: return
    freezeWorklist.remove(frozen)
    simplifyWorklist.add(frozen)
    freezeMoves(frozen)
  }

  private fun freezeMoves(u: Register) {
    for (mv in nodeMoves(u)) {
      val v = if (getAlias(mv.rs) == u) {
        getAlias(mv.rd)
      } else {
        getAlias(mv.rs)
      }
      activeMoves.remove(mv)
      frozenMoves.add(mv)
      if (nodeMoves(v).isEmpty() && degree.getValue(v) < k) { // it's no longer relate to mv, therefore should be removed from freezeWorklist
        simplifyWorklist.add(v)
        freezeWorklist.remove(v)
      }
    }
  }

  private fun selectSpilledWithHeuristic(candidates: MutableList<Register>): Register? {
    // this heuristic is bad, because it doesn't consider context of the register
    return candidates.find { it is VirReg && !rewrittenNodes.contains(it) }
  }

  private fun selectColorWithHeuristic(candidate: HashSet<Int>): Int {
//    val random = Random()
//    return candidate.elementAt(random.nextInt(candidate.size))
    return candidate.find { it in RegInfo.callerSavedRegList }
      ?: candidate.first()

//    return candidate.first()
  }

  /**
   * The pre-colored nodes should never be spilled. Because they have been colored, meaning that they cannot be reassigned.
   */
  private fun selectSpill() {
//    println("[selectSpill]")
    val spilled = selectSpilledWithHeuristic(spillWorklist) ?: throw Exception("no spilled register")
//    println("spilled: $spilled")
    spillWorklist.remove(spilled)
    simplifyWorklist.add(spilled) // FIXME: I don't know what it should be added to here
    freezeMoves(spilled)
  }

  private fun assignColors() {
    while (selectedStack.isNotEmpty()) {
      val top = selectedStack.last()
      selectedStack.removeLast()
      val okColors = RegInfo.assignableRegList.toHashSet()
      for (neighbor in adjList.getValue(top)) {
        val coalesced = getAlias(neighbor)
        if (coalesced is PhyReg || coloredNodes.contains(coalesced)) {
          okColors.remove(color.getValue(coalesced))
        }
      }
      if (okColors.isEmpty()) {
        spilledNodes.add(top)
      } else {
        coloredNodes.add(top)
        color[top] = selectColorWithHeuristic(okColors)
//        println("assign color ${color[top]} to $top")
      }
    }
    coalescedNodes.forEach { color[it] = color.getValue(getAlias(it)) }
  }

  private fun rewriteProgram() {
//    println("[rewrite]")
//    for ((key, value) in color) {
//      println("$key was colored with $value")
//    }
    for (spilled in spilledNodes) {
//      println("$spilled is rewritten")
      rewrittenNodes.add(spilled)
      // allocate memory on stack
      val offset = if (spilled == raVirReg) {
        asmFunc.stackFrame.newReturnAddress()
      } else {
        asmFunc.stackFrame.newSavedRegister()
      }

      // create store for def, load for use
      for (block in asmFunc.blockList) {
        val iterator = block.instList.listIterator()
        while (iterator.hasNext()) {
          val inst = iterator.next()
//            if (inst.getRd().contains(spilled) || inst.getRs().contains(spilled)) {
//              newReg = regFactory.newVirReg()
//            }
          if (inst.getRs().contains(spilled)) { // use
//              inst.replaceRs(spilled, newReg!!)
            val loadInst = ASMLoadInst(4, spilled, offset, regFactory.getPhyReg("sp"), "")
            iterator.previous()
            iterator.add(loadInst) // it's really tricky
            iterator.next()
//              ASMBuilder.setInsertPointBefore(inst)
//              ASMBuilder.createLoadInst(4, newReg!!, Immediate(offset), regFactory.getPhyReg("sp"))
          }
          if (inst.getRd().contains(spilled)) { // def
//              inst.replaceRd(spilled, newReg!!)
            val storeInst = ASMStoreInst(4, spilled, offset, regFactory.getPhyReg("sp"), "")
            iterator.add(storeInst) // it's really tricky
//              ASMBuilder.setInsertPointAfter(inst)
//              ASMBuilder.createStoreInst(4, newReg!!, Immediate(offset), regFactory.getPhyReg("sp"))
          }
        }
      }
    }
//    val emit = ASMEmit()
//    emit.visit(asmFunc)
//    println(adjList.getValue(regFactory.getVirReg(123)!!))
//    Thread.sleep(1000)
  }

  override fun visit(globalPtr: ASMGlobalPointer) {
    TODO("Not yet implemented")
  }

  override fun visit(func: ASMFunc) {
//    println(func)
    asmFunc = func
    processCalleeSaved()
    originVirRegNum = regFactory.virRegId
    main()
    colorInstructions()
    eliminateUselessMv()
//    val firstBlock = func.blockList.first()
//    val lastBlock = func.blockList.last()
//    (firstBlock.instList.first() as ASMArithiInst).imm = DeterminedImmediate(-func.stackAlloca)
//    (lastBlock.instList[lastBlock.instList.size - 2] as ASMArithiInst).imm = DeterminedImmediate(func.stackAlloca)
    determineOffset()
  }

  override fun visit(block: ASMBlock) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMStoreInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLoadInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMBzInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMJInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMRetInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMArithInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMArithiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCallInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMMvInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLaInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpzInst) {
    TODO("Not yet implemented")
  }
}

