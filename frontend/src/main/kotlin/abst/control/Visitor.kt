package abst.control

import abst.nodes.*

open abstract class Visitor {
  // more
  abstract fun visit(curr: Prog)

  // suites
  abstract fun visit(curr: Suite)
  abstract fun visit(curr: FuncSuite)
  abstract fun visit(curr: ClassSuite)
  abstract fun visit(curr: SimpleSuite)

  // definitions
  abstract fun visit(curr: ClassDef)
  abstract fun visit(curr: FuncDef)
  abstract fun visit(curr: LambdaDef)

  // declarations
  abstract fun visit(curr: VarDecl)
  abstract fun visit(curr: FuncDecl)

  // blocks
  abstract fun visit(curr: For)
  abstract fun visit(curr: While)
  abstract fun visit(curr: Cond)
  abstract fun visit(curr: Field)

  // jumps
  abstract fun visit(curr: Jump)

  // expressions
  abstract fun visit(curr: PriorExpr)
  abstract fun visit(curr: Atom)
  abstract fun visit(curr: InitExpr)
  abstract fun visit(curr: FuncCall)
  abstract fun visit(curr: MethodAccess)
  abstract fun visit(curr: MemberAccess)
  abstract fun visit(curr: ArrayAccess)
  abstract fun visit(curr: SuffixExpr)
  abstract fun visit(curr: PrefixExpr)
  abstract fun visit(curr: BinaryExpr)
  abstract fun visit(curr: AssignExpr)


}