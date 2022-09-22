package abst.nodes

import abst.control.Visitor
import abst.utils.Position
import java.lang.Exception

open abstract class Stmt(pos: Position) : Base(pos)

// 0 stands for "return", 1 stands for "break", 2 stands for "continue"
// correspond to the order declared in antlr g4
class JumpStmt(pos: Position, keyword: Int, expr: Expr?) : Stmt(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class CondStmt(pos: Position, cond: Expr, thenDo: BlockSuite, elseDo: BlockSuite) : Stmt(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ForStmt(pos: Position, init: VarDecl?, cond: Expr?, step: Expr?) : Stmt(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class WhileStmt(pos: Position, cond: Expr?) : Stmt(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

