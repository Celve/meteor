package abst.nodes

import abst.control.Visitor
import abst.utils.Position
import abst.utils.Scope

abstract class Block(pos: Position) : Base(pos)

// init could be varDecl or expr
// suite is actually extendedBlock, which corresponds to stmt, jump, or block
class For(pos: Position, scope: Scope, val init: Base?, val cond: Expr?, val step: Expr?, val suite: Base) :
  Block(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class While(pos: Position, val cond: Expr, val suite: Base) : Block(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class Cond(pos: Position, val cond: Expr, val thenDo: Base?, val elseDo: Base?) : Block(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class Field(pos: Position, val suite: FuncSuite) : Block(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}
