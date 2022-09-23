package abst.nodes

import abst.control.Visitor
import abst.utils.Position

abstract class Def(pos: Position) : Base(pos)

class ClassDef(pos: Position, val className: String, val classSuite: Base) : Def(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDef(
  pos: Position,
  val scope: Scope,
  val returnType: String,
  val funcName: String,
  val paramType: List<String>,
  val paramName: List<String>,
  val funcSuite: Base
) : Def(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class LambdaDef(pos: Position) : Def(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}