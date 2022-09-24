package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class JumpNode(pos: Position, type: String, expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

