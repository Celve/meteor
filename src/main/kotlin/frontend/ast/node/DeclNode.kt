package frontend.ast.node

import frontend.ast.controller.AstVisitor
import frontend.utils.SrcPos

abstract class DeclNode(pos: SrcPos) : BaseNode(pos)

class VarDeclNode(pos: SrcPos, val varType: String, val assigns: List<Pair<String, ExprNode?>>) :
  DeclNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}