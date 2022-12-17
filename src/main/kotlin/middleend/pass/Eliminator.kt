package middleend.pass

import middleend.basic.*

object Eliminator : IRVisitor() {
  val workList = mutableListOf<Instruction>()
  val markedSet = hashSetOf<Value>()
  var changed = false

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
        block.replaceInst(terminator, BranchInst(func.revDomTree.idoms.getValue(block), null, null))
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

  private fun foldRedundantBr(block: BasicBlock) {
    var terminator = block.getTerminator()
    if (terminator is BranchInst && !terminator.isJump()) { // it's a conditional branch
      val trueBlock = terminator.getTrueBlock()
      if (trueBlock == terminator.getFalseBlock()) { // case 1
        val newTerminator = BranchInst(trueBlock, null, null)
        block.replaceInst(terminator, newTerminator)
      }
    }
  }

  private fun removeEmptyBlock(block: BasicBlock) {
    val terminator = block.getTerminator()
    val func = block.parent!!
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
            inst.parent!!.replaceInst(inst, MvInst(inst.name!!, firstValue))
          }
        }

        block.substituteOnly(jumpBlock) { it is BranchInst }
        block.prevBlockSet.forEach {
          it.nextBlockSet.remove(block)
          it.nextBlockSet.add(jumpBlock)
          jumpBlock.addPrevBlock(it)
        }
        block.prevBlockSet.clear()

        jumpBlock.removePrevBlock(block)
        func.blockList.remove(block)

        assert(block.userList.isEmpty())
        changed = true
      }
    }
  }

  private fun combineBlocks(block: BasicBlock) { // it will affect the judgement of return block
    val func = block.parent!!
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
        block.substituteAll(prevBlock)
        block.prevBlockSet.clear()
        block.nextBlockSet.clear()
        func.blockList.remove(block)

        prevBlock.name = "${prevBlock.name}$${block.name}"

        prevBlock.instList.removeLast() // remove branch instruction
        prevBlock.instList.addAll(block.instList)
        prevBlock.instList.forEach { it.parent = prevBlock }

        changed = true
      }
    }
  }

  // I can construct a testcase to make this optimization takes effect
  private fun hoistBranch(block: BasicBlock) {
    val terminator = block.getTerminator()
    if (terminator is BranchInst && terminator.isJump()) {
      val jumpBlock = terminator.getTrueBlock()
      val jumpTerminator = jumpBlock.getTerminator()
      if (jumpBlock.instList.size == 1 && jumpTerminator is BranchInst && !jumpTerminator.isJump()) {
        // the first block ends with a jump while the second block ends with a branch
        val trueBlock = jumpTerminator.getTrueBlock()
        val cond = jumpTerminator.getCond()!!
        val falseBlock = jumpTerminator.getFalseBlock()!!

        block.replaceInst(terminator, BranchInst(trueBlock, cond, falseBlock))

        jumpBlock.removePrevBlock(block)
        block.removeNextBlock(jumpBlock)
        block.addNextBlock(trueBlock)
        trueBlock.addPrevBlock(block)
        block.addNextBlock(falseBlock)
        falseBlock.addPrevBlock(block)

        trueBlock.instList.filterIsInstance<PhiInst>().forEach {
          val value = it.getPred(jumpBlock)!!.first
          it.addAssignment(value, block)
        }
        falseBlock.instList.filterIsInstance<PhiInst>().forEach {
          val value = it.getPred(jumpBlock)!!.first
          it.addAssignment(value, block)
        }

        changed = true
      }
    }
  }

  private fun clean(func: Func) {
    do {
      changed = false
      func.domTree.build()
      for (block in func.domTree.blockListInPostorder) {
        foldRedundantBr(block) // case 1
        removeEmptyBlock(block) // case 2
        combineBlocks(block) // case 3
        hoistBranch(block)
      }
    } while (changed)
  }

  override fun visit(func: Func) {
    func.revDomTree.build()
    mark(func)
    sweep(func)

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