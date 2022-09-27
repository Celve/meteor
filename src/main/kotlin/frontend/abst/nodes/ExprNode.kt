package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.meta.TypeMeta
import frontend.abst.utils.CodePos

class StmtNode(pos: CodePos, val expr: ExprNode?) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

// assignable is all determined in builder
abstract class ExprNode(pos: CodePos, var assignable: Boolean) : BaseNode(pos) {
  var type: TypeMeta? = null
//  override fun accept(visitor: Visitor) {
//    visitor.visit(this)
//  }
}

class PriorExprNode(pos: CodePos, val expr: ExprNode) : ExprNode(pos, false) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AtomNode(pos: CodePos, val id: Int, val literal: String) : ExprNode(pos, id == 2) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class InitExprNode(pos: CodePos, val typeDef: String, val dim: Int, val arraySizeList: List<ExprNode?>) :
  ExprNode(pos, false) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncCallNode(pos: CodePos, val func: String, val params: List<ExprNode>) : ExprNode(pos, false) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MethodAccessNode(pos: CodePos, val expr: ExprNode, val method: String, val params: List<ExprNode>) :
  ExprNode(pos, false) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class MemberAccessNode(pos: CodePos, val expr: ExprNode, val member: String) : ExprNode(pos, true) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ArrayAccessNode(pos: CodePos, val array: ExprNode, val index: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SuffixExprNode(pos: CodePos, val expr: ExprNode, val op: String) : ExprNode(pos, false) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class PrefixExprNode(pos: CodePos, val op: String, val expr: ExprNode) : ExprNode(pos, op == "++" || op == "--") {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class BinaryExprNode(pos: CodePos, val op: String, val lhs: ExprNode, val rhs: ExprNode) : ExprNode(pos, false) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class AssignExprNode(pos: CodePos, val lhs: ExprNode, val rhs: ExprNode) : ExprNode(pos, true) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}
