package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class Decl(pos: Position) : Base(pos)

class VarDecl(pos: Position) : Decl(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDecl(pos: Position) : Decl(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}