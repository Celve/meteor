package middleend.pass

import middleend.basic.*

object Eliminator : IRVisitor() {
  val workList = mutableListOf<Instruction>()
  val markedSet = hashSetOf<Value>()

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun isCritical(inst: Instruction): Boolean {
    return inst is CallInst || inst is StoreInst || inst is ReturnInst
  }

  private fun take(value: Value) {
    val target = if (value is BasicBlock) {
      value.getTerminator() // especially for phi instruction, the branch it's also important
    } else {
      value
    }
    if (target is Instruction && !markedSet.contains(target)) {
      markedSet.add(target)
      workList.add(target)
    }
  }

  private fun mark(func: Func) {
    workList.clear()
    func.blockList.forEach { block -> block.instList.forEach { if (isCritical(it)) take(it) } }

    while (workList.isNotEmpty()) {
      val inst = workList.first()
      workList.removeFirst()
      inst.useeList.forEach { take(it) }
      func.revDomTree.domFrontiers.getValue(inst.parent!!).forEach {
        val terminator = it.getTerminator()
        if (terminator is BranchInst) {
          take(terminator)
        }
      }
    }
  }

  private fun sweep(func: Func) {
    func.blockList.forEach { block ->
      val eliminated = block.instList.filter { !markedSet.contains(it) && it !is BranchInst }
      eliminated.forEach { it.eliminate() }
      block.instList.removeAll(eliminated)

      val terminator = block.getTerminator()
      if (terminator is BranchInst && terminator.getCond() != null && !markedSet.contains(terminator)) {
        block.replaceInst(terminator, BranchInst(func.revDomTree.doms.getValue(block), null, null))
      }
    }
  }

  override fun visit(func: Func) {
    func.revDomTree.build()
    mark(func)
    sweep(func)
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