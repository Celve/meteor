package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class Decl(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class VarDecl(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDecl(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}