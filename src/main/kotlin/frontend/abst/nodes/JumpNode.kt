package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.utils.CodePos

class JumpNode(pos: CodePos, val type: String, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

