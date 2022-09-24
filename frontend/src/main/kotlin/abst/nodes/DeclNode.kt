package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class DeclNode(pos: Position) : BaseNode(pos)

class VarDeclNode(pos: Position, val type: String, val assigns: List<Pair<String, BaseNode?>>) :
  DeclNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDeclNode(pos: Position) : DeclNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}