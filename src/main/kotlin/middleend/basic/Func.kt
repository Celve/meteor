package middleend.basic

import middleend.helper.ValueSymbolTable

class Func(type: FuncType) : GlobalValue(type) {
  val blockList: MutableList<BasicBlock> = mutableListOf()
  val vst = ValueSymbolTable()

  fun addBasicBlock(index: Int, basicBlock: BasicBlock) {
    blockList.add(index, basicBlock)
  }
}
