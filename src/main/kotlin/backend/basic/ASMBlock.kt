package backend.basic

import backend.controller.ASMVisitor

class ASMBlock(val pureName: String, val parent: ASMFunc) : Label("${parent.name}.$pureName") {
  val instList = mutableListOf<ASMInst>()

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}