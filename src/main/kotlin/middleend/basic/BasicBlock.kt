package middleend.basic

import middleend.pass.IRVisitor

/**
 * BasicBlock is used for holding instruction in LLVM IR.
 * A BasicBlock is a sequence of instructions, terminated by a terminator instruction.
 */
class BasicBlock(name: String, var execFreq: Int) : Value(TypeFactory.getLabelType(name), name) {
  lateinit var parent: Func
  var instList: MutableList<Instruction> = mutableListOf()
  val prevBlockSet = hashSetOf<BasicBlock>()
  val nextBlockSet = hashSetOf<BasicBlock>()

  override fun duplicate(): Value {
    return BasicBlock(name!!, execFreq)
  }

  override fun replicate(): Value {
    val newBlock = BasicBlock(name!!, execFreq)
    instList.forEach {
      val newInst = it.replicate() as Instruction
      newInst.parent = newBlock
      newBlock.instList.add(newInst)
      if (newInst is BranchInst) {
        newBlock.addNextBlock(newInst.getTrueBlock())
        newInst.getFalseBlock()?.let {
          newBlock.addNextBlock(it)
        }
      }
    }
    return newBlock
  }

//  override fun substitutedBy(value: Value) {
//    value.userList.addAll(userList)
//    for (user in userList) {
//      if (user is BranchInst) user.cutBlocks()
//      user.useeList.replaceAll { if (it === this) value else it }
//      if (user is BranchInst) user.linkBlocks()
//    }
//    userList.clear()
//  }
//
//  override fun substitutedByExcept(value: Value, except: (User) -> Boolean) {
//    for (user in userList) {
//      if (!except(user)) {
//        value.userList.add(user)
//        if (user is BranchInst) user.cutBlocks()
//        user.useeList.replaceAll { if (it === this) value else it }
//        if (user is BranchInst) user.linkBlocks()
//      }
//    }
//    userList = userList.filter { except(it) }.toMutableList()
//  }
//
//  override fun substitutedByExcept(value: Value, except: Set<User>) {
//    for (user in userList) {
//      if (!except.contains(user)) {
//        value.userList.add(user)
//        if (user is BranchInst) user.cutBlocks()
//        user.useeList.replaceAll { if (it === this) value else it }
//        if (user is BranchInst) user.linkBlocks()
//      }
//    }
//    userList = except.toMutableList()
//  }
//
//  override fun substitutedByWhen(value: Value, cond: (User) -> Boolean) {
//    for (user in userList) {
//      if (cond(user)) {
//        value.userList.add(user)
//        if (user is BranchInst) user.cutBlocks()
//        user.useeList.replaceAll { if (it === this) value else it }
//        if (user is BranchInst) user.linkBlocks()
//      }
//    }
//    userList = userList.filter { !cond(it) }.toMutableList()
//  }

  fun hasTerminator(): Boolean {
    return instList.isNotEmpty() && instList.last().isTerminator()
  }

  fun getTerminator(): Instruction {
    return instList.last()
  }

  fun getIndexOfInst(inst: Instruction): Int {
    return instList.indexOf(inst)
  }

  fun addInst(inst: Instruction) {
    if (hasTerminator()) {
      throw Exception("basicblock has been terminated")
    }
    inst.parent = this
    instList.add(inst)
  }

  fun addInst(index: Int, inst: Instruction) {
    if (hasTerminator() && index >= instList.size) {
      throw Exception("basicblock has been terminated")
    }
    inst.parent = this
    instList.add(index, inst)
  }

  fun removeInst(inst: Instruction, sub: Value? = null) {
    assert(inst.name == null || sub != null)
    if (sub != null) {
      inst.substitutedBy(sub)
    }
    inst.eliminate()
    instList.remove(inst)
  }

  private fun cutEdge(from: BasicBlock, to: BasicBlock) {
    from.nextBlockSet.remove(to)
    to.prevBlockSet.remove(from)
  }

  private fun linkEdge(from: BasicBlock, to: BasicBlock) {
    from.nextBlockSet.add(to)
    to.prevBlockSet.add(from)
  }

  fun removeBrInst(inst: BranchInst, sub: Value? = null) {
    cutEdge(this, inst.getTrueBlock())
    val falseBlock = inst.getFalseBlock()
    if (falseBlock != null) {
      cutEdge(this, falseBlock)
    }
    removeInst(inst, sub)
  }

  fun replaceInst(oldInst: Instruction, newInst: Instruction) {
    val index = instList.indexOf(oldInst)
    if (index == -1) {
      throw Exception("cannot find instruction in basic block")
    }
    oldInst.eliminate()
    oldInst.substitutedBy(newInst)
    instList[index] = newInst
    newInst.parent = this
  }

  fun replaceBrInst(newInst: BranchInst) {
    val oldInst = getTerminator() as BranchInst
    cutEdge(this, oldInst.getTrueBlock())
    val oldFalseBlock = oldInst.getFalseBlock()
    if (oldFalseBlock != null) {
      cutEdge(this, oldFalseBlock)
    }
    linkEdge(this, newInst.getTrueBlock())
    val newFalseBlock = newInst.getFalseBlock()
    if (newFalseBlock != null) {
      linkEdge(this, newFalseBlock)
    }
    replaceInst(oldInst, newInst)
  }

  fun getIndexInFunc(): Int {
    for ((index, block) in parent.blockList.withIndex()) {
      if (block === this) {
        return index
      }
    }
    throw Exception("basic block cannot find itself in its parent function")
  }

  fun getLastAllocaInstIndex(): Int {
    for ((index, inst) in instList.withIndex()) {
      if (inst !is AllocaInst) {
        return index - 1
      }
    }
    return instList.size - 1
  }

  fun insertAtTheTailOf(func: Func) {
    func.addBasicBlockAtIndex(func.blockList.size, this)
    parent = func
  }

  fun insertAtTheHeadOf(func: Func) {
    func.addBasicBlockAtIndex(0, this)
    parent = func
  }

  fun insertAtIndex(func: Func, index: Int) {
    func.addBasicBlockAtIndex(index, this)
    parent = func
  }

  fun addPrevBlock(prevBlock: BasicBlock) {
    prevBlockSet.add(prevBlock)
  }

  fun removePrevBlock(prevBlock: BasicBlock) {
    prevBlockSet.remove(prevBlock)
  }

  fun addNextBlock(nextBlock: BasicBlock) {
    nextBlockSet.add(nextBlock)
  }

  fun removeNextBlock(nextBlock: BasicBlock) {
    nextBlockSet.remove(nextBlock)
  }

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }

  companion object {
    fun link(src: BasicBlock, dst: BasicBlock) {
      src.addNextBlock(dst)
      dst.addPrevBlock(src)
    }

    fun cut(src: BasicBlock, dst: BasicBlock) {
      src.removeNextBlock(dst)
      dst.removePrevBlock(src)
    }

    fun insert(src: BasicBlock, dst: BasicBlock, slice: Slice) {
      val srcBrInst = src.getTerminator() as BranchInst
      srcBrInst.replace(dst, slice.blockList.first())

      val exitingBlocks = slice.getExitingBlocks()
      if (exitingBlocks.size != 1) {
        throw Exception("slice has more than one exiting block")
      }
      val ebBrInst = exitingBlocks.first().getTerminator() as BranchInst
      val outsideBlocks = ebBrInst.useeList.filterIsInstance<BasicBlock>().filter { it !in slice.blockList }
      if (outsideBlocks.size != 1) {
        throw Exception("exiting block has more than one outside block")
      }
      ebBrInst.replace(outsideBlocks.first(), dst)
    }
  }
}
