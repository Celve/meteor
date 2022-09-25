package abst.nodes

import abst.control.Visitor
import abst.utils.Position
import meta.ClassMeta
import meta.FuncMeta

abstract class DefNode(pos: Position) : BaseNode(pos)

class ClassDefNode(pos: Position, val className: String, val classSuite: BaseNode?) : DefNode(pos) {
  val classMeta = ClassMeta(className, listOf(), listOf())
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ClassCtorNode(
  pos: Position,
  val className: String,
  val paramTypes: List<String>,
  val paramNames: List<String>,
  val funcSuite: BaseNode?
) : DefNode(pos) {
  var funcMeta = FuncMeta(className, listOf(), listOf(), null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDefNode(
  pos: Position,
  val funcName: String,
  val paramTypes: List<String>,
  val paramNames: List<String>,
  val returnType: String,
  val funcSuite: BaseNode?
) : DefNode(pos) {
  var funcMeta = FuncMeta(funcName, listOf(), listOf(), null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class LambdaDefNode(pos: Position) : DefNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}