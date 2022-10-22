package backend.basic

import backend.controller.ASMVisitor

class Directive(val op: String, val argList: List<String>)

class ASMGlobalPointer(val name: String) : ASMSymbol() {
  val defDirList = mutableListOf<Directive>()
  val emitDirList = mutableListOf<Directive>()

  fun addDef(directive: Directive) {
    defDirList.add(directive)
  }

  fun addEmit(directive: Directive) {
    emitDirList.add(directive)
  }

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }

  override fun toString(): String {
    return name
  }
}