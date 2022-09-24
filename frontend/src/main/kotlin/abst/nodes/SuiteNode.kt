package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class SuiteNode(pos: Position, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ClassSuiteNode(pos: Position, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncSuiteNode(pos: Position, val children: List<BaseNode>) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SimpleSuiteNode(pos: Position, val child: BaseNode) : BaseNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}