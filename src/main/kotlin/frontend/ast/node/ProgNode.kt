package frontend.ast.node

import frontend.ast.controller.AstVisitor
import frontend.utils.GlobalScope
import frontend.utils.SrcPos

class ProgNode(pos: SrcPos, val block: BaseNode) : BaseNode(pos) {
  val scope = GlobalScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}