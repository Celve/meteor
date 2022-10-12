package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.metadata.TypeMd
import frontend.utils.SrcPos
import middleend.basic.Value

// although but, stmt should be put inside Stmt.kt, however, it's now
class ShortNode(pos: SrcPos, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitShort(this)
  }
}

// assignable is all determined in builder
abstract class ExprNode(pos: SrcPos, var assignable: Boolean) : BaseNode(pos) {
  var type: TypeMd? = null
  var value: Value? = null
}

class PriorExprNode(pos: SrcPos, val expr: ExprNode) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitPriorExpr(this)
  }
}

class AtomNode(pos: SrcPos, val id: Int, val literal: String) : ExprNode(pos, id == 2) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitAtom(this)
  }
}

/**
 * For arraySizeExprList, the first half would be non-null, and the second half would be null.
 */
class NewExprNode(pos: SrcPos, val typeDef: String, val dim: Int, var arraySizeExprList: List<ExprNode?>) :
  ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitInitExpr(this)
  }
}

class LambdaCallNode(pos: SrcPos, val lambdaDef: LambdaDefNode, val params: List<ExprNode>) :
  ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitLambdaCall(this)
  }
}

class FuncCallNode(pos: SrcPos, val funcName: String, val argList: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitFuncCall(this)
  }
}

class MethodCallNode(pos: SrcPos, val expr: ExprNode, val method: String, val argList: List<ExprNode>) :
  ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitMethodCall(this)
  }
}

class MemberAccessNode(pos: SrcPos, val expr: ExprNode, val member: String) : ExprNode(pos, true) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitMemberAccess(this)
  }
}

class ArrayAccessNode(pos: SrcPos, val array: ExprNode, val index: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitArrayAccess(this)
  }
}

class SuffixExprNode(pos: SrcPos, val expr: ExprNode, val op: String) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitSuffixExpr(this)
  }
}

class PrefixExprNode(pos: SrcPos, val op: String, val expr: ExprNode) : ExprNode(pos, op == "++" || op == "--") {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitPrefixExpr(this)
  }
}

class BinaryExprNode(pos: SrcPos, val ops: List<String>, val exprs: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitBinaryExpr(this)
  }
}


class LogicalAndExprNode(pos: SrcPos, val exprs: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitLogicalAndExpr(this)
  }
}

class LogicalOrExprNode(pos: SrcPos, val exprs: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitLogicalOrExpr(this)
  }
}


class AssignExprNode(pos: SrcPos, val lhs: ExprNode, val rhs: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visitAssignExpr(this)
  }
}
