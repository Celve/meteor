package middleend.basic

import middleend.pass.IRVisitor

class BasicBlock(name: String) : Value(TypeFactory.getLabelType(name), name) {
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
    func.addBasicBlock(func.blockList.size, this)
    parent = func
  }

  fun insertAtTheHeadOf(func: Func) {
    func.addBasicBlock(0, this)
    parent = func
  }

  fun insertAtIndex(func: Func, index: Int) {
    func.addBasicBlock(index, this)
    parent = func
  }

  override fun toString(): String {
//    return instList.foldRight("\n") { left, right -> "\t$left\n\t$right" }
    return "$name:\n${instList.joinToString("\n") { "\t$it" }.plus("\n")}"
  }

  override fun toOperand(): String {
    return "%$name"
  }

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}
