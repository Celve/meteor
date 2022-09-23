package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class Suite(pos: Position) : Base(pos)

class ClassSuite(pos: Position, val children: Array<Base>) : Suite(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncSuite(pos: Position, val children: Array<Base>) : Suite(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}