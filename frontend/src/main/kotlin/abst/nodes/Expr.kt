package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class Expr(pos: Position) : Base(pos)

class PriorExpr(pos: Position) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class Atom(pos: Position, literal: String) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class InitExpr(pos: Position) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncCall(pos: Position, func: String, params: Array<Expr>) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MethodAccess(pos: Position) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MemberAccess(pos: Position, expr: Expr, member: String) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ArrayAccess(pos: Position, array: Expr, index: Expr) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SuffixExpr(pos: Position, expr: Expr, op: String) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class PrefixExpr(pos: Position, op: String, expr: Expr) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class BinaryExpr(pos: Position, op: String, lhs: Expr, rhs: Expr) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AssignExpr(pos: Position, lhs: Expr, rhs: Expr) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}