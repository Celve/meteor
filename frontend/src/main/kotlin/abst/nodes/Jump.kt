package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class Jump(pos: Position, type: String, expr: Expr?) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

