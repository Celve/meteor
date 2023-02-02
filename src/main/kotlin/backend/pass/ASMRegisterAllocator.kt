package backend.pass

import backend.basic.*
import backend.controller.ASMBuilder
import backend.helper.RegFactory

object ASMRegisterAllocator : ASMVisitor() {
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

  /** nodes that existed in the function */
  val initial = hashSetOf<Register>()

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
  val adjList = hashMapOf<Register, HashSet<Register>>().withDefault { hashSetOf() }

  /** degree of node u, the degree of pre-colored nodes should be considered as infinity  */
  val degree = hashMapOf<Register, Int>().withDefault { if (it is PhyReg) Int.MAX_VALUE else 0 }

  /** all mv insts that relates to one register */
  val moveList = hashMapOf<Register, MutableList<ASMMvInst>>().withDefault { mutableListOf() }

  /** alias for a merged node v, alias(v) = u*/
  val alias = hashMapOf<Register, Register>().withDefault { it }

  /** the color selected for a register*/
  val color = hashMapOf<Register, Int>().withDefault { if (it is PhyReg) it.id else -1 }

  val rewrittenNodes = hashSetOf<Register>()

  val crossCallNodes = hashMapOf<Register, Int>().withDefault { 0 }

  val executionFrequency = hashMapOf<Register, Double>().withDefault { 0.0 }

  var asmModule = ASMModule() // it would be updated in visit(module)
  var asmFunc = ASMFunc("initial") // it would be updated in visit(func)
  var raVirReg: VirReg? = null

  override fun visit(module: ASMModule) {
    asmModule = module

    module.funcList.forEach { it.accept(this) }
  }

