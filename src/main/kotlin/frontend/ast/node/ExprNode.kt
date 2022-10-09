package frontend.ast.node

import frontend.ast.controller.AstVisitor
import frontend.metadata.TypeMd
import frontend.utils.SrcPos
import middleend.basic.Value

// although but, stmt should be put inside Stmt.kt, however, it's now
class ShortNode(pos: SrcPos, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

// assignable is all determined in builder
abstract class ExprNode(pos: SrcPos, var assignable: Boolean) : BaseNode(pos) {
  var type: TypeMd? = null
  var value: Value? = null
}

class PriorExprNode(pos: SrcPos, val expr: ExprNode) : ExprNode(pos, false) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class AtomNode(pos: SrcPos, val id: Int, val literal: String) : ExprNode(pos, id == 2) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

/**
 * For arraySizeExprList, the first half would be non-null, and the second half would be null.
 */
class InitExprNode(pos: SrcPos, val typeDef: String, val dim: Int, val arraySizeExprList: List<ExprNode?>) :
  ExprNode(pos, false) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class LambdaCallNode(pos: SrcPos, val lambdaDef: LambdaDefNode, val params: List<ExprNode>) :
  ExprNode(pos, false) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class FuncCallNode(pos: SrcPos, val funcName: String, val params: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class MethodAccessNode(pos: SrcPos, val expr: ExprNode, val method: String, val params: List<ExprNode>) :
  ExprNode(pos, false) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class MemberAccessNode(pos: SrcPos, val expr: ExprNode, val member: String) : ExprNode(pos, true) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class ArrayAccessNode(pos: SrcPos, val array: ExprNode, val index: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class SuffixExprNode(pos: SrcPos, val expr: ExprNode, val op: String) : ExprNode(pos, false) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class PrefixExprNode(pos: SrcPos, val op: String, val expr: ExprNode) : ExprNode(pos, op == "++" || op == "--") {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class BinaryExprNode(pos: SrcPos, val op: String, val exprs: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class AssignExprNode(pos: SrcPos, val lhs: ExprNode, val rhs: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}
