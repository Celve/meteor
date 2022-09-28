package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.meta.FuncMeta
import frontend.abst.utils.CodePos

class SuiteNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ClassSuiteNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncSuiteNode(pos: CodePos, val children: List<BaseNode>) : BaseNode(pos) {
  var returnType: FuncMeta? = null
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SimpleSuiteNode(pos: CodePos, val child: BaseNode) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}