package abst.nodes

import abst.control.Visitor
import abst.utils.Position
import meta.ClassMeta

open class ExprNode(pos: Position) : BaseNode(pos) {
  var type: ClassMeta? = null
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class PriorExprNode(pos: Position) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AtomNode(pos: Position, literal: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class InitExprNode(pos: Position) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncCallNode(pos: Position, func: String, params: Array<ExprNode>) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MethodAccessNode(pos: Position) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MemberAccessNode(pos: Position, expr: ExprNode, member: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ArrayAccessNode(pos: Position, array: ExprNode, index: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SuffixExprNode(pos: Position, expr: ExprNode, op: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class PrefixExprNode(pos: Position, op: String, expr: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class BinaryExprNode(pos: Position, op: String, lhs: ExprNode, rhs: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AssignExprNode(pos: Position, lhs: ExprNode, rhs: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}