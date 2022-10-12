package frontend.ast.controller

import frontend.ast.node.*

abstract class ASTVisitor {
  // more
  abstract fun visitProg(curr: ProgNode)

  // suites
  abstract fun visitProgBlock(curr: ProgBlockNode)
  abstract fun visitFuncBlock(curr: FuncBlockNode)
  abstract fun visitClassBlock(curr: ClassBlockNode)
  abstract fun visitSimpleBlock(curr: SimpleBlockNode)

  // definitions
  abstract fun visitClassDef(curr: ClassDefNode)
  abstract fun visitClassCtor(curr: ClassCtorNode)
  abstract fun visitFuncDef(curr: FuncDefNode)
  abstract fun visitLambdaDef(curr: LambdaDefNode)

  // declarations
  abstract fun visitVarDecl(curr: VarDeclNode)

  // blocks
  abstract fun visitForSuite(curr: ForSuiteNode)
  abstract fun visitWhileSuite(curr: WhileSuiteNode)
  abstract fun visitCondSuite(curr: CondSuiteNode)
  abstract fun visitFieldSuite(curr: FieldSuiteNode)

  // jumps
  abstract fun visitJump(curr: JumpNode)

  // expressions
  abstract fun visitShort(curr: ShortNode)
  abstract fun visitPriorExpr(curr: PriorExprNode)
  abstract fun visitAtom(curr: AtomNode)
  abstract fun visitInitExpr(curr: NewExprNode)
  abstract fun visitLambdaCall(curr: LambdaCallNode)
  abstract fun visitFuncCall(curr: FuncCallNode)
  abstract fun visitMethodCall(curr: MethodCallNode)
  abstract fun visitMemberAccess(curr: MemberAccessNode)
  abstract fun visitArrayAccess(curr: ArrayAccessNode)
  abstract fun visitSuffixExpr(curr: SuffixExprNode)
  abstract fun visitPrefixExpr(curr: PrefixExprNode)
  abstract fun visitBinaryExpr(curr: BinaryExprNode)
  abstract fun visitLogicalAndExpr(curr: LogicalAndExprNode)
  abstract fun visitLogicalOrExpr(curr: LogicalOrExprNode)
  abstract fun visitAssignExpr(curr: AssignExprNode)


}