package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.utils.CodePos
import frontend.utils.GlobalScope

class ProgNode(pos: CodePos, val suite: BaseNode) : BaseNode(pos) {
  val scope = GlobalScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}