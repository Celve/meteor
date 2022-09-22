// Generated from java-escape by ANTLR 4.11.1
package parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MeteorParserParser}.
 */
public interface MeteorParserListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link MeteorParserParser#prog}.
   *
   * @param ctx the parse tree
   */
  void enterProg(MeteorParserParser.ProgContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#prog}.
   *
   * @param ctx the parse tree
   */
  void exitProg(MeteorParserParser.ProgContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterStmt(MeteorParserParser.StmtContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitStmt(MeteorParserParser.StmtContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#decl}.
   *
   * @param ctx the parse tree
   */
  void enterDecl(MeteorParserParser.DeclContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#decl}.
   *
   * @param ctx the parse tree
   */
  void exitDecl(MeteorParserParser.DeclContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#def}.
   *
   * @param ctx the parse tree
   */
  void enterDef(MeteorParserParser.DefContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#def}.
   *
   * @param ctx the parse tree
   */
  void exitDef(MeteorParserParser.DefContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#varType}.
   *
   * @param ctx the parse tree
   */
  void enterVarType(MeteorParserParser.VarTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#varType}.
   *
   * @param ctx the parse tree
   */
  void exitVarType(MeteorParserParser.VarTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#primitiveType}.
   *
   * @param ctx the parse tree
   */
  void enterPrimitiveType(MeteorParserParser.PrimitiveTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#primitiveType}.
   *
   * @param ctx the parse tree
   */
  void exitPrimitiveType(MeteorParserParser.PrimitiveTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#voidType}.
   *
   * @param ctx the parse tree
   */
  void enterVoidType(MeteorParserParser.VoidTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#voidType}.
   *
   * @param ctx the parse tree
   */
  void exitVoidType(MeteorParserParser.VoidTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#initExpr}.
   *
   * @param ctx the parse tree
   */
  void enterInitExpr(MeteorParserParser.InitExprContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#initExpr}.
   *
   * @param ctx the parse tree
   */
  void exitInitExpr(MeteorParserParser.InitExprContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#nonPrimitiveType}.
   *
   * @param ctx the parse tree
   */
  void enterNonPrimitiveType(MeteorParserParser.NonPrimitiveTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#nonPrimitiveType}.
   *
   * @param ctx the parse tree
   */
  void exitNonPrimitiveType(MeteorParserParser.NonPrimitiveTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#classSuite}.
   *
   * @param ctx the parse tree
   */
  void enterClassSuite(MeteorParserParser.ClassSuiteContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#classSuite}.
   *
   * @param ctx the parse tree
   */
  void exitClassSuite(MeteorParserParser.ClassSuiteContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#classDef}.
   *
   * @param ctx the parse tree
   */
  void enterClassDef(MeteorParserParser.ClassDefContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#classDef}.
   *
   * @param ctx the parse tree
   */
  void exitClassDef(MeteorParserParser.ClassDefContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#classCtor}.
   *
   * @param ctx the parse tree
   */
  void enterClassCtor(MeteorParserParser.ClassCtorContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#classCtor}.
   *
   * @param ctx the parse tree
   */
  void exitClassCtor(MeteorParserParser.ClassCtorContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#returnType}.
   *
   * @param ctx the parse tree
   */
  void enterReturnType(MeteorParserParser.ReturnTypeContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#returnType}.
   *
   * @param ctx the parse tree
   */
  void exitReturnType(MeteorParserParser.ReturnTypeContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#funcSuite}.
   *
   * @param ctx the parse tree
   */
  void enterFuncSuite(MeteorParserParser.FuncSuiteContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#funcSuite}.
   *
   * @param ctx the parse tree
   */
  void exitFuncSuite(MeteorParserParser.FuncSuiteContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#funcDef}.
   *
   * @param ctx the parse tree
   */
  void enterFuncDef(MeteorParserParser.FuncDefContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#funcDef}.
   *
   * @param ctx the parse tree
   */
  void exitFuncDef(MeteorParserParser.FuncDefContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#funcDecl}.
   *
   * @param ctx the parse tree
   */
  void enterFuncDecl(MeteorParserParser.FuncDeclContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#funcDecl}.
   *
   * @param ctx the parse tree
   */
  void exitFuncDecl(MeteorParserParser.FuncDeclContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#funcCall}.
   *
   * @param ctx the parse tree
   */
  void enterFuncCall(MeteorParserParser.FuncCallContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#funcCall}.
   *
   * @param ctx the parse tree
   */
  void exitFuncCall(MeteorParserParser.FuncCallContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#paramDefList}.
   *
   * @param ctx the parse tree
   */
  void enterParamDefList(MeteorParserParser.ParamDefListContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#paramDefList}.
   *
   * @param ctx the parse tree
   */
  void exitParamDefList(MeteorParserParser.ParamDefListContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#paramInputList}.
   *
   * @param ctx the parse tree
   */
  void enterParamInputList(MeteorParserParser.ParamInputListContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#paramInputList}.
   *
   * @param ctx the parse tree
   */
  void exitParamInputList(MeteorParserParser.ParamInputListContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#lambdaDef}.
   *
   * @param ctx the parse tree
   */
  void enterLambdaDef(MeteorParserParser.LambdaDefContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#lambdaDef}.
   *
   * @param ctx the parse tree
   */
  void exitLambdaDef(MeteorParserParser.LambdaDefContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#lambdaCall}.
   *
   * @param ctx the parse tree
   */
  void enterLambdaCall(MeteorParserParser.LambdaCallContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#lambdaCall}.
   *
   * @param ctx the parse tree
   */
  void exitLambdaCall(MeteorParserParser.LambdaCallContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#basicExpr}.
   *
   * @param ctx the parse tree
   */
  void enterBasicExpr(MeteorParserParser.BasicExprContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#basicExpr}.
   *
   * @param ctx the parse tree
   */
  void exitBasicExpr(MeteorParserParser.BasicExprContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#exprStmt}.
   *
   * @param ctx the parse tree
   */
  void enterExprStmt(MeteorParserParser.ExprStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#exprStmt}.
   *
   * @param ctx the parse tree
   */
  void exitExprStmt(MeteorParserParser.ExprStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#prefixOps}.
   *
   * @param ctx the parse tree
   */
  void enterPrefixOps(MeteorParserParser.PrefixOpsContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#prefixOps}.
   *
   * @param ctx the parse tree
   */
  void exitPrefixOps(MeteorParserParser.PrefixOpsContext ctx);

  /**
   * Enter a parse tree produced by the {@code prefixExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterPrefixExpr(MeteorParserParser.PrefixExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code prefixExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitPrefixExpr(MeteorParserParser.PrefixExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code memberAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterMemberAccess(MeteorParserParser.MemberAccessContext ctx);

  /**
   * Exit a parse tree produced by the {@code memberAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitMemberAccess(MeteorParserParser.MemberAccessContext ctx);

  /**
   * Enter a parse tree produced by the {@code methodAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterMethodAccess(MeteorParserParser.MethodAccessContext ctx);

  /**
   * Exit a parse tree produced by the {@code methodAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitMethodAccess(MeteorParserParser.MethodAccessContext ctx);

  /**
   * Enter a parse tree produced by the {@code suffixExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterSuffixExpr(MeteorParserParser.SuffixExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code suffixExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitSuffixExpr(MeteorParserParser.SuffixExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code priorExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterPriorExpr(MeteorParserParser.PriorExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code priorExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitPriorExpr(MeteorParserParser.PriorExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code binaryExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterBinaryExpr(MeteorParserParser.BinaryExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code binaryExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitBinaryExpr(MeteorParserParser.BinaryExprContext ctx);

  /**
   * Enter a parse tree produced by the {@code arrayAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterArrayAccess(MeteorParserParser.ArrayAccessContext ctx);

  /**
   * Exit a parse tree produced by the {@code arrayAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitArrayAccess(MeteorParserParser.ArrayAccessContext ctx);

  /**
   * Enter a parse tree produced by the {@code atom}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterAtom(MeteorParserParser.AtomContext ctx);

  /**
   * Exit a parse tree produced by the {@code atom}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitAtom(MeteorParserParser.AtomContext ctx);

  /**
   * Enter a parse tree produced by the {@code assignExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterAssignExpr(MeteorParserParser.AssignExprContext ctx);

  /**
   * Exit a parse tree produced by the {@code assignExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitAssignExpr(MeteorParserParser.AssignExprContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#assignUnit}.
   *
   * @param ctx the parse tree
   */
  void enterAssignUnit(MeteorParserParser.AssignUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#assignUnit}.
   *
   * @param ctx the parse tree
   */
  void exitAssignUnit(MeteorParserParser.AssignUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#varDecl}.
   *
   * @param ctx the parse tree
   */
  void enterVarDecl(MeteorParserParser.VarDeclContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#varDecl}.
   *
   * @param ctx the parse tree
   */
  void exitVarDecl(MeteorParserParser.VarDeclContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#blockSuite}.
   *
   * @param ctx the parse tree
   */
  void enterBlockSuite(MeteorParserParser.BlockSuiteContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#blockSuite}.
   *
   * @param ctx the parse tree
   */
  void exitBlockSuite(MeteorParserParser.BlockSuiteContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#jumpStmt}.
   *
   * @param ctx the parse tree
   */
  void enterJumpStmt(MeteorParserParser.JumpStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#jumpStmt}.
   *
   * @param ctx the parse tree
   */
  void exitJumpStmt(MeteorParserParser.JumpStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#condStmt}.
   *
   * @param ctx the parse tree
   */
  void enterCondStmt(MeteorParserParser.CondStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#condStmt}.
   *
   * @param ctx the parse tree
   */
  void exitCondStmt(MeteorParserParser.CondStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#whileStmt}.
   *
   * @param ctx the parse tree
   */
  void enterWhileStmt(MeteorParserParser.WhileStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#whileStmt}.
   *
   * @param ctx the parse tree
   */
  void exitWhileStmt(MeteorParserParser.WhileStmtContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#forInitUnit}.
   *
   * @param ctx the parse tree
   */
  void enterForInitUnit(MeteorParserParser.ForInitUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#forInitUnit}.
   *
   * @param ctx the parse tree
   */
  void exitForInitUnit(MeteorParserParser.ForInitUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#forCondUnit}.
   *
   * @param ctx the parse tree
   */
  void enterForCondUnit(MeteorParserParser.ForCondUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#forCondUnit}.
   *
   * @param ctx the parse tree
   */
  void exitForCondUnit(MeteorParserParser.ForCondUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#forStepUnit}.
   *
   * @param ctx the parse tree
   */
  void enterForStepUnit(MeteorParserParser.ForStepUnitContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#forStepUnit}.
   *
   * @param ctx the parse tree
   */
  void exitForStepUnit(MeteorParserParser.ForStepUnitContext ctx);

  /**
   * Enter a parse tree produced by {@link MeteorParserParser#forStmt}.
   *
   * @param ctx the parse tree
   */
  void enterForStmt(MeteorParserParser.ForStmtContext ctx);

  /**
   * Exit a parse tree produced by {@link MeteorParserParser#forStmt}.
   *
   * @param ctx the parse tree
   */
  void exitForStmt(MeteorParserParser.ForStmtContext ctx);
}