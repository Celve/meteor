package semantic

import abst.control.Visitor
import abst.nodes.*

class Checker : Visitor() {
  override fun visit(curr: Prog) {
    curr.children.forEach { it.accept(this) }
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

  override fun visit(curr: JumpStmt) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: CondStmt) {
    println(curr.cond.toString())
    println(curr.thenDo.toString())
    println(curr.elseDo.toString())
  }

  override fun visit(curr: ForStmt) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: WhileStmt) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: Def) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: Decl) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncSuite) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: VarDecl) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncDecl) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ClassDef) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncDef) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: LambdaDef) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: BlockSuite) {
    TODO("Not yet implemented")
  }

}