package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.metadata.FuncMetadata
import frontend.utils.CodePos

class ProgBlockNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class ClassBlockNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class FuncBlockNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  var returnType: FuncMetadata? = null
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class SimpleBlockNode(pos: CodePos, val child: BaseNode) : BaseNode(pos) {
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}