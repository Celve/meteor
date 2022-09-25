package abst.nodes

import abst.control.Visitor
import abst.utils.CodePos
import meta.ClassMeta

open class ExprNode(pos: CodePos) : BaseNode(pos) {
  var type: ClassMeta? = null
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class PriorExprNode(pos: CodePos) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AtomNode(pos: CodePos, literal: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class InitExprNode(pos: CodePos) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncCallNode(pos: CodePos, func: String, params: Array<ExprNode>) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MethodAccessNode(pos: CodePos) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MemberAccessNode(pos: CodePos, expr: ExprNode, member: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ArrayAccessNode(pos: CodePos, array: ExprNode, index: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SuffixExprNode(pos: CodePos, expr: ExprNode, op: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class PrefixExprNode(pos: CodePos, op: String, expr: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class BinaryExprNode(pos: CodePos, op: String, lhs: ExprNode, rhs: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AssignExprNode(pos: CodePos, lhs: ExprNode, rhs: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}