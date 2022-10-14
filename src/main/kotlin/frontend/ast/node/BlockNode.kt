package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.metadata.FuncMd
import frontend.utils.SrcPos

class ProgBlockNode(pos: SrcPos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class ClassBlockNode(pos: SrcPos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class FuncBlockNode(pos: SrcPos, val children: List<BaseNode>) : BaseNode(pos) {
  var returnType: FuncMd? = null
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class SimpleBlockNode(pos: SrcPos, val child: BaseNode) : BaseNode(pos) {
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}