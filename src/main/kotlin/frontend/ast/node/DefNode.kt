package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.metadata.ClassMd
import frontend.metadata.FuncMd
import frontend.utils.SrcPos

abstract class DefNode(pos: SrcPos) : BaseNode(pos)

class ClassDefNode(pos: SrcPos, val className: String, val classBlock: BaseNode?) : DefNode(pos) {
  val classMd = ClassMd(className)
  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}

class ClassCtorNode(
  pos: SrcPos,
  val className: String,
  val params: List<Pair<String, String>>,
  val funcBlock: BaseNode?
) : DefNode(pos) {
  var funcMd = FuncMd("new", listOf(), null)
  override fun accept(visitor: ASTVisitor) {
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
  var funcMd = FuncMd(funcName, listOf(), null)

  override fun accept(visitor: ASTVisitor) {
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
  var funcMd = FuncMd("lambda", listOf(), null, isRef)

  override fun accept(visitor: ASTVisitor) {
    visitor.visit(this)
  }
}