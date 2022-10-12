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
	 * Enter a parse tree produced by {@link MeteorParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(MeteorParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(MeteorParser.AtomContext ctx);
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
	 * Enter a parse tree produced by {@link MeteorParser#lambdaCall}.
	 * @param ctx the parse tree
	 */
	void enterLambdaCall(MeteorParser.LambdaCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#lambdaCall}.
	 * @param ctx the parse tree
	 */
	void exitLambdaCall(MeteorParser.LambdaCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(MeteorParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(MeteorParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#priorExpr}.
	 * @param ctx the parse tree
	 */
	void enterPriorExpr(MeteorParser.PriorExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#priorExpr}.
	 * @param ctx the parse tree
	 */
	void exitPriorExpr(MeteorParser.PriorExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(MeteorParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(MeteorParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccess(MeteorParser.MemberAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccess(MeteorParser.MemberAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExprRelay}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExprRelay(MeteorParser.PrimaryExprRelayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExprRelay}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExprRelay(MeteorParser.PrimaryExprRelayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixIncrement}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void enterSuffixIncrement(MeteorParser.SuffixIncrementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixIncrement}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void exitSuffixIncrement(MeteorParser.SuffixIncrementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(MeteorParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(MeteorParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodCall}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(MeteorParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodCall}
	 * labeled alternative in {@link MeteorParser#suffixExpr}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(MeteorParser.MethodCallContext ctx);
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
	 * Enter a parse tree produced by the {@code prefixIncrement}
	 * labeled alternative in {@link MeteorParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrefixIncrement(MeteorParser.PrefixIncrementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixIncrement}
	 * labeled alternative in {@link MeteorParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrefixIncrement(MeteorParser.PrefixIncrementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MeteorParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MeteorParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MeteorParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MeteorParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExprRelay}
	 * labeled alternative in {@link MeteorParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExprRelay(MeteorParser.SuffixExprRelayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExprRelay}
	 * labeled alternative in {@link MeteorParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExprRelay(MeteorParser.SuffixExprRelayContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#mulOps}.
	 * @param ctx the parse tree
	 */
	void enterMulOps(MeteorParser.MulOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#mulOps}.
	 * @param ctx the parse tree
	 */
	void exitMulOps(MeteorParser.MulOpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(MeteorParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(MeteorParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#addOps}.
	 * @param ctx the parse tree
	 */
	void enterAddOps(MeteorParser.AddOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#addOps}.
	 * @param ctx the parse tree
	 */
	void exitAddOps(MeteorParser.AddOpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(MeteorParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(MeteorParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#shiftOps}.
	 * @param ctx the parse tree
	 */
	void enterShiftOps(MeteorParser.ShiftOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#shiftOps}.
	 * @param ctx the parse tree
	 */
	void exitShiftOps(MeteorParser.ShiftOpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpr(MeteorParser.ShiftExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpr(MeteorParser.ShiftExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#cmpOps}.
	 * @param ctx the parse tree
	 */
	void enterCmpOps(MeteorParser.CmpOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#cmpOps}.
	 * @param ctx the parse tree
	 */
	void exitCmpOps(MeteorParser.CmpOpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#cmpExpr}.
	 * @param ctx the parse tree
	 */
	void enterCmpExpr(MeteorParser.CmpExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#cmpExpr}.
	 * @param ctx the parse tree
	 */
	void exitCmpExpr(MeteorParser.CmpExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#equalOps}.
	 * @param ctx the parse tree
	 */
	void enterEqualOps(MeteorParser.EqualOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#equalOps}.
	 * @param ctx the parse tree
	 */
	void exitEqualOps(MeteorParser.EqualOpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#equalExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualExpr(MeteorParser.EqualExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#equalExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualExpr(MeteorParser.EqualExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#bitwiseAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseAndExpr(MeteorParser.BitwiseAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#bitwiseAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseAndExpr(MeteorParser.BitwiseAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#bitwiseXorExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseXorExpr(MeteorParser.BitwiseXorExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#bitwiseXorExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseXorExpr(MeteorParser.BitwiseXorExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#bitwiseOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseOrExpr(MeteorParser.BitwiseOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#bitwiseOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseOrExpr(MeteorParser.BitwiseOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpr(MeteorParser.LogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpr(MeteorParser.LogicalAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpr(MeteorParser.LogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpr(MeteorParser.LogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#assignExpr}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MeteorParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#assignExpr}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MeteorParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MeteorParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MeteorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MeteorParser.ExprContext ctx);
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
