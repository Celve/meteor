package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.meta.ClassMeta
import frontend.abst.meta.FuncMeta
import frontend.abst.utils.CodePos

abstract class DefNode(pos: CodePos) : BaseNode(pos)

class ClassDefNode(pos: CodePos, val className: String, val classSuite: BaseNode?) : DefNode(pos) {
  val classMeta = ClassMeta(className)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class ClassCtorNode(
  pos: CodePos,
  val className: String,
  val params: List<Pair<String, String>>,
  val funcSuite: BaseNode?
) : DefNode(pos) {
  var funcMeta = FuncMeta(className, listOf(), null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class FuncDefNode(
  pos: CodePos,
  val funcName: String,
  val params: List<Pair<String, String>>,
  val returnType: String,
  val funcSuite: BaseNode?
) : DefNode(pos) {
  var funcMeta = FuncMeta(funcName, listOf(), null)
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}

class LambdaDefNode(pos: CodePos) : DefNode(pos) {
  override fun accept(visitor: Visitor) {
    visitor.visit(this)
  }
}