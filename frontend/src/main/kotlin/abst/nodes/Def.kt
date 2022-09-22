package abst.nodes

import abst.control.Visitor
import abst.utils.Position

class Def(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ClassDef(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDef(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class LambdaDef(pos: Position) : Base(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}