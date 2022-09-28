package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.utils.CodePos
import frontend.abst.utils.CondScope
import frontend.abst.utils.FieldScope
import frontend.abst.utils.LoopScope

abstract class BlockNode(pos: CodePos) : BaseNode(pos)

// init could be varDecl or expr
// suite is actually extendedBlock, which corresponds to stmt, jump, decl, or block
class ForNode(
  pos: CodePos,
  val init: BaseNode?,
  val cond: ExprNode?,
  val step: ExprNode?,
  val suite: BaseNode
) :
  BlockNode(pos) {
  val scope = LoopScope(null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class WhileNode(pos: CodePos, val cond: ExprNode, val suite: BaseNode) : BlockNode(pos) {
  val scope = LoopScope(null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class CondNode(pos: CodePos, val cond: ExprNode, val thenDo: BaseNode, val elseDo: BaseNode?) : BlockNode(pos) {
  val thenScope = CondScope(null)
  val elseScope = CondScope(null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FieldNode(pos: CodePos, val suite: BaseNode) : BlockNode(pos) {
  val scope = FieldScope(null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}
