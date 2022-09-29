package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.utils.CodePos
import frontend.utils.CondScope
import frontend.utils.FieldScope
import frontend.utils.LoopScope

abstract class SuiteNode(pos: CodePos) : BaseNode(pos)

// suite is actually extendedBlock, which corresponds to stmt, jump, decl, or block
class ForSuiteNode(
  pos: CodePos,
  val init: BaseNode?, // init could be varDecl or expr
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

class WhileSuiteNode(pos: CodePos, val cond: ExprNode, val suite: BaseNode) : SuiteNode(pos) {
  val scope = LoopScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class CondSuiteNode(pos: CodePos, val cond: ExprNode, val thenDo: BaseNode, val elseDo: BaseNode?) : SuiteNode(pos) {
  val thenScope = CondScope(null)
  val elseScope = CondScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class FieldSuiteNode(pos: CodePos, val suite: BaseNode) : SuiteNode(pos) {
  val scope = FieldScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}
