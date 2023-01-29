package middleend.pass

import middleend.basic.*

object Eliminator : IRVisitor() {
  private val workList = mutableListOf<Instruction>()
  private val markedSet = hashSetOf<Value>()

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
    markedSet.clear()
    func.blockList.flatMap { it.instList }.filter { isCritical(it) }.forEach { take(it) }

    while (workList.isNotEmpty()) {
      val inst = workList.first()
      workList.removeFirst()
      inst.useeList.forEach { take(it) }
      func.revDomTree.domFrontiers.getValue(inst.parent).map { it.getTerminator() }.filterIsInstance<BranchInst>()
        .forEach { take(it) }
    }
  }

  private fun sweep(func: Func) {
    func.blockList.forEach { block ->
      val eliminated = block.instList.filter { !markedSet.contains(it) && it !is BranchInst }
      eliminated.forEach { it.eliminate() }
      block.instList.removeAll(eliminated)

      // the branch instruction may never make effect
      val terminator = block.getTerminator()
      if (terminator is BranchInst && !terminator.isJump() && !markedSet.contains(terminator)) {
        val validNextBlock = func.revDomTree.idoms.getValue(block)
        block.replaceBrInst(BranchInst(validNextBlock, null, null))
      }
    }
  }

  private fun checkPhiInst(origin: BasicBlock): Boolean { // avoid phi inst with two different values from the same block
    val phiInstList = origin.userList.filterIsInstance<PhiInst>()
    return phiInstList.all { inst ->
      val originsValue = inst.getPred(inst.getIndex(origin)).first
      !origin.prevBlockSet.any { inst.getPred(it)?.first != originsValue }
    }
  }

  private fun foldRedundantBr(block: BasicBlock): Boolean {
    val terminator = block.getTerminator()
    if (terminator is BranchInst && !terminator.isJump()) { // it's a conditional branch
      val trueBlock = terminator.getTrueBlock()
      if (trueBlock == terminator.getFalseBlock()) { // case 1
        block.replaceBrInst(BranchInst(trueBlock, null, null))
        return true
      }
    }
    return false
  }

  private fun removeEmptyBlock(block: BasicBlock): Boolean {
    val terminator = block.getTerminator()
    val func = block.parent
    if (terminator is BranchInst && terminator.isJump() && block != func.getEntryBlock()) {
      val jumpBlock = terminator.getTrueBlock()
      if (block.instList.size == 1 && checkPhiInst(block)) { // it's empty
        block.userList.filterIsInstance<PhiInst>().forEach { inst ->
          val value = inst.getPred(block)!!.first
          inst.removePred(block)
          block.prevBlockSet.forEach { inst.addAssignment(value, it) }

          // eliminate useless phi instruction
          val firstValue = inst.getPred(0).first
          if (inst.getPredList().size == 1 || inst.getPredList().all { it.first == firstValue }) {
            inst.parent.replaceInst(inst, MvInst(inst.name!!, firstValue))
          }
        }

        block.substitutedByWhen(jumpBlock) { it is BranchInst }
        block.prevBlockSet.forEach {
          it.nextBlockSet.remove(block)
          it.nextBlockSet.add(jumpBlock)
          jumpBlock.addPrevBlock(it)
        }
        block.prevBlockSet.clear()

        jumpBlock.removePrevBlock(block)
        func.blockList.remove(block)

        assert(block.userList.isEmpty())
        return true
      }
    }
    return false
  }

  private fun combineBlocks(block: BasicBlock): Boolean { // it will affect the judgement of return block
    val func = block.parent
    if (block.prevBlockSet.size == 1 && block != func.getReturnBlock()) { // it has only one predecessor
      val prevBlock = block.prevBlockSet.first()
      if (prevBlock.nextBlockSet.size == 1) {
        assert(block.instList.filterIsInstance<PhiInst>().isEmpty())

        // control flow related
        prevBlock.nextBlockSet.clear()
        prevBlock.nextBlockSet.addAll(block.nextBlockSet)
        prevBlock.nextBlockSet.forEach {
          it.removePrevBlock(block)
          it.addPrevBlock(prevBlock)
        }
        block.substitutedBy(prevBlock)
        block.prevBlockSet.clear()
        block.nextBlockSet.clear()
        func.blockList.remove(block)

//        prevBlock.name = func.mulTable.rename("${prevBlock.name}")

        prevBlock.instList.removeLast() // remove branch instruction
        prevBlock.instList.addAll(block.instList)
        prevBlock.instList.forEach { it.parent = prevBlock }

        return true
      }
    }
    return false
  }

  // I can construct a testcase to make this optimization takes effect
  private fun hoistBranch(block: BasicBlock): Boolean {
    val terminator = block.getTerminator()
    if (terminator is BranchInst && terminator.isJump()) {
      val jumpBlock = terminator.getTrueBlock()
      val jumpTerminator = jumpBlock.getTerminator()
      if (jumpBlock.instList.size == 1 && jumpTerminator is BranchInst && !jumpTerminator.isJump()) {
        // the first block ends with a jump while the second block ends with a branch
        val trueBlock = jumpTerminator.getTrueBlock()
        val cond = jumpTerminator.getCond()!!
        val falseBlock = jumpTerminator.getFalseBlock()!!

        block.replaceBrInst(BranchInst(trueBlock, cond, falseBlock))

        trueBlock.instList.filterIsInstance<PhiInst>().forEach {
          val value = it.getPred(jumpBlock)!!.first
          it.addAssignment(value, block)
        }
        falseBlock.instList.filterIsInstance<PhiInst>().forEach {
          val value = it.getPred(jumpBlock)!!.first
          it.addAssignment(value, block)
        }

        return true
      }
    }
    return false
  }

  private fun clean(func: Func) {
    var changed: Boolean
    do {
      changed = false
      func.domTree.build()
      for (block in func.domTree.blockListInPostorder) {
        changed = changed || foldRedundantBr(block) // case 1
        changed = when {
          removeEmptyBlock(block) -> true
          combineBlocks(block) -> true
          hoistBranch(block) -> true
          else -> changed
        }
      }
    } while (changed)
  }

  override fun visit(func: Func) {
    func.revDomTree.build()
    mark(func)
    sweep(func)
    Checker.visit(func)

    clean(func)
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