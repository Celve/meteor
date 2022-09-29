package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.utils.CodePos
import frontend.utils.CondScope
import frontend.utils.FieldScope
import frontend.utils.LoopScope

abstract class SuiteNode(pos: CodePos) : BaseNode(pos)

// init could be varDecl or expr
// suite is actually extendedBlock, which corresponds to stmt, jump, decl, or block
class ForNode(
  pos: CodePos,
  val init: BaseNode?,
  val cond: ExprNode?,
  val step: ExprNode?,
  val suite: BaseNode
) :
  SuiteNode(pos) {
  val scope = LoopScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class WhileNode(pos: CodePos, val cond: ExprNode, val suite: BaseNode) : SuiteNode(pos) {
  val scope = LoopScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class CondNode(pos: CodePos, val cond: ExprNode, val thenDo: BaseNode, val elseDo: BaseNode?) : SuiteNode(pos) {
  val thenScope = CondScope(null)
  val elseScope = CondScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class FieldNode(pos: CodePos, val suite: BaseNode) : SuiteNode(pos) {
  val scope = FieldScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}
