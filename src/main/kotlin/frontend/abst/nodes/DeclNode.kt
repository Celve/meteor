package frontend.abst.nodes

import frontend.abst.controller.AbstVisitor
import frontend.utils.CodePos

abstract class DeclNode(pos: CodePos) : BaseNode(pos)

class VarDeclNode(pos: CodePos, val varType: String, val assigns: List<Pair<String, ExprNode?>>) :
  DeclNode(pos) {
  override fun accept(visitor: AbstVisitor) {
    visitor.visit(this)
  }
}