package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.utils.*

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
  val bodyScope = LoopScope(null, "for")
  val initScope = InitScope(null)
  override fun accept(visitor: ASTVisitor) {
    visitor.visitForSuite(this)
  }
}

class WhileSuiteNode(pos: SrcPos, val cond: ExprNode, val body: BaseNode) : SuiteNode(pos) {
  val scope = LoopScope(null, "while")
  override fun accept(visitor: ASTVisitor) {
    visitor.visitWhileSuite(this)
  }
}

class CondSuiteNode(pos: SrcPos, val cond: ExprNode, val thenDo: BaseNode, val elseDo: BaseNode?) : SuiteNode(pos) {
  val thenScope = CondScope(null)
  val elseScope = CondScope(null)
  override fun accept(visitor: ASTVisitor) {
    visitor.visitCondSuite(this)
  }
}

class FieldSuiteNode(pos: SrcPos, val block: BaseNode) : SuiteNode(pos) {
  val scope = FieldScope(null)
  override fun accept(visitor: ASTVisitor) {
    visitor.visitFieldSuite(this)
  }
}
