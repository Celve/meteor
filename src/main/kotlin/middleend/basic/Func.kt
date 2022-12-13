package middleend.basic

import middleend.helper.SSATable
import middleend.helper.ValueTable
import middleend.pass.IRVisitor
import middleend.struct.DomTree

class Func(name: String, val funcType: FuncType, val argList: List<Value>) : GlobalValue(name, funcType) {
  var blockList: MutableList<BasicBlock> = mutableListOf()
  val mulTable = ValueTable()
  val ssaTable = SSATable()
  val domTree = DomTree(this)

  override fun replicate(): Value {
    TODO("Not yet implemented")
  }

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
