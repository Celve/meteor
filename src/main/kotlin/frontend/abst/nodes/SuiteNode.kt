package frontend.abst.nodes

import frontend.abst.controller.AbstVisitor
import frontend.meta.FuncMeta
import frontend.utils.CodePos

class ProgSuiteNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: AbstVisitor) {
    visitor.visit(this)
  }
}

class ClassSuiteNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: AbstVisitor) {
    visitor.visit(this)
  }
}

class FuncSuiteNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  var returnType: FuncMeta? = null
  override fun accept(visitor: AbstVisitor) {
    visitor.visit(this)
  }
}

class SimpleSuiteNode(pos: CodePos, val child: BaseNode) : BaseNode(pos) {
  override fun accept(visitor: AbstVisitor) {
    visitor.visit(this)
  }
}