package abst.nodes

import abst.control.Visitor
import abst.utils.GlobalScope
import abst.utils.Position

class ProgNode(pos: Position, val suite: BaseNode) : BaseNode(pos) {
  val scope = GlobalScope(null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}