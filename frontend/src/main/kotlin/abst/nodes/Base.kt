package abst.nodes

import abst.control.Visitor
import abst.utils.Position

open abstract class Base(pos: Position) {

  abstract fun accept(visitor: Visitor)
}