package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.utils.GlobalScope
import frontend.utils.SrcPos

class ProgNode(pos: SrcPos, val block: BaseNode) : BaseNode(pos) {
  val scope = GlobalScope(null)
  override fun accept(visitor: ASTVisitor) {
    visitor.visitProg(this)
  }
}