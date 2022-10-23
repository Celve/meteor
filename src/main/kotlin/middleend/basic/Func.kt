package middleend.basic

import middleend.helper.ValueSymbolTable
import middleend.pass.IRVisitor

class Func(name: String, val funcType: FuncType, val argList: List<Value>) : GlobalValue(name, funcType) {
  val blockList: MutableList<BasicBlock> = mutableListOf()
  val vst = ValueSymbolTable()

  fun addBasicBlockAtIndex(index: Int, basicBlock: BasicBlock) {
    blockList.add(index, basicBlock)
  }

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}
