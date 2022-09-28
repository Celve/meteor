package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.utils.CodePos

class JumpNode(pos: CodePos, val type: String, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

