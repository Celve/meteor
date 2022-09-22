package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class Expr(pos: Position) : Base(pos)

class PriorExpr(pos: Position) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class Atom(pos: Position) : Expr(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MethodAccess

class MemberAccess

class ArrayAccess

class SuffixExpr

class PrefixExpr

class BinaryExpr

class AssignExpr