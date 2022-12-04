package middleend.basic

import middleend.helper.ValueSymbolTable
import middleend.pass.IRVisitor

class Func(name: String, val funcType: FuncType, val argList: List<Value>) : GlobalValue(name, funcType) {
  var blockList: MutableList<BasicBlock> = mutableListOf()
  val vst = ValueSymbolTable()

  fun getEntryBlock(): BasicBlock {
    return blockList.first()
  }

  fun addBasicBlockAtIndex(index: Int, basicBlock: BasicBlock) {
    blockList.add(index, basicBlock)
  }

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}
