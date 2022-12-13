package middleend.pass

import middleend.basic.*

object Checker : IRVisitor() {
  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  override fun visit(func: Func) {
    for (block in func.blockList) {
      assert(block.parent == func)
      block.accept(this)
    }
  }

  override fun visit(block: BasicBlock) {
    block.instList.forEach { inst ->
      assert(inst.parent == block)
      inst.useeList.forEach { assert(it.userList.contains(inst)) }
    }
    for (inst in block.instList) {
      inst.accept(this)
    }
  }

  override fun visit(inst: AllocaInst) { }

  override fun visit(inst: CallInst) { }

  override fun visit(inst: LoadInst) { }

  override fun visit(inst: BitCastInst) { }

  override fun visit(inst: PhiInst) { }

  override fun visit(inst: BinaryInst) { }

  override fun visit(inst: BranchInst) {
    val trueBlock = inst.getTrueBlock()
    val falseBlock = inst.getFalseBlock()
    assert(trueBlock.prevBlockList.contains(inst.parent) && inst.parent!!.nextBlockList.contains(trueBlock))
    if (falseBlock != null) {
      assert(falseBlock.prevBlockList.contains(inst.parent) && inst.parent!!.nextBlockList.contains(falseBlock))
    }
  }

  override fun visit(inst: GetElementPtrInst) {}

  override fun visit(inst: ZExtInst) {}

  override fun visit(inst: TruncInst) {}

  override fun visit(inst: StoreInst) {}

  override fun visit(inst: CmpInst) {}

  override fun visit(inst: ReturnInst) {}

  override fun visit(inst: PCopyInst) {}

  override fun visit(inst: MvInst) {}
}