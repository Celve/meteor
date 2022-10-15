package backend.basic

import backend.controller.ASMVisitor
import middleend.basic.BasicBlock

class ASMBlock(val block: BasicBlock) : Label("${block.parent!!.name}.${block.name!!}") {
  var parent: ASMFunc? = null
  val instList = mutableListOf<ASMInst>()

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}