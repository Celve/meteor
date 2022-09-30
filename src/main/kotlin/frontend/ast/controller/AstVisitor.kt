package frontend.ast.controller

import frontend.ast.node.*

abstract class AstVisitor {
  // more
  abstract fun visit(curr: ProgNode)

  // suites
  abstract fun visit(curr: ProgBlockNode)
  abstract fun visit(curr: FuncBlockNode)
  abstract fun visit(curr: ClassBlockNode)
  abstract fun visit(curr: SimpleBlockNode)

  // definitions
  abstract fun visit(curr: ClassDefNode)
  abstract fun visit(curr: ClassCtorNode)
  abstract fun visit(curr: FuncDefNode)
  abstract fun visit(curr: LambdaDefNode)

  // declarations
  abstract fun visit(curr: VarDeclNode)

  // blocks
  abstract fun visit(curr: ForSuiteNode)
  abstract fun visit(curr: WhileSuiteNode)
  abstract fun visit(curr: CondSuiteNode)
  abstract fun visit(curr: FieldSuiteNode)

  // jumps
  abstract fun visit(curr: JumpNode)

  // expressions
  abstract fun visit(curr: ShortNode)
  abstract fun visit(curr: PriorExprNode)
  abstract fun visit(curr: AtomNode)
  abstract fun visit(curr: InitExprNode)
  abstract fun visit(curr: LambdaCallNode)
  abstract fun visit(curr: FuncCallNode)
  abstract fun visit(curr: MethodAccessNode)
  abstract fun visit(curr: MemberAccessNode)
  abstract fun visit(curr: ArrayAccessNode)
  abstract fun visit(curr: SuffixExprNode)
  abstract fun visit(curr: PrefixExprNode)
  abstract fun visit(curr: BinaryExprNode)
  abstract fun visit(curr: AssignExprNode)


}