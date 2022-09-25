package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.utils.CodePos
import frontend.abst.utils.GlobalScope

class ProgNode(pos: CodePos, val suite: BaseNode) : BaseNode(pos) {
  val scope = GlobalScope(null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}