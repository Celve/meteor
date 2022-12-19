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
    block.prevBlockSet.forEach {
      assert(it.nextBlockSet.contains(block))
      assert(block.parent!!.blockList.contains(it))
    }
    block.nextBlockSet.forEach {
      assert(it.prevBlockSet.contains(block))
      assert(block.parent!!.blockList.contains(it))
    }
    block.instList.forEach { inst ->
      assert(inst.parent == block)
      inst.useeList.forEach { assert(it.userList.contains(inst)) }
      inst.userList.forEach { assert(it.useeList.contains(inst)) }
    }

    val terminator = block.getTerminator()
    if (terminator is BranchInst) {
      if (terminator.isJump()) {
        assert(block.nextBlockSet.size == 1)
      } else {
        assert(block.nextBlockSet.size == 2)
      }
    }

    for (inst in block.instList) {
      inst.accept(this)
    }
  }

  override fun visit(inst: AllocaInst) {}

  override fun visit(inst: CallInst) {}

  override fun visit(inst: LoadInst) {}

  override fun visit(inst: BitCastInst) {}

  override fun visit(inst: PhiInst) {
    val block = inst.parent!!
    val predList = inst.getPredList()
    assert(predList.size == block.prevBlockSet.size)
    assert(predList.size != 1)
    predList.forEach { assert(block.prevBlockSet.contains(it.second)) }
  }

  override fun visit(inst: BinaryInst) {}

  override fun visit(inst: BranchInst) {
    val trueBlock = inst.getTrueBlock()
    val falseBlock = inst.getFalseBlock()
    assert(trueBlock.prevBlockSet.contains(inst.parent) && inst.parent!!.nextBlockSet.contains(trueBlock))
    if (falseBlock != null) {
      assert(falseBlock.prevBlockSet.contains(inst.parent) && inst.parent!!.nextBlockSet.contains(falseBlock))
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