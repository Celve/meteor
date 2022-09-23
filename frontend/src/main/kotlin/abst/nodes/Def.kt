package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class Def(pos: Position) : Base(pos)

class ClassDef(pos: Position) : Def(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDef(pos: Position) : Def(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class LambdaDef(pos: Position) : Def(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}