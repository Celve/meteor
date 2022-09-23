package abst.nodes

import abst.control.Visitor
import abst.utils.Position
import abst.utils.Scope

class Prog(pos: Position, val scope: Scope, val children: Array<Base>) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}