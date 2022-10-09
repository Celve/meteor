package frontend.ast.node

import frontend.ast.controller.AstVisitor
import frontend.utils.CondScope
import frontend.utils.FieldScope
import frontend.utils.LoopScope
import frontend.utils.SrcPos

abstract class SuiteNode(pos: SrcPos) : BaseNode(pos)

// suite is actually extendedBlock, which corresponds to stmt, jump, decl, or block
class ForSuiteNode(
  pos: SrcPos,
  val init: BaseNode?, // init could be varDecl or expr
  val cond: ExprNode?,
  val inc: ExprNode?,
  val body: BaseNode
) :
  SuiteNode(pos) {
  val scope = LoopScope(null, "for")
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class WhileSuiteNode(pos: SrcPos, val cond: ExprNode, val body: BaseNode) : SuiteNode(pos) {
  val scope = LoopScope(null, "while")
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class CondSuiteNode(pos: SrcPos, val cond: ExprNode, val thenDo: BaseNode, val elseDo: BaseNode?) : SuiteNode(pos) {
  val thenScope = CondScope(null)
  val elseScope = CondScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class FieldSuiteNode(pos: SrcPos, val block: BaseNode) : SuiteNode(pos) {
  val scope = FieldScope(null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}
