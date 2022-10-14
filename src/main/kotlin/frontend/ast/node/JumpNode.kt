package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.utils.SrcPos

class JumpNode(pos: SrcPos, val type: String, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

