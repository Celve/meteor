package frontend.abst.nodes

import frontend.abst.controller.AbstVisitor
import frontend.utils.CodePos

class JumpNode(pos: CodePos, val type: String, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: AbstVisitor) {
    visitor.visit(this)
  }
}

