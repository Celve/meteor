package parser;
// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MeteorParser}.
 */
public interface MeteorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MeteorParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MeteorParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MeteorParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(MeteorParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(MeteorParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MeteorParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MeteorParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(MeteorParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(MeteorParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(MeteorParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(MeteorParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDef(MeteorParser.DefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDef(MeteorParser.DefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#varType}.
	 * @param ctx the parse tree
	 */
	void enterVarType(MeteorParser.VarTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#varType}.
	 * @param ctx the parse tree
	 */
	void exitVarType(MeteorParser.VarTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(MeteorParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(MeteorParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#voidType}.
	 * @param ctx the parse tree
	 */
	void enterVoidType(MeteorParser.VoidTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#voidType}.
	 * @param ctx the parse tree
	 */
	void exitVoidType(MeteorParser.VoidTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#nonPrimitiveType}.
	 * @param ctx the parse tree
	 */
	void enterNonPrimitiveType(MeteorParser.NonPrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#nonPrimitiveType}.
	 * @param ctx the parse tree
	 */
	void exitNonPrimitiveType(MeteorParser.NonPrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#classSuite}.
	 * @param ctx the parse tree
	 */
	void enterClassSuite(MeteorParser.ClassSuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#classSuite}.
	 * @param ctx the parse tree
	 */
	void exitClassSuite(MeteorParser.ClassSuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MeteorParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MeteorParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#classCtor}.
	 * @param ctx the parse tree
	 */
	void enterClassCtor(MeteorParser.ClassCtorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#classCtor}.
	 * @param ctx the parse tree
	 */
	void exitClassCtor(MeteorParser.ClassCtorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(MeteorParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(MeteorParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#funcSuite}.
	 * @param ctx the parse tree
	 */
	void enterFuncSuite(MeteorParser.FuncSuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#funcSuite}.
	 * @param ctx the parse tree
	 */
	void exitFuncSuite(MeteorParser.FuncSuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(MeteorParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(MeteorParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void enterFuncDecl(MeteorParser.FuncDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void exitFuncDecl(MeteorParser.FuncDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#paramDefList}.
	 * @param ctx the parse tree
	 */
	void enterParamDefList(MeteorParser.ParamDefListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#paramDefList}.
	 * @param ctx the parse tree
	 */
	void exitParamDefList(MeteorParser.ParamDefListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#paramInputList}.
	 * @param ctx the parse tree
	 */
	void enterParamInputList(MeteorParser.ParamInputListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#paramInputList}.
	 * @param ctx the parse tree
	 */
	void exitParamInputList(MeteorParser.ParamInputListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#lambdaDef}.
	 * @param ctx the parse tree
	 */
	void enterLambdaDef(MeteorParser.LambdaDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#lambdaDef}.
	 * @param ctx the parse tree
	 */
	void exitLambdaDef(MeteorParser.LambdaDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#basicExpr}.
	 * @param ctx the parse tree
	 */
	void enterBasicExpr(MeteorParser.BasicExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#basicExpr}.
	 * @param ctx the parse tree
	 */
	void exitBasicExpr(MeteorParser.BasicExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(MeteorParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(MeteorParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#prefixOps}.
	 * @param ctx the parse tree
	 */
	void enterPrefixOps(MeteorParser.PrefixOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#prefixOps}.
	 * @param ctx the parse tree
	 */
	void exitPrefixOps(MeteorParser.PrefixOpsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(MeteorParser.PrefixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(MeteorParser.PrefixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccess(MeteorParser.MemberAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccess(MeteorParser.MemberAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMethodAccess(MeteorParser.MethodAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMethodAccess(MeteorParser.MethodAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExpr(MeteorParser.SuffixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExpr(MeteorParser.SuffixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code priorExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPriorExpr(MeteorParser.PriorExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code priorExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPriorExpr(MeteorParser.PriorExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MeteorParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MeteorParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(MeteorParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(MeteorParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(MeteorParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(MeteorParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atom}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtom(MeteorParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atom}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtom(MeteorParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code initExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInitExpr(MeteorParser.InitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code initExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInitExpr(MeteorParser.InitExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambdaCall}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLambdaCall(MeteorParser.LambdaCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambdaCall}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLambdaCall(MeteorParser.LambdaCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MeteorParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MeteorParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#assignUnit}.
	 * @param ctx the parse tree
	 */
	void enterAssignUnit(MeteorParser.AssignUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#assignUnit}.
	 * @param ctx the parse tree
	 */
	void exitAssignUnit(MeteorParser.AssignUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(MeteorParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(MeteorParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#jump}.
	 * @param ctx the parse tree
	 */
	void enterJump(MeteorParser.JumpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#jump}.
	 * @param ctx the parse tree
	 */
	void exitJump(MeteorParser.JumpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#extendedBlock}.
	 * @param ctx the parse tree
	 */
	void enterExtendedBlock(MeteorParser.ExtendedBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#extendedBlock}.
	 * @param ctx the parse tree
	 */
	void exitExtendedBlock(MeteorParser.ExtendedBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(MeteorParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(MeteorParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#while}.
	 * @param ctx the parse tree
	 */
	void enterWhile(MeteorParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#while}.
	 * @param ctx the parse tree
	 */
	void exitWhile(MeteorParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#forInitUnit}.
	 * @param ctx the parse tree
	 */
	void enterForInitUnit(MeteorParser.ForInitUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#forInitUnit}.
	 * @param ctx the parse tree
	 */
	void exitForInitUnit(MeteorParser.ForInitUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#forCondUnit}.
	 * @param ctx the parse tree
	 */
	void enterForCondUnit(MeteorParser.ForCondUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#forCondUnit}.
	 * @param ctx the parse tree
	 */
	void exitForCondUnit(MeteorParser.ForCondUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#forStepUnit}.
	 * @param ctx the parse tree
	 */
	void enterForStepUnit(MeteorParser.ForStepUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#forStepUnit}.
	 * @param ctx the parse tree
	 */
	void exitForStepUnit(MeteorParser.ForStepUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#for}.
	 * @param ctx the parse tree
	 */
	void enterFor(MeteorParser.ForContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#for}.
	 * @param ctx the parse tree
	 */
	void exitFor(MeteorParser.ForContext ctx);
}
