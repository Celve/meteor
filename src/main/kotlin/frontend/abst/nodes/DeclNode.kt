package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.utils.CodePos

abstract class DeclNode(pos: CodePos) : BaseNode(pos)

class VarDeclNode(pos: CodePos, val varType: String, val assigns: List<Pair<String, ExprNode?>>) :
  DeclNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}