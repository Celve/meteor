package abst.nodes

import abst.control.Visitor
import abst.utils.CodePos

abstract class DeclNode(pos: CodePos) : BaseNode(pos)

class VarDeclNode(pos: CodePos, val type: String, val assigns: List<Pair<String, BaseNode?>>) :
  DeclNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}