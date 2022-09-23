package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class Suite(pos: Position, val children: List<Base>) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ClassSuite(pos: Position, val children: List<Base>) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncSuite(pos: Position, val children: List<Base>) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class SimpleSuite(pos: Position, val child: Base) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}