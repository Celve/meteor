package backend.basic

import backend.controller.ASMVisitor

class Directive(val op: String, val argList: List<String>)

/**
 * This class is used for holding the information of a global pointer, like string literal, global variable, etc.
 * @param name the name of the global variable
 * With the structure presented in RISC-V assembly code, there are two parts of directives.
 * One if before the name, and the other is after the name.
 * The previous one includes something about metadata, while the latter one includes concreate data.
 */
class ASMGlobalPointer(val name: String) {
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