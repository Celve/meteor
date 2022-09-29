package frontend.parser;
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
	 * Enter a parse tree produced by {@link MeteorParser#progBlock}.
	 * @param ctx the parse tree
	 */
	void enterProgBlock(MeteorParser.ProgBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#progBlock}.
	 * @param ctx the parse tree
	 */
	void exitProgBlock(MeteorParser.ProgBlockContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#fieldSuite}.
	 * @param ctx the parse tree
	 */
	void enterFieldSuite(MeteorParser.FieldSuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#fieldSuite}.
	 * @param ctx the parse tree
	 */
	void exitFieldSuite(MeteorParser.FieldSuiteContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(MeteorParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(MeteorParser.ClassTypeContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#classBlock}.
	 * @param ctx the parse tree
	 */
	void enterClassBlock(MeteorParser.ClassBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#classBlock}.
	 * @param ctx the parse tree
	 */
	void exitClassBlock(MeteorParser.ClassBlockContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#funcBlock}.
	 * @param ctx the parse tree
	 */
	void enterFuncBlock(MeteorParser.FuncBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#funcBlock}.
	 * @param ctx the parse tree
	 */
	void exitFuncBlock(MeteorParser.FuncBlockContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#paramDecl}.
	 * @param ctx the parse tree
	 */
	void enterParamDecl(MeteorParser.ParamDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#paramDecl}.
	 * @param ctx the parse tree
	 */
	void exitParamDecl(MeteorParser.ParamDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#paramDeclList}.
	 * @param ctx the parse tree
	 */
	void enterParamDeclList(MeteorParser.ParamDeclListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#paramDeclList}.
	 * @param ctx the parse tree
	 */
	void exitParamDeclList(MeteorParser.ParamDeclListContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#short}.
	 * @param ctx the parse tree
	 */
	void enterShort(MeteorParser.ShortContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#short}.
	 * @param ctx the parse tree
	 */
	void exitShort(MeteorParser.ShortContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#bracketedExpr}.
	 * @param ctx the parse tree
	 */
	void enterBracketedExpr(MeteorParser.BracketedExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#bracketedExpr}.
	 * @param ctx the parse tree
	 */
	void exitBracketedExpr(MeteorParser.BracketedExprContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#simpleBlock}.
	 * @param ctx the parse tree
	 */
	void enterSimpleBlock(MeteorParser.SimpleBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#simpleBlock}.
	 * @param ctx the parse tree
	 */
	void exitSimpleBlock(MeteorParser.SimpleBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#extendedSuite}.
	 * @param ctx the parse tree
	 */
	void enterExtendedSuite(MeteorParser.ExtendedSuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#extendedSuite}.
	 * @param ctx the parse tree
	 */
	void exitExtendedSuite(MeteorParser.ExtendedSuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#condSuite}.
	 * @param ctx the parse tree
	 */
	void enterCondSuite(MeteorParser.CondSuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#condSuite}.
	 * @param ctx the parse tree
	 */
	void exitCondSuite(MeteorParser.CondSuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#whileSuite}.
	 * @param ctx the parse tree
	 */
	void enterWhileSuite(MeteorParser.WhileSuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#whileSuite}.
	 * @param ctx the parse tree
	 */
	void exitWhileSuite(MeteorParser.WhileSuiteContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#forSuite}.
	 * @param ctx the parse tree
	 */
	void enterForSuite(MeteorParser.ForSuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#forSuite}.
	 * @param ctx the parse tree
	 */
	void exitForSuite(MeteorParser.ForSuiteContext ctx);
}
