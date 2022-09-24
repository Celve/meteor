package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class ExprNode(pos: Position) : Base(pos)

class PriorExpr(pos: Position) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class Atom(pos: Position, literal: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class InitExpr(pos: Position) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncCall(pos: Position, func: String, params: Array<ExprNode>) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MethodAccess(pos: Position) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MemberAccess(pos: Position, expr: ExprNode, member: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ArrayAccess(pos: Position, array: ExprNode, index: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SuffixExpr(pos: Position, expr: ExprNode, op: String) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class PrefixExpr(pos: Position, op: String, expr: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class BinaryExpr(pos: Position, op: String, lhs: ExprNode, rhs: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AssignExpr(pos: Position, lhs: ExprNode, rhs: ExprNode) : ExprNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}