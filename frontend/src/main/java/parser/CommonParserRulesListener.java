package parser;// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CommonParserRulesParser}.
 */
public interface CommonParserRulesListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#prog}.
   *
   * @param ctx the parse tree
   */
  void enterProg(CommonParserRulesParser.ProgContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#prog}.
   *
   * @param ctx the parse tree
   */
  void exitProg(CommonParserRulesParser.ProgContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterStmt(CommonParserRulesParser.StmtContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitStmt(CommonParserRulesParser.StmtContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#decl}.
   *
   * @param ctx the parse tree
   */
  void enterDecl(CommonParserRulesParser.DeclContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#decl}.
   *
   * @param ctx the parse tree
   */
  void exitDecl(CommonParserRulesParser.DeclContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#def}.
   *
   * @param ctx the parse tree
   */
  void enterDef(CommonParserRulesParser.DefContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#def}.
   *
   * @param ctx the parse tree
   */
  void exitDef(CommonParserRulesParser.DefContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#varType}.
   *
   * @param ctx the parse tree
   */
  void enterVarType(CommonParserRulesParser.VarTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#varType}.
   *
   * @param ctx the parse tree
   */
  void exitVarType(CommonParserRulesParser.VarTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#primitiveType}.
   *
   * @param ctx the parse tree
   */
  void enterPrimitiveType(CommonParserRulesParser.PrimitiveTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#primitiveType}.
   *
   * @param ctx the parse tree
   */
  void exitPrimitiveType(CommonParserRulesParser.PrimitiveTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#voidType}.
   *
   * @param ctx the parse tree
   */
  void enterVoidType(CommonParserRulesParser.VoidTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#voidType}.
   *
   * @param ctx the parse tree
   */
  void exitVoidType(CommonParserRulesParser.VoidTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#initExpr}.
   *
   * @param ctx the parse tree
   */
  void enterInitExpr(CommonParserRulesParser.InitExprContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#initExpr}.
   *
   * @param ctx the parse tree
   */
  void exitInitExpr(CommonParserRulesParser.InitExprContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#nonPrimitiveType}.
   *
   * @param ctx the parse tree
   */
  void enterNonPrimitiveType(CommonParserRulesParser.NonPrimitiveTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#nonPrimitiveType}.
   *
   * @param ctx the parse tree
   */
  void exitNonPrimitiveType(CommonParserRulesParser.NonPrimitiveTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#classSuite}.
   *
   * @param ctx the parse tree
   */
  void enterClassSuite(CommonParserRulesParser.ClassSuiteContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#classSuite}.
   *
   * @param ctx the parse tree
   */
  void exitClassSuite(CommonParserRulesParser.ClassSuiteContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#classDef}.
   *
   * @param ctx the parse tree
   */
  void enterClassDef(CommonParserRulesParser.ClassDefContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#classDef}.
   *
   * @param ctx the parse tree
   */
  void exitClassDef(CommonParserRulesParser.ClassDefContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#classCtor}.
   *
   * @param ctx the parse tree
   */
  void enterClassCtor(CommonParserRulesParser.ClassCtorContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#classCtor}.
   *
   * @param ctx the parse tree
   */
  void exitClassCtor(CommonParserRulesParser.ClassCtorContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#returnType}.
   *
   * @param ctx the parse tree
   */
  void enterReturnType(CommonParserRulesParser.ReturnTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#returnType}.
   *
   * @param ctx the parse tree
   */
  void exitReturnType(CommonParserRulesParser.ReturnTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#funcSuite}.
   *
   * @param ctx the parse tree
   */
  void enterFuncSuite(CommonParserRulesParser.FuncSuiteContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#funcSuite}.
   *
   * @param ctx the parse tree
   */
  void exitFuncSuite(CommonParserRulesParser.FuncSuiteContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#funcDef}.
   *
   * @param ctx the parse tree
   */
  void enterFuncDef(CommonParserRulesParser.FuncDefContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#funcDef}.
   *
   * @param ctx the parse tree
   */
  void exitFuncDef(CommonParserRulesParser.FuncDefContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#funcDecl}.
   *
   * @param ctx the parse tree
   */
  void enterFuncDecl(CommonParserRulesParser.FuncDeclContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#funcDecl}.
   *
   * @param ctx the parse tree
   */
  void exitFuncDecl(CommonParserRulesParser.FuncDeclContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#funcCall}.
   *
   * @param ctx the parse tree
   */
  void enterFuncCall(CommonParserRulesParser.FuncCallContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#funcCall}.
   *
   * @param ctx the parse tree
   */
  void exitFuncCall(CommonParserRulesParser.FuncCallContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#paramDefList}.
   *
   * @param ctx the parse tree
   */
  void enterParamDefList(CommonParserRulesParser.ParamDefListContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#paramDefList}.
   *
   * @param ctx the parse tree
   */
  void exitParamDefList(CommonParserRulesParser.ParamDefListContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#paramInputList}.
   *
   * @param ctx the parse tree
   */
  void enterParamInputList(CommonParserRulesParser.ParamInputListContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#paramInputList}.
   *
   * @param ctx the parse tree
   */
  void exitParamInputList(CommonParserRulesParser.ParamInputListContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#lambdaDef}.
   *
   * @param ctx the parse tree
   */
  void enterLambdaDef(CommonParserRulesParser.LambdaDefContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#lambdaDef}.
   *
   * @param ctx the parse tree
   */
  void exitLambdaDef(CommonParserRulesParser.LambdaDefContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#lambdaCall}.
   *
   * @param ctx the parse tree
   */
  void enterLambdaCall(CommonParserRulesParser.LambdaCallContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#lambdaCall}.
   *
   * @param ctx the parse tree
   */
  void exitLambdaCall(CommonParserRulesParser.LambdaCallContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#basicExpr}.
   *
   * @param ctx the parse tree
   */
  void enterBasicExpr(CommonParserRulesParser.BasicExprContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#basicExpr}.
   *
   * @param ctx the parse tree
   */
  void exitBasicExpr(CommonParserRulesParser.BasicExprContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#exprStmt}.
   *
   * @param ctx the parse tree
   */
  void enterExprStmt(CommonParserRulesParser.ExprStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#exprStmt}.
   *
   * @param ctx the parse tree
   */
  void exitExprStmt(CommonParserRulesParser.ExprStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#prefixOps}.
   *
   * @param ctx the parse tree
   */
  void enterPrefixOps(CommonParserRulesParser.PrefixOpsContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#prefixOps}.
   *
   * @param ctx the parse tree
   */
  void exitPrefixOps(CommonParserRulesParser.PrefixOpsContext ctx);

  /**
   * Enter a parse tree produced by the {@code prefixExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterPrefixExpr(CommonParserRulesParser.PrefixExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code prefixExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitPrefixExpr(CommonParserRulesParser.PrefixExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code memberAccess}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterMemberAccess(CommonParserRulesParser.MemberAccessContext ctx);

  /**
   * Exit a parse tree produced by the {@code memberAccess}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitMemberAccess(CommonParserRulesParser.MemberAccessContext ctx);

  /**
   * Enter a parse tree produced by the {@code methodAccess}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterMethodAccess(CommonParserRulesParser.MethodAccessContext ctx);

  /**
   * Exit a parse tree produced by the {@code methodAccess}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitMethodAccess(CommonParserRulesParser.MethodAccessContext ctx);

  /**
   * Enter a parse tree produced by the {@code suffixExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterSuffixExpr(CommonParserRulesParser.SuffixExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code suffixExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitSuffixExpr(CommonParserRulesParser.SuffixExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code priorExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterPriorExpr(CommonParserRulesParser.PriorExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code priorExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitPriorExpr(CommonParserRulesParser.PriorExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code binaryExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterBinaryExpr(CommonParserRulesParser.BinaryExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code binaryExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitBinaryExpr(CommonParserRulesParser.BinaryExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code arrayAccess}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterArrayAccess(CommonParserRulesParser.ArrayAccessContext ctx);

  /**
   * Exit a parse tree produced by the {@code arrayAccess}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitArrayAccess(CommonParserRulesParser.ArrayAccessContext ctx);

  /**
   * Enter a parse tree produced by the {@code atom}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterAtom(CommonParserRulesParser.AtomContext ctx);

  /**
   * Exit a parse tree produced by the {@code atom}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitAtom(CommonParserRulesParser.AtomContext ctx);

  /**
   * Enter a parse tree produced by the {@code assignExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterAssignExpr(CommonParserRulesParser.AssignExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code assignExpr}
   * labeled alternative in {@link CommonParserRulesParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitAssignExpr(CommonParserRulesParser.AssignExprContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#assignUnit}.
   *
   * @param ctx the parse tree
   */
  void enterAssignUnit(CommonParserRulesParser.AssignUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#assignUnit}.
   *
   * @param ctx the parse tree
   */
  void exitAssignUnit(CommonParserRulesParser.AssignUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#varDecl}.
   *
   * @param ctx the parse tree
   */
  void enterVarDecl(CommonParserRulesParser.VarDeclContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#varDecl}.
   *
   * @param ctx the parse tree
   */
  void exitVarDecl(CommonParserRulesParser.VarDeclContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#blockSuite}.
   *
   * @param ctx the parse tree
   */
  void enterBlockSuite(CommonParserRulesParser.BlockSuiteContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#blockSuite}.
   *
   * @param ctx the parse tree
   */
  void exitBlockSuite(CommonParserRulesParser.BlockSuiteContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#jumpStmt}.
   *
   * @param ctx the parse tree
   */
  void enterJumpStmt(CommonParserRulesParser.JumpStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#jumpStmt}.
   *
   * @param ctx the parse tree
   */
  void exitJumpStmt(CommonParserRulesParser.JumpStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#condStmt}.
   *
   * @param ctx the parse tree
   */
  void enterCondStmt(CommonParserRulesParser.CondStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#condStmt}.
   *
   * @param ctx the parse tree
   */
  void exitCondStmt(CommonParserRulesParser.CondStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#whileStmt}.
   *
   * @param ctx the parse tree
   */
  void enterWhileStmt(CommonParserRulesParser.WhileStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#whileStmt}.
   *
   * @param ctx the parse tree
   */
  void exitWhileStmt(CommonParserRulesParser.WhileStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#forInitUnit}.
   *
   * @param ctx the parse tree
   */
  void enterForInitUnit(CommonParserRulesParser.ForInitUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#forInitUnit}.
   *
   * @param ctx the parse tree
   */
  void exitForInitUnit(CommonParserRulesParser.ForInitUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#forCondUnit}.
   *
   * @param ctx the parse tree
   */
  void enterForCondUnit(CommonParserRulesParser.ForCondUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#forCondUnit}.
   *
   * @param ctx the parse tree
   */
  void exitForCondUnit(CommonParserRulesParser.ForCondUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#forStepUnit}.
   *
   * @param ctx the parse tree
   */
  void enterForStepUnit(CommonParserRulesParser.ForStepUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#forStepUnit}.
   *
   * @param ctx the parse tree
   */
  void exitForStepUnit(CommonParserRulesParser.ForStepUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link CommonParserRulesParser#forStmt}.
   *
   * @param ctx the parse tree
   */
  void enterForStmt(CommonParserRulesParser.ForStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link CommonParserRulesParser#forStmt}.
   *
   * @param ctx the parse tree
   */
  void exitForStmt(CommonParserRulesParser.ForStmtContext ctx);
}