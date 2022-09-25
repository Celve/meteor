package abst.nodes

import abst.control.Visitor
import abst.utils.CodePos

class JumpNode(pos: CodePos, type: String, expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

