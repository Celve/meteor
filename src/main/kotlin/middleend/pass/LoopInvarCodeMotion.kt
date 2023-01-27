package middleend.pass

import middleend.basic.*
import middleend.struct.EqSet
import middleend.struct.Loop
import middleend.struct.ValNum

object LoopInvarCodeMotion : IRVisitor() {
  private lateinit var eqSet: EqSet
  private lateinit var valNum: ValNum
  private lateinit var currFunc: Func
  private lateinit var module: TopModule

  override fun visit(topModule: TopModule) {
    module = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun collectInvariants(loop: Loop): List<Instruction> {
    var invariants = listOf<Instruction>()
    val instList = loop.allBlocks.flatMap { it.instList }
    val defSet = instList.toSet()
    val modifiedSet = instList.filterIsInstance<StoreInst>().flatMap { eqSet.get(it.getAddr()) }
    val noCall = !instList.any { it is CallInst && !module.builtinFuncMap.contains(it.getCallee().name) }
    var lastSize = 0
    while (true) {
      invariants =
        instList.filter { it is BinaryInst || it is CmpInst || (it is LoadInst && noCall) || it is GetElementPtrInst }
          .filter { inst -> inst.useeList.all { it is Constant || invariants.contains(it) || !defSet.contains(it) } }
          .filter { it !is LoadInst || !modifiedSet.contains(valNum.get(it.getAddr())) }
      if (invariants.size != lastSize) {
        lastSize = invariants.size
      } else {
        break
      }
    }
    return invariants
  }

  private fun moveInvariants(loop: Loop) {
    loop.succLoops.forEach { moveInvariants(it) }

    val invariants = collectInvariants(loop)
    val doms = currFunc.domTree.doms
    val exitsDom = loop.exitingBlocks.map { doms.getValue(it).toSet() }.reduce { lhs, rhs -> lhs.intersect(rhs) }
    val movable = invariants.filter { exitsDom.contains(it.parent) }

    val header = loop.headerBlock
    val prevBlockSet = header.prevBlockSet.filter { !loop.allBlocks.contains(it) }
    if (movable.isNotEmpty() && prevBlockSet.size == 1) {
      // CFG
      val preHeader = BasicBlock(currFunc.mulTable.rename("preheader"), loop.headerBlock.execFreq)
      val preLoop = prevBlockSet.first()
      preHeader.parent = currFunc
      preLoop.removeNextBlock(header)
      preLoop.addNextBlock(preHeader)
      preHeader.addPrevBlock(preLoop)

      // branch inst
      val terminator = preLoop.getTerminator() as BranchInst
      if (terminator.getTrueBlock() == header) {
        terminator.setTrueBlock(preHeader)
      } else {
        terminator.setFalseBlock(preHeader)
      }
      header.removePrevBlock(preLoop)
      header.addPrevBlock(preHeader)
      preHeader.addNextBlock(header)

      // phi inst
      header.instList.filterIsInstance<PhiInst>().forEach {
        val (value, _) = it.getPred(preLoop)!!
        it.removePred(preLoop)
        it.addAssignment(value, preHeader)
      }

      currFunc.blockList.add(currFunc.blockList.indexOf(loop.headerBlock), preHeader)

      movable.forEach {
        it.parent.instList.remove(it)
        it.parent = preHeader
        preHeader.instList.add(it)
      }
      val branchInst = BranchInst(header, null, null)
      branchInst.parent = preHeader
      preHeader.instList.add(branchInst)
      loop.preHeaderBlock = preHeader
    }
  }

  override fun visit(func: Func) {
    currFunc = func
    valNum = func.valNum
    eqSet = func.eqSet
    eqSet.build()
    func.loopNestTree.build()
    func.loopNestTree.roots.forEach { moveInvariants(it) }
  }

  override fun visit(block: BasicBlock) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: AllocaInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CallInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: LoadInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BitCastInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BinaryInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BranchInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: GetElementPtrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ZExtInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: TruncInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: StoreInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CmpInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ReturnInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PCopyInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: MvInst) {
    TODO("Not yet implemented")
  }
}