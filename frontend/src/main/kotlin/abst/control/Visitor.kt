package abst.control

import abst.nodes.*

open abstract class Visitor {
  // more
  abstract fun visit(curr: ProgNode)

  // suites
  abstract fun visit(curr: SuiteNode)
  abstract fun visit(curr: FuncSuiteNode)
  abstract fun visit(curr: ClassSuiteNode)
  abstract fun visit(curr: SimpleSuiteNode)

  // definitions
  abstract fun visit(curr: ClassDefNode)
  abstract fun visit(curr: ClassCtorNode)
  abstract fun visit(curr: FuncDefNode)
  abstract fun visit(curr: LambdaDefNode)

  // declarations
  abstract fun visit(curr: VarDeclNode)

  // blocks
  abstract fun visit(curr: ForNode)
  abstract fun visit(curr: WhileNode)
  abstract fun visit(curr: CondNode)
  abstract fun visit(curr: FieldNode)

  // jumps
  abstract fun visit(curr: JumpNode)

  // expressions
  abstract fun visit(curr: ExprNode)
  abstract fun visit(curr: PriorExprNode)
  abstract fun visit(curr: AtomNode)
  abstract fun visit(curr: InitExprNode)
  abstract fun visit(curr: FuncCallNode)
  abstract fun visit(curr: MethodAccessNode)
  abstract fun visit(curr: MemberAccessNode)
  abstract fun visit(curr: ArrayAccessNode)
  abstract fun visit(curr: SuffixExprNode)
  abstract fun visit(curr: PrefixExprNode)
  abstract fun visit(curr: BinaryExprNode)
  abstract fun visit(curr: AssignExprNode)


}