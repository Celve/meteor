package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class Prog(pos: Position, children: Array<Base>) : Base(pos) {
  val stmts: Array<Base> = children

  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}