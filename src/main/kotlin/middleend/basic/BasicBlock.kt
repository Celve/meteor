package middleend.basic

import middleend.helper.Twine

class BasicBlock(name: Twine) : Value(TypeFactory.createLabel(), name) {
  var parent: Func? = null
  val instList: MutableList<Instruction> = mutableListOf()

  fun addInst(index: Int, inst: Instruction) {
    instList.add(index, inst)
  }

  fun insertAtTail(func: Func) {
    func.addBasicBlock(func.blockList.size, this)
    parent = func
  }

  fun insertAtHead(func: Func) {
    func.addBasicBlock(0, this)
    parent = func
  }

  fun insertAtIndex(func: Func, index: Int) {
    func.addBasicBlock(index, this)
    parent = func
  }

  override fun toString(): String {
    return instList.foldRight("\n") { left, right -> "$left\n$right" }
  }
}
