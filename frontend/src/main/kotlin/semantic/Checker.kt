package semantic

import abst.control.Visitor
import abst.nodes.*
import abst.utils.ScopeManager

class Checker : Visitor() {
  val scopeManager = ScopeManager()
  override fun visit(curr: Prog) {
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
  }

  override fun visit(curr: Suite) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: FuncSuite) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: ClassSuite) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: SimpleSuite) {
    curr.child.accept(this)
  }

  // the definition of class should be in global scope
  // the antlr guarantees it, so here is no check
  // the member and method should be put in both global scope and local scope
  override fun visit(curr: ClassDef) {
    val scope = scopeManager.first()
    scope.set(curr.className, "class")
    scope.set(curr.className + "()", "implicit")
    curr.classSuite.accept(this)
  }

  // the definition of function should be in global scope
  // the antlr guarantees it, so here is no check
  override fun visit(curr: FuncDef) {
    val outerScope = scopeManager.first()
    outerScope.set(curr.funcName, curr.paramType.foldRight("") { a, b -> "$a,$b" }.plus(curr.returnType))
    scopeManager.addLast(curr.scope)
    curr.funcSuite.accept(this)
  }

  override fun visit(curr: LambdaDef) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: VarDecl) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncDecl) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: For) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: While) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: Cond) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: Field) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: Jump) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: PriorExpr) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: Atom) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: InitExpr) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncCall) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: MethodAccess) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: MemberAccess) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ArrayAccess) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: SuffixExpr) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: PrefixExpr) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: BinaryExpr) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: AssignExpr) {
    TODO("Not yet implemented")
  }
}