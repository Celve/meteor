package abst.nodes

import abst.control.Visitor
import abst.utils.CodePos
import abst.utils.GlobalScope

class ProgNode(pos: CodePos, val suite: BaseNode) : BaseNode(pos) {
  val scope = GlobalScope(null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}