  private fun processCalleeSaved() {
    val entryBlock = asmFunc.blockList.first()
    val exitBlock = asmFunc.blockList.last()
    val insertedRegList = RegInfo.calleeSavedRegList.reversed() + 1
    for (reg in insertedRegList) {
      val virReg = RegFactory.newVirReg()
      val phyReg = RegFactory.getPhyReg(reg)
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
        if (inst is ASMLoadInst && inst.getImm() is UndeterminedImmediate) {
          inst.replaceUse(inst.getImm(), (inst.getImm() as UndeterminedImmediate).get())
        } else if (inst is ASMStoreInst && inst.getImm() is UndeterminedImmediate) {
          inst.replaceUse(inst.getImm(), (inst.getImm() as UndeterminedImmediate).get())
        } else if (inst is ASMArithiInst && inst.getImm() is UndeterminedImmediate) {
          inst.replaceUse(inst.getImm(), (inst.getImm() as UndeterminedImmediate).get())
        }
      }
    }
  }

  private fun eliminateUselessMv() {
    for (block in asmFunc.blockList) {
      block.instList.removeAll { it is ASMMvInst && it.getRd() == it.getRs() }
    }
  }

  private fun colorInstructions() {
    for (block in asmFunc.blockList) {
      for (inst in block.instList) {
        val rs = inst.getUseList()
        val rd = inst.getDefSet()
        rs.forEach { inst.replaceUse(it, RegFactory.getPhyReg(color.getValue(it))) }
        rd.forEach { inst.replaceDef(it, RegFactory.getPhyReg(color.getValue(it))) }
      }
    }
  }

  private fun init() {
    initial.clear()
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
//    rewrittenNodes.clear()
    crossCallNodes.clear()
    executionFrequency.clear()
  }

  private fun graphColoring() {
    init()
    livenessAnalysis()
    build()
    makeWorklist()
    do {
      if (simplifyWorklist.isNotEmpty()) {
        simplify()
      } else if (worklistMoves.isNotEmpty()) {
        coalesce()
      } else if (freezeWorklist.isNotEmpty()) {
        freeze()
      } else if (spillWorklist.isNotEmpty()) {
        selectSpill()
      }
    } while (simplifyWorklist.isNotEmpty() || worklistMoves.isNotEmpty() || freezeWorklist.isNotEmpty() || spillWorklist.isNotEmpty())

    // only after colors are all assigned can we know whether there would be a spill or not
    assignColors()

    if (spilledNodes.isNotEmpty()) {
      rewriteProgram()
      graphColoring()
    }
  }

  private fun collectDefAndUse(block: ASMBlock) {
    block.defSet = mutableSetOf()
    block.useSet = mutableSetOf()
    for (inst in block.instList) {
      block.defSet.addAll(inst.getDefSet())
      // the book misinterpreted this line
      // the use is the set of use that are not defined in the block
      block.useSet.addAll(inst.getUseList().subtract(block.defSet))
    }
//    block.useSet.removeAll(block.defSet)
    block.useSet += RegFactory.getPhyReg(0)
  }

  private fun livenessAnalysis() {
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
//    val workList = mutableListOf<ASMBlock>(asmFunc.blockList.last())
    val workList = asmFunc.blockList.toMutableList()
    while (workList.isNotEmpty()) {
      val block = workList.removeFirst()
      val originLiveInSet = block.liveInSet
      val originLiveOutSet = block.liveOutSet
      block.liveOutSet = block.succList.fold(setOf()) { acc, succ -> acc.union(succ.liveInSet) }
      block.liveInSet = block.useSet.union(block.liveOutSet.minus(block.defSet))

      // FIXME: This is time-wasting, maybe I should maintain something like finish set?
      if (originLiveInSet != block.liveInSet || originLiveOutSet != block.liveOutSet) {
        workList.addAll(block.predList)
      }
    }
  }

  private fun build() {
    // from the liveOut of block to deduce the liveOut of inst
    for (block in asmFunc.blockList) {
      val lives = block.liveOutSet.toMutableSet()
      for (inst in block.instList.reversed()) {
        val uses = inst.getUseList()
        val defs = inst.getDefSet()
        if (inst is ASMMvInst) {
          // from mv especially, we ignore the use of rs, to implement coalesce
          lives.removeAll(inst.getUseList().toSet())
          uses.union(defs).forEach { moveList.getOrPut(it) { mutableListOf() }.add(inst) }
          worklistMoves.add(inst)
        }

        with(executionFrequency) {
          uses.forEach { set(it, getValue(it) + block.execFreq) }
          defs.forEach { set(it, getValue(it) + block.execFreq) }
        }

        // connect the two overlapped live ranges only in the latter's definition
        lives.addAll(defs)
        defs.flatMap { first -> lives.map { second -> first to second } }.forEach { addEdge(it.first, it.second) }

        // set up initial
        initial.addAll(defs.filterIsInstance<VirReg>())
        initial.addAll(uses.filterIsInstance<VirReg>())

        lives.removeAll(defs.toSet())
        lives.addAll(uses.toSet())

        // check which nodes are crossCallNode
        if (inst is ASMCallInst) {
          lives.forEach { crossCallNodes[it] = crossCallNodes.getValue(it) + block.execFreq }
        }
      }
    }
  }

  private fun addEdge(lhs: Register, rhs: Register): Boolean {
    return if (!adjSet.contains(Pair(lhs, rhs)) && lhs != rhs) {
      adjSet.add(Pair(lhs, rhs))
      adjSet.add(Pair(rhs, lhs))
      if (lhs !is PhyReg) { // it's useless, because it cannot be simplified, and it can save space
        adjList.getOrPut(lhs) { hashSetOf() }.add(rhs)
        degree[lhs] = degree.getValue(lhs) + 1
      }
      if (rhs !is PhyReg) { // it's useless, and it can save space
        adjList.getOrPut(rhs) { hashSetOf() }.add(lhs)
        degree[rhs] = degree.getValue(rhs) + 1
      }
      lhs !is PhyReg
    } else {
      false
    }
  }

  private fun makeWorklist() {
    // FIXME: please optimize this part, because every function only a subset of RegFactory's generated registers
    for (virReg in initial) {
      if (degree.getValue(virReg) >= k) {
        spillWorklist.add(virReg)
      } else if (moveRelated(virReg)) {
        freezeWorklist.add(virReg)
      } else {
        simplifyWorklist.add(virReg)
      }
    }
  }

  private fun adjacent(n: Register): Set<Register> {
    return adjList.getValue(n).minus(coalescedNodes.union(selectedStack))
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
    val simplified = simplifyWorklist.removeFirstOrNull() ?: return
    selectedStack.add(simplified)
    adjacent(simplified).filter { it !is PhyReg }.forEach { decrementDegree(it) }
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

  private fun enableMoves(regs: Set<Register>) {
    for (reg in regs) {
      for (mv in nodeMoves(reg)) {
        if (activeMoves.contains(mv)) {
          activeMoves.remove(mv)
          worklistMoves.add(mv)
        }
      }
    }
  }

  private fun coalesce() {
    val mv = worklistMoves.removeFirst()
    var u = getAlias(mv.getRs())
    var v = getAlias(mv.getRd()!!)

    if (v is PhyReg) {
      u = v.also { v = u } // swap u, v
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
//    if (alias.contains(reg)) {
//      alias[reg] = getAlias(alias[reg]!!)
//      return alias[reg]!!
//    } else {
//      return reg
//    }
    return if (reg in coalescedNodes) {
      getAlias(alias[reg]!!)
    } else {
      reg
    }
  }

  private fun combine(u: Register, v: Register) {
    if (freezeWorklist.contains(v)) {
      freezeWorklist.remove(v)
    } else { // because v must be related with mv inst
      spillWorklist.remove(v)
    }
    coalescedNodes.add(v)
    alias[v] = u
    moveList.getOrPut(u) { mutableListOf() }.addAll(moveList.getValue(v))
    enableMoves(setOf(v))
    for (neighbor in adjacent(v)) {
      if (addEdge(neighbor, u)) {
        decrementDegree(neighbor)
      }
    }
    if (degree.getValue(u) >= k && u in freezeWorklist) {
      freezeWorklist.remove(u)
      spillWorklist.add(u)
    }
  }

  private fun freeze() {
    val frozen = freezeWorklist.firstOrNull() ?: return // could be optimized
    freezeWorklist.remove(frozen)
    simplifyWorklist.add(frozen)
    freezeMoves(frozen)
  }

  private fun freezeMoves(u: Register) {
    for (mv in nodeMoves(u)) {
      val v = if (getAlias(mv.getRs()) == getAlias(u)) {
        getAlias(mv.getRd()!!)
      } else {
        getAlias(mv.getRs())
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
//    candidates.sortBy { executionFrequency.getValue(it) / degree.getValue(it) }
    return candidates.filter { it is VirReg && it !in rewrittenNodes }
      .minByOrNull { executionFrequency.getValue(it) / degree.getValue(it) }
  }

  private fun selectColorWithHeuristic(reg: Register, candidates: HashSet<Int>): Int {
    val crossCallTimes = crossCallNodes.getValue(reg)
    val relativeMoveList = moveList.getValue(reg)
    val reg2Cost = hashMapOf<Int, Int>()

    candidates.forEach { reg2Cost[it] = 0 }

    relativeMoveList.forEach {
      val rs = getAlias(it.getRs())
      val rd = getAlias(it.getRd()!!)
      if (rs is PhyReg && reg2Cost.contains(rs.id)) {
        reg2Cost[rs.id] = reg2Cost[rs.id]!! - it.parent.execFreq
      }
      if (rd is PhyReg && reg2Cost.contains(rd.id)) {
        reg2Cost[rd.id] = reg2Cost[rd.id]!! - it.parent.execFreq
      }
    }

    val ranking = reg2Cost.toList()
      .map { (reg, cost) ->
        reg to cost + (if (!RegInfo.calleeSavedRegList.contains(reg)) (crossCallTimes - 1) * 2 else 0)
      }
      .sortedBy { it.second }

    return ranking.first().first

//    return if (crossCallNodes.contains(reg)) {
//      candidates.find { it in RegInfo.calleeSavedRegList } ?: candidates.first()
//    } else {
//      candidates.find { it in RegInfo.callerSavedRegList } ?: candidates.first()
//    }
  }

  /**
   * The pre-colored nodes should never be spilled. Because they have been colored, meaning that they cannot be reassigned.
   */
  private fun selectSpill() {
    val spilled = selectSpilledWithHeuristic(spillWorklist) ?: throw Exception("no spilled register")
    spillWorklist.remove(spilled)
    simplifyWorklist.add(spilled) // FIXME: I don't know what it should be added to here
    freezeMoves(spilled)
  }

  private fun assignColors() {
    while (selectedStack.isNotEmpty()) {
      val top = selectedStack.removeLast()
      val okColors = RegInfo.assignableRegList.toHashSet()
      adjList.getValue(top).map { getAlias(it) }.distinct().filter { it is PhyReg || it in coloredNodes }
        .forEach { okColors.remove(color.getValue(it)) } // remove adjacent existed color
      if (okColors.isEmpty()) {
        spilledNodes.add(top)
      } else {
        coloredNodes.add(top)
        color[top] = selectColorWithHeuristic(top, okColors)
      }
    }
    coalescedNodes.forEach { color[it] = color.getValue(getAlias(it)) }
  }

  private fun rewriteProgram() {
    for (spilled in spilledNodes) {
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
          if (inst.getDefSet().contains(spilled) || inst.getUseList().contains(spilled)) {
            val newReg = RegFactory.newVirReg()
            rewrittenNodes.add(newReg)
            if (inst.getUseList().contains(spilled)) { // use
              inst.replaceUse(spilled, newReg)
              val loadInst = ASMLoadInst(4, newReg, offset, RegFactory.getPhyReg("sp"))
              loadInst.parent = block
              iterator.previous()
              iterator.add(loadInst) // it's really tricky
              iterator.next()
//              ASMBuilder.setInsertPointBefore(inst)
//              ASMBuilder.createLoadInst(4, newReg!!, Immediate(offset), RegFactory.getPhyReg("sp"))
            }
            if (inst.getDefSet().contains(spilled)) { // def
              inst.replaceDef(spilled, newReg)
              val storeInst = ASMStoreInst(4, newReg, offset, RegFactory.getPhyReg("sp"))
              storeInst.parent = block
              iterator.add(storeInst) // it's really tricky
//              ASMBuilder.setInsertPointAfter(inst)
//              ASMBuilder.createStoreInst(4, newReg!!, Immediate(offset), RegFactory.getPhyReg("sp"))
            }
          }
        }
      }
    }
  }

  override fun visit(globalPtr: ASMGlobalPointer) {
    TODO("Not yet implemented")
  }

  override fun visit(func: ASMFunc) {
    asmFunc = func
    processCalleeSaved()
    graphColoring()
    colorInstructions()
    eliminateUselessMv()
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

  override fun visit(inst: ASMBrInst) {
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

  override fun visit(inst: ASMLuiInst) {
    TODO("Not yet implemented")
  }
}