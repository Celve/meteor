package backend.basic

import backend.controller.ASMVisitor
import middleend.basic.Value

class ASMFunc(name: String) : Label(name) {
  val value2Reg = hashMapOf<Value, Register>()
  val reg2Value = hashMapOf<Register, Value>()
  val blockList = mutableListOf<ASMBlock>()
  var usedVirRegNum = 0
  var stackAlloca = 0

  fun getBlockByPureName(pureName: String): ASMBlock? {
    val compositeName = "${name}.$pureName"
    return getBlockByCompositeName(compositeName)
  }

  fun getBlockByCompositeName(compositeName: String): ASMBlock? {
    return blockList.find { it.name == compositeName }
  }

  fun addBlock(block: ASMBlock) {
    blockList.add(block)
  }

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}