package frontend.parser;
// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MeteorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MeteorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MeteorParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(MeteorParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#progBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgBlock(MeteorParser.ProgBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MeteorParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#fieldSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldSuite(MeteorParser.FieldSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(MeteorParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef(MeteorParser.DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(MeteorParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(MeteorParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(MeteorParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#voidType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidType(MeteorParser.VoidTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#nonPrimitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonPrimitiveType(MeteorParser.NonPrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#classBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBlock(MeteorParser.ClassBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MeteorParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#classCtor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassCtor(MeteorParser.ClassCtorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(MeteorParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#funcBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncBlock(MeteorParser.FuncBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(MeteorParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#paramDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDecl(MeteorParser.ParamDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#paramDeclList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDeclList(MeteorParser.ParamDeclListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#paramInputList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamInputList(MeteorParser.ParamInputListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#lambdaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaDef(MeteorParser.LambdaDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#basicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicExpr(MeteorParser.BasicExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#short}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShort(MeteorParser.ShortContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#prefixOps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixOps(MeteorParser.PrefixOpsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#bracketedExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedExpr(MeteorParser.BracketedExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(MeteorParser.PrefixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccess(MeteorParser.MemberAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodAccess(MeteorParser.MethodAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffixExpr(MeteorParser.SuffixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code priorExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPriorExpr(MeteorParser.PriorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MeteorParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(MeteorParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(MeteorParser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atom}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(MeteorParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code initExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitExpr(MeteorParser.InitExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaCall}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaCall(MeteorParser.LambdaCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MeteorParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#assignUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignUnit(MeteorParser.AssignUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(MeteorParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#jump}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJump(MeteorParser.JumpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#simpleBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleBlock(MeteorParser.SimpleBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#extendedSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtendedSuite(MeteorParser.ExtendedSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#condSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondSuite(MeteorParser.CondSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#whileSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileSuite(MeteorParser.WhileSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#forInitUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitUnit(MeteorParser.ForInitUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#forCondUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCondUnit(MeteorParser.ForCondUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#forStepUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStepUnit(MeteorParser.ForStepUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MeteorParser#forSuite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForSuite(MeteorParser.ForSuiteContext ctx);
}