package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class Suite

class BlockSuite(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncSuite