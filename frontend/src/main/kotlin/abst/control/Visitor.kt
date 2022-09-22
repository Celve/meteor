package abst.control

import abst.nodes.*

open abstract class Visitor {
  // expressions
  abstract fun visit(node: PriorExpr);
  abstract fun visit(node: Atom);
  abstract fun visit(node: MethodAccess);
  abstract fun visit(node: MemberAccess);
  abstract fun visit(node: ArrayAccess);
  abstract fun visit(node: SuffixExpr);
  abstract fun visit(node: PrefixExpr);
  abstract fun visit(node: BinaryExpr);
  abstract fun visit(node: AssignExpr);


  // statements
  abstract fun visit(node: JumpStmt);
  abstract fun visit(node: ExprStmt);
  abstract fun visit(node: ForStmt);
  abstract fun visit(node: WhileStmt);
  abstract fun visit(node: Def);
  abstract fun visit(node: Decl);
  abstract fun visit(node: FuncSuite);
}