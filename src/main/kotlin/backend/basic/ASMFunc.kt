package backend.basic

import backend.controller.ASMVisitor
import middleend.basic.Func
import middleend.basic.Value

class ASMFunc(func: Func) : Label(func.name!!) {
  val value2Reg = hashMapOf<Value, Register>()
  val reg2Value = hashMapOf<Register, Value>()
  val blockList =
    func.blockList.map { ASMBlock(it) } + (if (func.returnBlock == null) listOf() else listOf(ASMBlock(func.returnBlock!!)))
  var usedVirRegNum = 0

  init {
    blockList.forEach { it.parent = this }
  }

  fun getBlock(blockName: String): ASMBlock? {
    val realBlockName = "${name}.$blockName"
    for (block in blockList) {
      if (block.name == realBlockName) {
        return block
      }
    }
    return null
  }

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}