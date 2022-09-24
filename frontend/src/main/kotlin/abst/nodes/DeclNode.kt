package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class DeclNode(pos: Position) : Base(pos)

class VarDecl(pos: Position) : DeclNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDecl(pos: Position) : DeclNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}