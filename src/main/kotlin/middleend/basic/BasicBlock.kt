package middleend.basic

class BasicBlock(name: String) : Value(TypeFactory.getLabelType(name), name) {
  var parent: Func? = null
  val instList: MutableList<Instruction> = mutableListOf()
  private var terminated: Boolean = false

  fun hasTerminator(): Boolean {
    return terminated
  }

  fun addInst(index: Int, inst: Instruction) {
    instList.add(index, inst)
    if (inst.isTerminator()) {
      terminated = true
    }
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
}
