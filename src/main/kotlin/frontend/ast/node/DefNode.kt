package frontend.ast.node

import frontend.ast.controller.AstVisitor
import frontend.metadata.ClassMetadata
import frontend.metadata.FuncMetadata
import frontend.utils.SrcPos

abstract class DefNode(pos: SrcPos) : BaseNode(pos)

class ClassDefNode(pos: SrcPos, val className: String, val classBlock: BaseNode?) : DefNode(pos) {
  val classMetadata = ClassMetadata(className)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class ClassCtorNode(
  pos: SrcPos,
  val className: String,
  val params: List<Pair<String, String>>,
  val funcBlock: BaseNode?
) : DefNode(pos) {
  var funcMetadata = FuncMetadata(className, listOf(), null)
  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class FuncDefNode(
  pos: SrcPos,
  val funcName: String,
  val params: List<Pair<String, String>>,
  val returnType: String,
  val funcBlock: BaseNode?
) : DefNode(pos) {
  var funcMetadata = FuncMetadata(funcName, listOf(), null)

  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}

class LambdaDefNode(
  pos: SrcPos,
  private val isRef: Boolean,
  val params: List<Pair<String, String>>,
  val funcBlock: BaseNode?
) :
  DefNode(pos) {
  var funcMetadata = FuncMetadata("lambda", listOf(), null, isRef)

  override fun accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}