// Generated from java-escape by ANTLR 4.11.1
package parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MeteorParserParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface MeteorParserVisitor<T> extends ParseTreeVisitor<T> {
  /**
   * Visit a parse tree produced by {@link MeteorParserParser#prog}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitProg(MeteorParserParser.ProgContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitStmt(MeteorParserParser.StmtContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#decl}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitDecl(MeteorParserParser.DeclContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#def}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitDef(MeteorParserParser.DefContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#varType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVarType(MeteorParserParser.VarTypeContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#primitiveType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPrimitiveType(MeteorParserParser.PrimitiveTypeContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#voidType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVoidType(MeteorParserParser.VoidTypeContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#initExpr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitInitExpr(MeteorParserParser.InitExprContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#nonPrimitiveType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitNonPrimitiveType(MeteorParserParser.NonPrimitiveTypeContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#classSuite}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitClassSuite(MeteorParserParser.ClassSuiteContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#classDef}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitClassDef(MeteorParserParser.ClassDefContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#classCtor}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitClassCtor(MeteorParserParser.ClassCtorContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#returnType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitReturnType(MeteorParserParser.ReturnTypeContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#funcSuite}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitFuncSuite(MeteorParserParser.FuncSuiteContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#funcDef}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitFuncDef(MeteorParserParser.FuncDefContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#funcDecl}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitFuncDecl(MeteorParserParser.FuncDeclContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#funcCall}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitFuncCall(MeteorParserParser.FuncCallContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#paramDefList}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitParamDefList(MeteorParserParser.ParamDefListContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#paramInputList}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitParamInputList(MeteorParserParser.ParamInputListContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#lambdaDef}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitLambdaDef(MeteorParserParser.LambdaDefContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#lambdaCall}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitLambdaCall(MeteorParserParser.LambdaCallContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#basicExpr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBasicExpr(MeteorParserParser.BasicExprContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#exprStmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitExprStmt(MeteorParserParser.ExprStmtContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#prefixOps}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPrefixOps(MeteorParserParser.PrefixOpsContext ctx);

  /**
   * Visit a parse tree produced by the {@code prefixExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPrefixExpr(MeteorParserParser.PrefixExprContext ctx);

  /**
   * Visit a parse tree produced by the {@code memberAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMemberAccess(MeteorParserParser.MemberAccessContext ctx);

  /**
   * Visit a parse tree produced by the {@code methodAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMethodAccess(MeteorParserParser.MethodAccessContext ctx);

  /**
   * Visit a parse tree produced by the {@code suffixExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitSuffixExpr(MeteorParserParser.SuffixExprContext ctx);

  /**
   * Visit a parse tree produced by the {@code priorExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitPriorExpr(MeteorParserParser.PriorExprContext ctx);

  /**
   * Visit a parse tree produced by the {@code binaryExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBinaryExpr(MeteorParserParser.BinaryExprContext ctx);

  /**
   * Visit a parse tree produced by the {@code arrayAccess}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitArrayAccess(MeteorParserParser.ArrayAccessContext ctx);

  /**
   * Visit a parse tree produced by the {@code atom}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAtom(MeteorParserParser.AtomContext ctx);

  /**
   * Visit a parse tree produced by the {@code assignExpr}
   * labeled alternative in {@link MeteorParserParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAssignExpr(MeteorParserParser.AssignExprContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#assignUnit}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAssignUnit(MeteorParserParser.AssignUnitContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#varDecl}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVarDecl(MeteorParserParser.VarDeclContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#blockSuite}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBlockSuite(MeteorParserParser.BlockSuiteContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#jumpStmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitJumpStmt(MeteorParserParser.JumpStmtContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#condStmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitCondStmt(MeteorParserParser.CondStmtContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#whileStmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitWhileStmt(MeteorParserParser.WhileStmtContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#forInitUnit}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitForInitUnit(MeteorParserParser.ForInitUnitContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#forCondUnit}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitForCondUnit(MeteorParserParser.ForCondUnitContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#forStepUnit}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitForStepUnit(MeteorParserParser.ForStepUnitContext ctx);

  /**
   * Visit a parse tree produced by {@link MeteorParserParser#forStmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitForStmt(MeteorParserParser.ForStmtContext ctx);
}