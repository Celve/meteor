package middleend.basic

import middleend.helper.ValueSymbolTable

class Func(name: String, val funcType: FuncType) : GlobalValue(name, funcType) {
  val blockList: MutableList<BasicBlock> = mutableListOf()
  val vst = ValueSymbolTable()

  fun addBasicBlock(index: Int, basicBlock: BasicBlock) {
    blockList.add(index, basicBlock)
  }

  override fun toString(): String {
    return "define ${funcType.result} @$name() { \n${blockList.joinToString("\n")}}\n"
  }
}
