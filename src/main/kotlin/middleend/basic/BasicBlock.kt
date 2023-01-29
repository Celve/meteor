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

  fun hasTerminator(): Boolean {
    return instList.isNotEmpty() && instList.last().isTerminator()
  }

  fun getTerminator(): Instruction {
    return instList.last()
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
    val block = inst.parent
    cutEdge(block, inst.getTrueBlock())
    val falseBlock = inst.getFalseBlock()
    if (falseBlock != null) {
      cutEdge(block, falseBlock)
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

  fun replaceBrInst(oldInst: BranchInst, newInst: BranchInst) {
    val block = oldInst.parent
    cutEdge(block, oldInst.getTrueBlock())
    val oldFalseBlock = oldInst.getFalseBlock()
    if (oldFalseBlock != null) {
      cutEdge(block, oldFalseBlock)
    }
    linkEdge(block, newInst.getTrueBlock())
    val newFalseBlock = newInst.getFalseBlock()
    if (newFalseBlock != null) {
      linkEdge(block, newFalseBlock)
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

    fun unlink(src: BasicBlock, dst: BasicBlock) {
      src.removeNextBlock(dst)
      dst.removePrevBlock(src)
    }
  }
}
