package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.metadata.ClassMetadata
import frontend.metadata.FuncMetadata
import frontend.utils.CodePos

abstract class DefNode(pos: CodePos) : BaseNode(pos)

class ClassDefNode(pos: CodePos, val className: String, val classSuite: BaseNode?) : DefNode(pos) {
  val classMetadata = ClassMetadata(className)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class ClassCtorNode(
  pos: CodePos,
  val className: String,
  val params: List<Pair<String, String>>,
  val funcSuite: BaseNode?
) : DefNode(pos) {
  var funcMetadata = FuncMetadata(className, listOf(), null)
  override fun accept(visitor: AstVisitor) {
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
  var funcMetadata = FuncMetadata(funcName, listOf(), null)

  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class LambdaDefNode(
  pos: CodePos,
  val isRef: Boolean,
  val params: List<Pair<String, String>>,
  val funcSuite: BaseNode?
) :
  DefNode(pos) {
  var funcMetadata = FuncMetadata("lambda", listOf(), null, isRef)

  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}