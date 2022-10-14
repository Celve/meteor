package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.metadata.TypeMd
import frontend.utils.SrcPos

abstract class DeclNode(pos: SrcPos) : BaseNode(pos)

class VarDeclNode(pos: SrcPos, val varTypeStr: String, val assigns: List<Pair<String, ExprNode?>>) :
  DeclNode(pos) {
  var varTypeMd: TypeMd? = null
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}