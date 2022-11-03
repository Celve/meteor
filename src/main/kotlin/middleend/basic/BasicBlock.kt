package middleend.basic

import middleend.pass.IRVisitor

/**
 * BasicBlock is used for holding instruction in LLVM IR.
 * A BasicBlock is a sequence of instructions, terminated by a terminator instruction.
 */
class BasicBlock(name: String, val executionFrequency: Int) : Value(TypeFactory.getLabelType(name), name) {
  var parent: Func? = null
  val instList: MutableList<Instruction> = mutableListOf()
  private var terminated: Boolean = false

  fun hasTerminator(): Boolean {
    return terminated
  }

  fun addInst(index: Int, inst: Instruction) {
    if (terminated && index >= instList.size - 1) {
      return
    }
    instList.add(index, inst)
    if (inst.isTerminator()) {
      terminated = true
    }
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

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}
