package frontend.abst.nodes

import frontend.abst.controller.AbstVisitor
import frontend.utils.CodePos
import frontend.utils.GlobalScope

class ProgNode(pos: CodePos, val suite: BaseNode) : BaseNode(pos) {
  val scope = GlobalScope(null)
  override fun accept(visitor: AbstVisitor) {
    visitor.visit(this)
  }
}