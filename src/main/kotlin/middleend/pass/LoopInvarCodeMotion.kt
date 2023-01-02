package middleend.pass

import middleend.basic.*
import middleend.struct.Loop

object LoopInvarCodeMotion : IRVisitor() {
  private lateinit var currFunc: Func

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun collectInvariants(loop: Loop): List<Instruction> {
    var invariants = listOf<Instruction>()
    val defs = loop.body.flatMap { it.instList }.toSet()
    val insts = loop.body.flatMap { it.instList }
    var lastSize = 0
    while (true) {
      invariants = insts.filter { it is BinaryInst || it is CmpInst }
        .filter { inst -> inst.useeList.all { it is Constant || invariants.contains(it) || !defs.contains(it) } }
      if (invariants.size != lastSize) {
        lastSize = invariants.size
      } else {
        break
      }
    }
    return invariants
  }

  private fun moveInvariants(loop: Loop) {
    val invariants = collectInvariants(loop)
    val doms = currFunc.domTree.doms
    val exitsDom = loop.exit.map { doms.getValue(it).toSet() }.reduce { lhs, rhs -> lhs.intersect(rhs) }
    val movable = invariants.filter { exitsDom.contains(it.parent) }

    val header = loop.header
    val prevBlockSet = header.prevBlockSet.filter { !loop.body.contains(it) }
    if (movable.isNotEmpty() && prevBlockSet.size == 1) {
      // CFG
      val preHeader = BasicBlock(currFunc.mulTable.rename("preheader"), loop.header.execFreq)
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

      currFunc.blockList.add(currFunc.blockList.indexOf(loop.header), preHeader)

      movable.forEach {
        it.parent.instList.remove(it)
        it.parent = preHeader
        preHeader.instList.add(it)
      }
      val branchInst = BranchInst(header, null, null)
      branchInst.parent = preHeader
      preHeader.instList.add(branchInst)
    }

    loop.succs.forEach { moveInvariants(it) }
  }

  override fun visit(func: Func) {
    currFunc = func
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