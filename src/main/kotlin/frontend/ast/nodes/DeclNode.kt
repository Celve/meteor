package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.utils.CodePos

abstract class DeclNode(pos: CodePos) : BaseNode(pos)

class VarDeclNode(pos: CodePos, val varType: String, val assigns: List<Pair<String, ExprNode?>>) :
  DeclNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}