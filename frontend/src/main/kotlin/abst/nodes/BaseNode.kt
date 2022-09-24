package abst.nodes

import abst.control.Visitor
import abst.utils.Position

open abstract class BaseNode(pos: Position) {
  abstract fun accept(visitor: Visitor)
}