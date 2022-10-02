package frontend.ast.node

import frontend.ast.controller.AstVisitor
import frontend.metadata.TypeMd
import frontend.utils.SrcPos

abstract class DeclNode(pos: SrcPos) : BaseNode(pos)

class VarDeclNode(pos: SrcPos, val varTypeStr: String, val assigns: List<Pair<String, ExprNode?>>) :
  DeclNode(pos) {
  var varTypeMd: TypeMd? = null
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}