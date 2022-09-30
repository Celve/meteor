package frontend.ast.node

import frontend.ast.controller.AstVisitor
import frontend.utils.SrcPos

class JumpNode(pos: SrcPos, val type: String, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

