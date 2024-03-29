package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.metadata.TypeMd
import frontend.utils.SrcPos
import middleend.basic.Value

// honestly, this is a stmt node, but it's actually a expr node, hence it's put in here
class ShortNode(pos: SrcPos, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

// assignable is all determined in builder
abstract class ExprNode(pos: SrcPos, var assignable: Boolean) : BaseNode(pos) {
  var type: TypeMd? = null
  var value: Value? = null
}

class PriorExprNode(pos: SrcPos, val expr: ExprNode) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class AtomNode(pos: SrcPos, val id: Int, val literal: String) : ExprNode(pos, id == 2) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

/**
 * For arraySizeExprList, the first half would be non-null, and the second half would be null.
 */
class NewExprNode(pos: SrcPos, val typeDef: String, val dim: Int, var arraySizeExprList: List<ExprNode?>) :
  ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class LambdaCallNode(pos: SrcPos, val lambdaDef: LambdaDefNode, val params: List<ExprNode>) :
  ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class FuncCallNode(pos: SrcPos, val funcName: String, val argList: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class MethodCallNode(pos: SrcPos, val expr: ExprNode, val method: String, val argList: List<ExprNode>) :
  ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class MemberAccessNode(pos: SrcPos, val expr: ExprNode, val member: String) : ExprNode(pos, true) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class ArrayAccessNode(pos: SrcPos, val array: ExprNode, val index: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class SuffixExprNode(pos: SrcPos, val expr: ExprNode, val op: String) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class PrefixExprNode(pos: SrcPos, val op: String, val expr: ExprNode) : ExprNode(pos, op == "++" || op == "--") {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class BinaryExprNode(pos: SrcPos, val ops: List<String>, val exprs: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}


class LogicalAndExprNode(pos: SrcPos, val exprs: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class LogicalOrExprNode(pos: SrcPos, val exprs: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}


class AssignExprNode(pos: SrcPos, val lhs: ExprNode, val rhs: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}
