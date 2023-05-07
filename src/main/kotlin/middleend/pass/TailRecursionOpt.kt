package middleend.pass

import middleend.basic.*
import middleend.struct.ValNum

object TailRecursionOpt : IRVisitor() {
  private lateinit var valNum: ValNum
  private lateinit var currFunc: Func

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun isTailCall(callInst: CallInst): Boolean {
    val retBlock = currFunc.getReturnBlock()
    val callBlock = callInst.parent
    return callInst.getCallee() == currFunc
        && callBlock.getIndexOfInst(callInst) == callBlock.instList.size - 2
        && (callBlock == retBlock || (callBlock in retBlock.prevBlockSet && retBlock.instList.size <= 2))
  }

  override fun visit(func: Func) {
    valNum = func.valNum
    currFunc = func
    valNum.build()
    val callInsts = currFunc.blockList.flatMap { it.instList }.filterIsInstance<CallInst>().filter { isTailCall(it) }

    // we need to make sure that there are always multiple ways out
    if (callInsts.isNotEmpty() && currFunc.blockList.flatMap { it.instList }.count { it is ReturnInst } != 1) {
      val originEntryBlock = currFunc.getEntryBlock()
      val anchor = BasicBlock(currFunc.mulTable.rename("anchor"), 1)
      currFunc.addBasicBlockAtIndex(0, anchor)
      val preHeader = BasicBlock(currFunc.mulTable.rename("preheader"), 1)
      currFunc.addBasicBlockAtIndex(1, preHeader)

      // complementary basic block for phi instructions
      anchor.addBrInst(BranchInst(preHeader, null, null))

      // add phi instructions for function's arguments
      callInsts.fold(func.argList.map { listOf(it to anchor) }) { acc, callInst ->
        acc.zip(callInst.getArgList()) { list, arg -> list + (arg to callInst.parent) }
      }.forEach { preds ->
        val arg = preds.first().first
        val phiInst = PhiInst(currFunc.ssaTable.rename(arg.name!!), arg.type, preds.toMutableList())
        preHeader.addInst(phiInst)
        arg.substitutedByWhen(phiInst) { it != phiInst }
      }

      // modify those call instructions to branch instructions
      callInsts.forEach { callInst ->
        val callBlock = callInst.parent
        callInst.userList.filterIsInstance<PhiInst>().forEach {
          it.removeValue(callInst)
          if (it.getSize() == 1) {
            it.parent.replaceInst(it, MvInst(currFunc.ssaTable.rename(it.name!!), it.getValueList().first()))
          }
        }
        callInst.eliminate()
        callBlock.instList.remove(callInst)
        callBlock.replaceBrInst(BranchInst(preHeader, null, null))
      }

      // don't forget to add terminator to pre-header
      preHeader.addBrInst(BranchInst(originEntryBlock, null, null))
    }
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