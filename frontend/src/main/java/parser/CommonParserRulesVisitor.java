package parser;// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CommonParserRulesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CommonParserRulesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(CommonParserRulesParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(CommonParserRulesParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(CommonParserRulesParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef(CommonParserRulesParser.DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(CommonParserRulesParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(CommonParserRulesParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#voidType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidType(CommonParserRulesParser.VoidTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#initExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitExpr(CommonParserRulesParser.InitExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#nonPrimitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonPrimitiveType(CommonParserRulesParser.NonPrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#classSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassSuite(CommonParserRulesParser.ClassSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(CommonParserRulesParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#classCtor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassCtor(CommonParserRulesParser.ClassCtorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(CommonParserRulesParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#funcSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncSuite(CommonParserRulesParser.FuncSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(CommonParserRulesParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#funcDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDecl(CommonParserRulesParser.FuncDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(CommonParserRulesParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#paramDefList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDefList(CommonParserRulesParser.ParamDefListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#paramInputList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamInputList(CommonParserRulesParser.ParamInputListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#lambdaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaDef(CommonParserRulesParser.LambdaDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#lambdaCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaCall(CommonParserRulesParser.LambdaCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#basicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicExpr(CommonParserRulesParser.BasicExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(CommonParserRulesParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#prefixOps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixOps(CommonParserRulesParser.PrefixOpsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(CommonParserRulesParser.PrefixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccess(CommonParserRulesParser.MemberAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodAccess}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodAccess(CommonParserRulesParser.MethodAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffixExpr(CommonParserRulesParser.SuffixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code priorExpr}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPriorExpr(CommonParserRulesParser.PriorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(CommonParserRulesParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(CommonParserRulesParser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atom}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(CommonParserRulesParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link CommonParserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(CommonParserRulesParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#assignUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignUnit(CommonParserRulesParser.AssignUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(CommonParserRulesParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#blockSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockSuite(CommonParserRulesParser.BlockSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#jumpStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStmt(CommonParserRulesParser.JumpStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#condStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondStmt(CommonParserRulesParser.CondStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(CommonParserRulesParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#forInitUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitUnit(CommonParserRulesParser.ForInitUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#forCondUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCondUnit(CommonParserRulesParser.ForCondUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#forStepUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStepUnit(CommonParserRulesParser.ForStepUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonParserRulesParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(CommonParserRulesParser.ForStmtContext ctx);
}