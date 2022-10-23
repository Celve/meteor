package middleend.basic

import middleend.pass.IRVisitor

class GlobalVariable(name: String, type: Type) : GlobalValue(name, type) {
  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}