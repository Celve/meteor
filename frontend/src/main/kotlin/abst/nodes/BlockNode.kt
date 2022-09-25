package abst.nodes

import abst.control.Visitor
import abst.utils.CodePos
import abst.utils.Scope

abstract class BlockNode(pos: CodePos) : BaseNode(pos)

// init could be varDecl or expr
// suite is actually extendedBlock, which corresponds to stmt, jump, or block
class ForNode(
  pos: CodePos,
  scope: Scope,
  val init: BaseNode?,
  val cond: ExprNode?,
  val step: ExprNode?,
  val suite: BaseNode
) :
  BlockNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class WhileNode(pos: CodePos, val cond: ExprNode, val suite: BaseNode) : BlockNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class CondNode(pos: CodePos, val cond: ExprNode, val thenDo: BaseNode?, val elseDo: BaseNode?) : BlockNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FieldNode(pos: CodePos, val suite: FuncSuiteNode) : BlockNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}
