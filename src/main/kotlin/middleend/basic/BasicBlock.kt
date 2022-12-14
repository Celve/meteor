package middleend.basic

import middleend.pass.IRVisitor

/**
 * BasicBlock is used for holding instruction in LLVM IR.
 * A BasicBlock is a sequence of instructions, terminated by a terminator instruction.
 */
class BasicBlock(name: String, val execFreq: Int) : Value(TypeFactory.getLabelType(name), name) {
  var parent: Func? = null
  var instList: MutableList<Instruction> = mutableListOf()
  val prevBlockList = mutableListOf<BasicBlock>()
  val nextBlockList = mutableListOf<BasicBlock>()

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
    instList.add(index, inst)
  }

  fun replaceInst(oldInst: Instruction, newInst: Instruction) {
    val index = instList.indexOf(oldInst)
    if (index == -1) {
      throw Exception("cannot find instruction in basic block")
    }
    oldInst.eliminate()
    oldInst.substituteAll(newInst)
    instList[index] = newInst
    newInst.parent = this
  }

  fun getIndexInFunc(): Int {
    for ((index, block) in parent!!.blockList.withIndex()) {
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
    prevBlockList.add(prevBlock)
  }

  fun removePrevBlock(prevBlock: BasicBlock) {
    prevBlockList.remove(prevBlock)
  }

  fun addNextBlock(nextBlock: BasicBlock) {
    nextBlockList.add(nextBlock)
  }

  fun removeNextBlock(nextBlock: BasicBlock) {
    nextBlockList.remove(nextBlock)
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
