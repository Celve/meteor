// Generated from /Users/celve/Repositories/Courses/University/meteor/src/grammar/CommonParserRules.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommonParserRulesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, Add=4, Sub=5, Mul=6, Div=7, Mod=8, Greater=9, 
		Less=10, GreaterEqual=11, LessEqual=12, Equal=13, NotEqual=14, LogicalAnd=15, 
		LogicalOr=16, LogicalNot=17, RightShift=18, LeftShift=19, BitwiseAnd=20, 
		BitwiseOr=21, BitwiseXor=22, BitwiseNot=23, Assign=24, Increment=25, Decrement=26, 
		Brackets=27, LeftBracket=28, RightBracket=29, LeftParen=30, RightParen=31, 
		LeftBrace=32, RightBrace=33, LineComment=34, BlockComment=35, Void=36, 
		Bool=37, Int=38, String=39, New=40, Class=41, Null=42, True=43, False=44, 
		This=45, If=46, Else=47, For=48, While=49, Break=50, Continue=51, Return=52, 
		IntegerLiteral=53, StringLiteral=54, Escape=55, WhiteSpace=56, NewLine=57, 
		Access=58, Id=59;
	public static final int
		RULE_prog = 0, RULE_stmt = 1, RULE_decl = 2, RULE_def = 3, RULE_varType = 4, 
		RULE_primitiveType = 5, RULE_voidType = 6, RULE_initExpr = 7, RULE_nonPrimitiveType = 8, 
		RULE_classSuite = 9, RULE_classDef = 10, RULE_classCtor = 11, RULE_returnType = 12, 
		RULE_funcSuite = 13, RULE_funcDef = 14, RULE_funcDecl = 15, RULE_funcCall = 16, 
		RULE_paramDefList = 17, RULE_paramInputList = 18, RULE_lambdaDef = 19, 
		RULE_lambdaCall = 20, RULE_basicExpr = 21, RULE_exprStmt = 22, RULE_prefixOps = 23, 
		RULE_expr = 24, RULE_assignUnit = 25, RULE_varDecl = 26, RULE_blockSuite = 27, 
		RULE_jumpStmt = 28, RULE_condStmt = 29, RULE_whileStmt = 30, RULE_forInitUnit = 31, 
		RULE_forCondUnit = 32, RULE_forStepUnit = 33, RULE_forStmt = 34;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "stmt", "decl", "def", "varType", "primitiveType", "voidType", 
			"initExpr", "nonPrimitiveType", "classSuite", "classDef", "classCtor", 
			"returnType", "funcSuite", "funcDef", "funcDecl", "funcCall", "paramDefList", 
			"paramInputList", "lambdaDef", "lambdaCall", "basicExpr", "exprStmt", 
			"prefixOps", "expr", "assignUnit", "varDecl", "blockSuite", "jumpStmt", 
			"condStmt", "whileStmt", "forInitUnit", "forCondUnit", "forStepUnit", 
			"forStmt"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "'->'", "';'", "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", 
			"'<'", "'>='", "'<='", "'=='", "'!='", "'&&'", "'||'", "'!'", "'>>'", 
			"'<<'", "'&'", "'|'", "'^'", "'~'", "'='", "'++'", "'--'", "'[]'", "'['", 
			"']'", "'('", "')'", "'{'", "'}'", null, null, "'void'", "'bool'", "'int'", 
			"'string'", "'new'", "'class'", "'null'", "'true'", "'false'", "'this'", 
			"'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", "'return'", 
			null, null, null, null, null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "Add", "Sub", "Mul", "Div", "Mod", "Greater", 
			"Less", "GreaterEqual", "LessEqual", "Equal", "NotEqual", "LogicalAnd", 
			"LogicalOr", "LogicalNot", "RightShift", "LeftShift", "BitwiseAnd", "BitwiseOr", 
			"BitwiseXor", "BitwiseNot", "Assign", "Increment", "Decrement", "Brackets", 
			"LeftBracket", "RightBracket", "LeftParen", "RightParen", "LeftBrace", 
			"RightBrace", "LineComment", "BlockComment", "Void", "Bool", "Int", "String", 
			"New", "Class", "Null", "True", "False", "This", "If", "Else", "For", 
			"While", "Break", "Continue", "Return", "IntegerLiteral", "StringLiteral", 
			"Escape", "WhiteSpace", "NewLine", "Access", "Id"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CommonParserRules.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CommonParserRulesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftBracket) | (1L << LeftParen) | (1L << LeftBrace) | (1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << New) | (1L << Class) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
				{
				{
				setState(70);
				stmt();
				}
				}
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public JumpStmtContext jumpStmt() {
			return getRuleContext(JumpStmtContext.class,0);
		}
		public ExprStmtContext exprStmt() {
			return getRuleContext(ExprStmtContext.class,0);
		}
		public CondStmtContext condStmt() {
			return getRuleContext(CondStmtContext.class,0);
		}
		public ForStmtContext forStmt() {
			return getRuleContext(ForStmtContext.class,0);
		}
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public DefContext def() {
			return getRuleContext(DefContext.class,0);
		}
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public FuncSuiteContext funcSuite() {
			return getRuleContext(FuncSuiteContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stmt);
		try {
			setState(84);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				jumpStmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				exprStmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(78);
				condStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(79);
				forStmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(80);
				whileStmt();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(81);
				def();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(82);
				decl();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(83);
				funcSuite();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public FuncDeclContext funcDecl() {
			return getRuleContext(FuncDeclContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decl);
		try {
			setState(88);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(86);
				varDecl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				funcDecl();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefContext extends ParserRuleContext {
		public ClassDefContext classDef() {
			return getRuleContext(ClassDefContext.class,0);
		}
		public FuncDefContext funcDef() {
			return getRuleContext(FuncDefContext.class,0);
		}
		public LambdaDefContext lambdaDef() {
			return getRuleContext(LambdaDefContext.class,0);
		}
		public DefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def; }
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_def);
		try {
			setState(93);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Class:
				enterOuterAlt(_localctx, 1);
				{
				setState(90);
				classDef();
				}
				break;
			case Void:
			case Bool:
			case Int:
			case String:
			case Id:
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				funcDef();
				}
				break;
			case LeftBracket:
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				lambdaDef();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public NonPrimitiveTypeContext nonPrimitiveType() {
			return getRuleContext(NonPrimitiveTypeContext.class,0);
		}
		public List<TerminalNode> Brackets() { return getTokens(CommonParserRulesParser.Brackets); }
		public TerminalNode Brackets(int i) {
			return getToken(CommonParserRulesParser.Brackets, i);
		}
		public VarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varType; }
	}

	public final VarTypeContext varType() throws RecognitionException {
		VarTypeContext _localctx = new VarTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_varType);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
				{
				setState(95);
				primitiveType();
				}
				break;
			case Id:
				{
				setState(96);
				nonPrimitiveType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(102);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(99);
					match(Brackets);
					}
					} 
				}
				setState(104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode Bool() { return getToken(CommonParserRulesParser.Bool, 0); }
		public TerminalNode Int() { return getToken(CommonParserRulesParser.Int, 0); }
		public TerminalNode String() { return getToken(CommonParserRulesParser.String, 0); }
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoidTypeContext extends ParserRuleContext {
		public TerminalNode Void() { return getToken(CommonParserRulesParser.Void, 0); }
		public VoidTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voidType; }
	}

	public final VoidTypeContext voidType() throws RecognitionException {
		VoidTypeContext _localctx = new VoidTypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_voidType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(Void);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitExprContext extends ParserRuleContext {
		public TerminalNode New() { return getToken(CommonParserRulesParser.New, 0); }
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<TerminalNode> LeftBracket() { return getTokens(CommonParserRulesParser.LeftBracket); }
		public TerminalNode LeftBracket(int i) {
			return getToken(CommonParserRulesParser.LeftBracket, i);
		}
		public List<TerminalNode> RightBracket() { return getTokens(CommonParserRulesParser.RightBracket); }
		public TerminalNode RightBracket(int i) {
			return getToken(CommonParserRulesParser.RightBracket, i);
		}
		public List<TerminalNode> Brackets() { return getTokens(CommonParserRulesParser.Brackets); }
		public TerminalNode Brackets(int i) {
			return getToken(CommonParserRulesParser.Brackets, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public InitExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initExpr; }
	}

	public final InitExprContext initExpr() throws RecognitionException {
		InitExprContext _localctx = new InitExprContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_initExpr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(New);
			setState(110);
			varType();
			setState(118);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(111);
					match(LeftBracket);
					setState(113);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftParen) | (1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
						{
						setState(112);
						expr(0);
						}
					}

					setState(115);
					match(RightBracket);
					}
					} 
				}
				setState(120);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(124);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(121);
					match(Brackets);
					}
					} 
				}
				setState(126);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonPrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public NonPrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonPrimitiveType; }
	}

	public final NonPrimitiveTypeContext nonPrimitiveType() throws RecognitionException {
		NonPrimitiveTypeContext _localctx = new NonPrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_nonPrimitiveType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(Id);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassSuiteContext extends ParserRuleContext {
		public TerminalNode LeftBrace() { return getToken(CommonParserRulesParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(CommonParserRulesParser.RightBrace, 0); }
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public List<ClassCtorContext> classCtor() {
			return getRuleContexts(ClassCtorContext.class);
		}
		public ClassCtorContext classCtor(int i) {
			return getRuleContext(ClassCtorContext.class,i);
		}
		public ClassSuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classSuite; }
	}

	public final ClassSuiteContext classSuite() throws RecognitionException {
		ClassSuiteContext _localctx = new ClassSuiteContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_classSuite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(LeftBrace);
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << Id))) != 0)) {
				{
				setState(132);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(130);
					decl();
					}
					break;
				case 2:
					{
					setState(131);
					classCtor();
					}
					break;
				}
				}
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(137);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDefContext extends ParserRuleContext {
		public Token className;
		public TerminalNode Class() { return getToken(CommonParserRulesParser.Class, 0); }
		public ClassSuiteContext classSuite() {
			return getRuleContext(ClassSuiteContext.class,0);
		}
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_classDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(Class);
			setState(140);
			((ClassDefContext)_localctx).className = match(Id);
			setState(141);
			classSuite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassCtorContext extends ParserRuleContext {
		public Token classId;
		public TerminalNode LeftBrace() { return getToken(CommonParserRulesParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(CommonParserRulesParser.RightBrace, 0); }
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public ParamDefListContext paramDefList() {
			return getRuleContext(ParamDefListContext.class,0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public ClassCtorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classCtor; }
	}

	public final ClassCtorContext classCtor() throws RecognitionException {
		ClassCtorContext _localctx = new ClassCtorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_classCtor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			((ClassCtorContext)_localctx).classId = match(Id);
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LeftParen) {
				{
				setState(144);
				paramDefList();
				}
			}

			setState(147);
			match(LeftBrace);
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftBracket) | (1L << LeftParen) | (1L << LeftBrace) | (1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << New) | (1L << Class) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
				{
				{
				setState(148);
				stmt();
				}
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(154);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public NonPrimitiveTypeContext nonPrimitiveType() {
			return getRuleContext(NonPrimitiveTypeContext.class,0);
		}
		public VoidTypeContext voidType() {
			return getRuleContext(VoidTypeContext.class,0);
		}
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnType);
		try {
			setState(159);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
				enterOuterAlt(_localctx, 1);
				{
				setState(156);
				primitiveType();
				}
				break;
			case Id:
				enterOuterAlt(_localctx, 2);
				{
				setState(157);
				nonPrimitiveType();
				}
				break;
			case Void:
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				voidType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncSuiteContext extends ParserRuleContext {
		public TerminalNode LeftBrace() { return getToken(CommonParserRulesParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(CommonParserRulesParser.RightBrace, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public FuncSuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcSuite; }
	}

	public final FuncSuiteContext funcSuite() throws RecognitionException {
		FuncSuiteContext _localctx = new FuncSuiteContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_funcSuite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(LeftBrace);
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftBracket) | (1L << LeftParen) | (1L << LeftBrace) | (1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << New) | (1L << Class) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
				{
				{
				setState(162);
				stmt();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(168);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncDefContext extends ParserRuleContext {
		public Token funcId;
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public ParamDefListContext paramDefList() {
			return getRuleContext(ParamDefListContext.class,0);
		}
		public FuncSuiteContext funcSuite() {
			return getRuleContext(FuncSuiteContext.class,0);
		}
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public FuncDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDef; }
	}

	public final FuncDefContext funcDef() throws RecognitionException {
		FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_funcDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			returnType();
			setState(171);
			((FuncDefContext)_localctx).funcId = match(Id);
			setState(172);
			paramDefList();
			setState(173);
			funcSuite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncDeclContext extends ParserRuleContext {
		public Token funcId;
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public ParamDefListContext paramDefList() {
			return getRuleContext(ParamDefListContext.class,0);
		}
		public FuncSuiteContext funcSuite() {
			return getRuleContext(FuncSuiteContext.class,0);
		}
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public FuncDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDecl; }
	}

	public final FuncDeclContext funcDecl() throws RecognitionException {
		FuncDeclContext _localctx = new FuncDeclContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_funcDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			returnType();
			setState(176);
			((FuncDeclContext)_localctx).funcId = match(Id);
			setState(177);
			paramDefList();
			setState(178);
			funcSuite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncCallContext extends ParserRuleContext {
		public Token funcId;
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_funcCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(180);
			((FuncCallContext)_localctx).funcId = match(Id);
			}
			setState(181);
			paramInputList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamDefListContext extends ParserRuleContext {
		public TerminalNode LeftParen() { return getToken(CommonParserRulesParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(CommonParserRulesParser.RightParen, 0); }
		public List<VarTypeContext> varType() {
			return getRuleContexts(VarTypeContext.class);
		}
		public VarTypeContext varType(int i) {
			return getRuleContext(VarTypeContext.class,i);
		}
		public List<TerminalNode> Id() { return getTokens(CommonParserRulesParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(CommonParserRulesParser.Id, i);
		}
		public ParamDefListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramDefList; }
	}

	public final ParamDefListContext paramDefList() throws RecognitionException {
		ParamDefListContext _localctx = new ParamDefListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_paramDefList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(LeftParen);
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Id))) != 0)) {
				{
				setState(184);
				varType();
				setState(185);
				match(Id);
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(186);
					match(T__0);
					setState(187);
					varType();
					setState(188);
					match(Id);
					}
					}
					setState(194);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(197);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamInputListContext extends ParserRuleContext {
		public TerminalNode LeftParen() { return getToken(CommonParserRulesParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(CommonParserRulesParser.RightParen, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ParamInputListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramInputList; }
	}

	public final ParamInputListContext paramInputList() throws RecognitionException {
		ParamInputListContext _localctx = new ParamInputListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_paramInputList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			match(LeftParen);
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftParen) | (1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
				{
				setState(200);
				expr(0);
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(201);
					match(T__0);
					setState(202);
					expr(0);
					}
					}
					setState(207);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(210);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LambdaDefContext extends ParserRuleContext {
		public Token op;
		public TerminalNode LeftBracket() { return getToken(CommonParserRulesParser.LeftBracket, 0); }
		public TerminalNode RightBracket() { return getToken(CommonParserRulesParser.RightBracket, 0); }
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public TerminalNode LeftBrace() { return getToken(CommonParserRulesParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(CommonParserRulesParser.RightBrace, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public TerminalNode BitwiseAnd() { return getToken(CommonParserRulesParser.BitwiseAnd, 0); }
		public LambdaDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaDef; }
	}

	public final LambdaDefContext lambdaDef() throws RecognitionException {
		LambdaDefContext _localctx = new LambdaDefContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_lambdaDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(LeftBracket);
			setState(214);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitwiseAnd) {
				{
				setState(213);
				((LambdaDefContext)_localctx).op = match(BitwiseAnd);
				}
			}

			setState(216);
			match(RightBracket);
			setState(217);
			paramInputList();
			setState(218);
			match(T__1);
			setState(219);
			match(LeftBrace);
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftBracket) | (1L << LeftParen) | (1L << LeftBrace) | (1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << New) | (1L << Class) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
				{
				{
				setState(220);
				stmt();
				}
				}
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(226);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LambdaCallContext extends ParserRuleContext {
		public LambdaDefContext lambdaDef() {
			return getRuleContext(LambdaDefContext.class,0);
		}
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public LambdaCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaCall; }
	}

	public final LambdaCallContext lambdaCall() throws RecognitionException {
		LambdaCallContext _localctx = new LambdaCallContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_lambdaCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			lambdaDef();
			setState(229);
			paramInputList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BasicExprContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(CommonParserRulesParser.IntegerLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(CommonParserRulesParser.StringLiteral, 0); }
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public TerminalNode This() { return getToken(CommonParserRulesParser.This, 0); }
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public InitExprContext initExpr() {
			return getRuleContext(InitExprContext.class,0);
		}
		public TerminalNode True() { return getToken(CommonParserRulesParser.True, 0); }
		public TerminalNode False() { return getToken(CommonParserRulesParser.False, 0); }
		public TerminalNode Null() { return getToken(CommonParserRulesParser.Null, 0); }
		public BasicExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicExpr; }
	}

	public final BasicExprContext basicExpr() throws RecognitionException {
		BasicExprContext _localctx = new BasicExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_basicExpr);
		try {
			setState(240);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(231);
				match(IntegerLiteral);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(232);
				match(StringLiteral);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(233);
				match(Id);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(234);
				match(This);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(235);
				funcCall();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(236);
				initExpr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(237);
				match(True);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(238);
				match(False);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(239);
				match(Null);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprStmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStmt; }
	}

	public final ExprStmtContext exprStmt() throws RecognitionException {
		ExprStmtContext _localctx = new ExprStmtContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_exprStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			expr(0);
			setState(243);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixOpsContext extends ParserRuleContext {
		public TerminalNode Increment() { return getToken(CommonParserRulesParser.Increment, 0); }
		public TerminalNode Decrement() { return getToken(CommonParserRulesParser.Decrement, 0); }
		public TerminalNode Add() { return getToken(CommonParserRulesParser.Add, 0); }
		public TerminalNode Sub() { return getToken(CommonParserRulesParser.Sub, 0); }
		public TerminalNode LogicalNot() { return getToken(CommonParserRulesParser.LogicalNot, 0); }
		public TerminalNode BitwiseNot() { return getToken(CommonParserRulesParser.BitwiseNot, 0); }
		public PrefixOpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixOps; }
	}

	public final PrefixOpsContext prefixOps() throws RecognitionException {
		PrefixOpsContext _localctx = new PrefixOpsContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_prefixOps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public Token op;
		public Token methodName;
		public Token classMember;
		public TerminalNode LeftParen() { return getToken(CommonParserRulesParser.LeftParen, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RightParen() { return getToken(CommonParserRulesParser.RightParen, 0); }
		public BasicExprContext basicExpr() {
			return getRuleContext(BasicExprContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public PrefixOpsContext prefixOps() {
			return getRuleContext(PrefixOpsContext.class,0);
		}
		public TerminalNode Mul() { return getToken(CommonParserRulesParser.Mul, 0); }
		public TerminalNode Div() { return getToken(CommonParserRulesParser.Div, 0); }
		public TerminalNode Mod() { return getToken(CommonParserRulesParser.Mod, 0); }
		public TerminalNode Add() { return getToken(CommonParserRulesParser.Add, 0); }
		public TerminalNode Sub() { return getToken(CommonParserRulesParser.Sub, 0); }
		public TerminalNode LeftShift() { return getToken(CommonParserRulesParser.LeftShift, 0); }
		public TerminalNode RightShift() { return getToken(CommonParserRulesParser.RightShift, 0); }
		public TerminalNode Less() { return getToken(CommonParserRulesParser.Less, 0); }
		public TerminalNode LessEqual() { return getToken(CommonParserRulesParser.LessEqual, 0); }
		public TerminalNode Greater() { return getToken(CommonParserRulesParser.Greater, 0); }
		public TerminalNode GreaterEqual() { return getToken(CommonParserRulesParser.GreaterEqual, 0); }
		public TerminalNode Equal() { return getToken(CommonParserRulesParser.Equal, 0); }
		public TerminalNode NotEqual() { return getToken(CommonParserRulesParser.NotEqual, 0); }
		public TerminalNode BitwiseAnd() { return getToken(CommonParserRulesParser.BitwiseAnd, 0); }
		public TerminalNode BitwiseXor() { return getToken(CommonParserRulesParser.BitwiseXor, 0); }
		public TerminalNode BitwiseOr() { return getToken(CommonParserRulesParser.BitwiseOr, 0); }
		public TerminalNode LogicalAnd() { return getToken(CommonParserRulesParser.LogicalAnd, 0); }
		public TerminalNode LogicalOr() { return getToken(CommonParserRulesParser.LogicalOr, 0); }
		public TerminalNode Assign() { return getToken(CommonParserRulesParser.Assign, 0); }
		public TerminalNode Access() { return getToken(CommonParserRulesParser.Access, 0); }
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public List<TerminalNode> LeftBracket() { return getTokens(CommonParserRulesParser.LeftBracket); }
		public TerminalNode LeftBracket(int i) {
			return getToken(CommonParserRulesParser.LeftBracket, i);
		}
		public List<TerminalNode> RightBracket() { return getTokens(CommonParserRulesParser.RightBracket); }
		public TerminalNode RightBracket(int i) {
			return getToken(CommonParserRulesParser.RightBracket, i);
		}
		public TerminalNode Increment() { return getToken(CommonParserRulesParser.Increment, 0); }
		public TerminalNode Decrement() { return getToken(CommonParserRulesParser.Decrement, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(248);
				match(LeftParen);
				setState(249);
				expr(0);
				setState(250);
				match(RightParen);
				}
				break;
			case 2:
				{
				setState(252);
				basicExpr();
				}
				break;
			case 3:
				{
				setState(253);
				funcCall();
				}
				break;
			case 4:
				{
				setState(254);
				prefixOps();
				setState(255);
				expr(12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(312);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(310);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(259);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(260);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Mul) | (1L << Div) | (1L << Mod))) != 0)) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(261);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(262);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(263);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Add || _la==Sub) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(264);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(265);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(266);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==RightShift || _la==LeftShift) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(267);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(268);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(269);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Greater) | (1L << Less) | (1L << GreaterEqual) | (1L << LessEqual))) != 0)) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(270);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(271);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(272);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Equal || _la==NotEqual) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(273);
						expr(8);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(274);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(275);
						match(BitwiseAnd);
						setState(276);
						expr(7);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(277);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(278);
						match(BitwiseXor);
						setState(279);
						expr(6);
						}
						break;
					case 8:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(280);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(281);
						match(BitwiseOr);
						setState(282);
						expr(5);
						}
						break;
					case 9:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(283);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(284);
						match(LogicalAnd);
						setState(285);
						expr(4);
						}
						break;
					case 10:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(286);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(287);
						match(LogicalOr);
						setState(288);
						expr(3);
						}
						break;
					case 11:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(289);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(290);
						match(Assign);
						setState(291);
						expr(2);
						}
						break;
					case 12:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(292);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(293);
						match(Access);
						{
						setState(294);
						((ExprContext)_localctx).methodName = match(Id);
						}
						setState(295);
						paramInputList();
						}
						break;
					case 13:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(296);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(297);
						match(Access);
						{
						setState(298);
						((ExprContext)_localctx).classMember = match(Id);
						}
						}
						break;
					case 14:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(299);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(304); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(300);
								match(LeftBracket);
								setState(301);
								expr(0);
								setState(302);
								match(RightBracket);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(306); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 15:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(308);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(309);
						_la = _input.LA(1);
						if ( !(_la==Increment || _la==Decrement) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(314);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AssignUnitContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public TerminalNode Assign() { return getToken(CommonParserRulesParser.Assign, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignUnit; }
	}

	public final AssignUnitContext assignUnit() throws RecognitionException {
		AssignUnitContext _localctx = new AssignUnitContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_assignUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			match(Id);
			setState(318);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(316);
				match(Assign);
				setState(317);
				expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclContext extends ParserRuleContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<AssignUnitContext> assignUnit() {
			return getRuleContexts(AssignUnitContext.class);
		}
		public AssignUnitContext assignUnit(int i) {
			return getRuleContext(AssignUnitContext.class,i);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			varType();
			setState(321);
			assignUnit();
			setState(326);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(322);
				match(T__0);
				setState(323);
				assignUnit();
				}
				}
				setState(328);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(329);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockSuiteContext extends ParserRuleContext {
		public TerminalNode LeftBrace() { return getToken(CommonParserRulesParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(CommonParserRulesParser.RightBrace, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockSuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockSuite; }
	}

	public final BlockSuiteContext blockSuite() throws RecognitionException {
		BlockSuiteContext _localctx = new BlockSuiteContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_blockSuite);
		int _la;
		try {
			setState(340);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(331);
				match(LeftBrace);
				setState(335);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftBracket) | (1L << LeftParen) | (1L << LeftBrace) | (1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << New) | (1L << Class) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
					{
					{
					setState(332);
					stmt();
					}
					}
					setState(337);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(338);
				match(RightBrace);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(339);
				stmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStmtContext extends ParserRuleContext {
		public TerminalNode Return() { return getToken(CommonParserRulesParser.Return, 0); }
		public TerminalNode Break() { return getToken(CommonParserRulesParser.Break, 0); }
		public TerminalNode Continue() { return getToken(CommonParserRulesParser.Continue, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public JumpStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStmt; }
	}

	public final JumpStmtContext jumpStmt() throws RecognitionException {
		JumpStmtContext _localctx = new JumpStmtContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_jumpStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Return:
				{
				setState(342);
				match(Return);
				setState(344);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftParen) | (1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
					{
					setState(343);
					expr(0);
					}
				}

				}
				break;
			case Break:
				{
				setState(346);
				match(Break);
				}
				break;
			case Continue:
				{
				setState(347);
				match(Continue);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(350);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondStmtContext extends ParserRuleContext {
		public TerminalNode If() { return getToken(CommonParserRulesParser.If, 0); }
		public TerminalNode LeftParen() { return getToken(CommonParserRulesParser.LeftParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(CommonParserRulesParser.RightParen, 0); }
		public List<BlockSuiteContext> blockSuite() {
			return getRuleContexts(BlockSuiteContext.class);
		}
		public BlockSuiteContext blockSuite(int i) {
			return getRuleContext(BlockSuiteContext.class,i);
		}
		public TerminalNode Else() { return getToken(CommonParserRulesParser.Else, 0); }
		public CondStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condStmt; }
	}

	public final CondStmtContext condStmt() throws RecognitionException {
		CondStmtContext _localctx = new CondStmtContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_condStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			match(If);
			setState(353);
			match(LeftParen);
			setState(354);
			expr(0);
			setState(355);
			match(RightParen);
			setState(356);
			blockSuite();
			setState(359);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(357);
				match(Else);
				setState(358);
				blockSuite();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStmtContext extends ParserRuleContext {
		public TerminalNode While() { return getToken(CommonParserRulesParser.While, 0); }
		public TerminalNode LeftParen() { return getToken(CommonParserRulesParser.LeftParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(CommonParserRulesParser.RightParen, 0); }
		public BlockSuiteContext blockSuite() {
			return getRuleContext(BlockSuiteContext.class,0);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			match(While);
			setState(362);
			match(LeftParen);
			setState(363);
			expr(0);
			setState(364);
			match(RightParen);
			setState(365);
			blockSuite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForInitUnitContext extends ParserRuleContext {
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForInitUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInitUnit; }
	}

	public final ForInitUnitContext forInitUnit() throws RecognitionException {
		ForInitUnitContext _localctx = new ForInitUnitContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_forInitUnit);
		try {
			setState(374);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Add:
			case Sub:
			case LogicalNot:
			case BitwiseNot:
			case Increment:
			case Decrement:
			case LeftParen:
			case Bool:
			case Int:
			case String:
			case New:
			case Null:
			case True:
			case False:
			case This:
			case IntegerLiteral:
			case StringLiteral:
			case Id:
				enterOuterAlt(_localctx, 1);
				{
				setState(371);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(367);
					varDecl();
					}
					break;
				case 2:
					{
					setState(368);
					expr(0);
					setState(369);
					match(T__2);
					}
					break;
				}
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(373);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForCondUnitContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForCondUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forCondUnit; }
	}

	public final ForCondUnitContext forCondUnit() throws RecognitionException {
		ForCondUnitContext _localctx = new ForCondUnitContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_forCondUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftParen) | (1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
				{
				setState(376);
				expr(0);
				}
			}

			setState(379);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStepUnitContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForStepUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStepUnit; }
	}

	public final ForStepUnitContext forStepUnit() throws RecognitionException {
		ForStepUnitContext _localctx = new ForStepUnitContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_forStepUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LogicalNot) | (1L << BitwiseNot) | (1L << Increment) | (1L << Decrement) | (1L << LeftParen) | (1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << IntegerLiteral) | (1L << StringLiteral) | (1L << Id))) != 0)) {
				{
				setState(381);
				expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStmtContext extends ParserRuleContext {
		public TerminalNode For() { return getToken(CommonParserRulesParser.For, 0); }
		public TerminalNode LeftParen() { return getToken(CommonParserRulesParser.LeftParen, 0); }
		public ForInitUnitContext forInitUnit() {
			return getRuleContext(ForInitUnitContext.class,0);
		}
		public ForCondUnitContext forCondUnit() {
			return getRuleContext(ForCondUnitContext.class,0);
		}
		public ForStepUnitContext forStepUnit() {
			return getRuleContext(ForStepUnitContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(CommonParserRulesParser.RightParen, 0); }
		public BlockSuiteContext blockSuite() {
			return getRuleContext(BlockSuiteContext.class,0);
		}
		public ForStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStmt; }
	}

	public final ForStmtContext forStmt() throws RecognitionException {
		ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_forStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			match(For);
			setState(385);
			match(LeftParen);
			setState(386);
			forInitUnit();
			setState(387);
			forCondUnit();
			setState(388);
			forStepUnit();
			setState(389);
			match(RightParen);
			setState(390);
			blockSuite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 24:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		case 11:
			return precpred(_ctx, 17);
		case 12:
			return precpred(_ctx, 16);
		case 13:
			return precpred(_ctx, 15);
		case 14:
			return precpred(_ctx, 13);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3=\u018b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\7\2J\n\2\f\2\16\2M\13\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\5\3W\n\3\3\4\3\4\5\4[\n\4\3\5\3\5\3\5\5\5`\n\5\3\6\3\6"+
		"\5\6d\n\6\3\6\7\6g\n\6\f\6\16\6j\13\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t"+
		"\5\tt\n\t\3\t\7\tw\n\t\f\t\16\tz\13\t\3\t\7\t}\n\t\f\t\16\t\u0080\13\t"+
		"\3\n\3\n\3\13\3\13\3\13\7\13\u0087\n\13\f\13\16\13\u008a\13\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\f\3\r\3\r\5\r\u0094\n\r\3\r\3\r\7\r\u0098\n\r\f\r\16"+
		"\r\u009b\13\r\3\r\3\r\3\16\3\16\3\16\5\16\u00a2\n\16\3\17\3\17\7\17\u00a6"+
		"\n\17\f\17\16\17\u00a9\13\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\7"+
		"\23\u00c1\n\23\f\23\16\23\u00c4\13\23\5\23\u00c6\n\23\3\23\3\23\3\24\3"+
		"\24\3\24\3\24\7\24\u00ce\n\24\f\24\16\24\u00d1\13\24\5\24\u00d3\n\24\3"+
		"\24\3\24\3\25\3\25\5\25\u00d9\n\25\3\25\3\25\3\25\3\25\3\25\7\25\u00e0"+
		"\n\25\f\25\16\25\u00e3\13\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\5\27\u00f3\n\27\3\30\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0104\n\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\6\32\u0133\n\32\r\32\16\32\u0134\3\32\3\32\7\32\u0139\n\32"+
		"\f\32\16\32\u013c\13\32\3\33\3\33\3\33\5\33\u0141\n\33\3\34\3\34\3\34"+
		"\3\34\7\34\u0147\n\34\f\34\16\34\u014a\13\34\3\34\3\34\3\35\3\35\7\35"+
		"\u0150\n\35\f\35\16\35\u0153\13\35\3\35\3\35\5\35\u0157\n\35\3\36\3\36"+
		"\5\36\u015b\n\36\3\36\3\36\5\36\u015f\n\36\3\36\3\36\3\37\3\37\3\37\3"+
		"\37\3\37\3\37\3\37\5\37\u016a\n\37\3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\5!\u0176"+
		"\n!\3!\5!\u0179\n!\3\"\5\"\u017c\n\"\3\"\3\"\3#\5#\u0181\n#\3$\3$\3$\3"+
		"$\3$\3$\3$\3$\3$\2\3\62%\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&("+
		"*,.\60\62\64\668:<>@BDF\2\n\3\2\')\6\2\6\7\23\23\31\31\33\34\3\2\b\n\3"+
		"\2\6\7\3\2\24\25\3\2\13\16\3\2\17\20\3\2\33\34\2\u01ab\2K\3\2\2\2\4V\3"+
		"\2\2\2\6Z\3\2\2\2\b_\3\2\2\2\nc\3\2\2\2\fk\3\2\2\2\16m\3\2\2\2\20o\3\2"+
		"\2\2\22\u0081\3\2\2\2\24\u0083\3\2\2\2\26\u008d\3\2\2\2\30\u0091\3\2\2"+
		"\2\32\u00a1\3\2\2\2\34\u00a3\3\2\2\2\36\u00ac\3\2\2\2 \u00b1\3\2\2\2\""+
		"\u00b6\3\2\2\2$\u00b9\3\2\2\2&\u00c9\3\2\2\2(\u00d6\3\2\2\2*\u00e6\3\2"+
		"\2\2,\u00f2\3\2\2\2.\u00f4\3\2\2\2\60\u00f7\3\2\2\2\62\u0103\3\2\2\2\64"+
		"\u013d\3\2\2\2\66\u0142\3\2\2\28\u0156\3\2\2\2:\u015e\3\2\2\2<\u0162\3"+
		"\2\2\2>\u016b\3\2\2\2@\u0178\3\2\2\2B\u017b\3\2\2\2D\u0180\3\2\2\2F\u0182"+
		"\3\2\2\2HJ\5\4\3\2IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2L\3\3\2\2\2"+
		"MK\3\2\2\2NW\5:\36\2OW\5.\30\2PW\5<\37\2QW\5F$\2RW\5> \2SW\5\b\5\2TW\5"+
		"\6\4\2UW\5\34\17\2VN\3\2\2\2VO\3\2\2\2VP\3\2\2\2VQ\3\2\2\2VR\3\2\2\2V"+
		"S\3\2\2\2VT\3\2\2\2VU\3\2\2\2W\5\3\2\2\2X[\5\66\34\2Y[\5 \21\2ZX\3\2\2"+
		"\2ZY\3\2\2\2[\7\3\2\2\2\\`\5\26\f\2]`\5\36\20\2^`\5(\25\2_\\\3\2\2\2_"+
		"]\3\2\2\2_^\3\2\2\2`\t\3\2\2\2ad\5\f\7\2bd\5\22\n\2ca\3\2\2\2cb\3\2\2"+
		"\2dh\3\2\2\2eg\7\35\2\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2i\13\3"+
		"\2\2\2jh\3\2\2\2kl\t\2\2\2l\r\3\2\2\2mn\7&\2\2n\17\3\2\2\2op\7*\2\2px"+
		"\5\n\6\2qs\7\36\2\2rt\5\62\32\2sr\3\2\2\2st\3\2\2\2tu\3\2\2\2uw\7\37\2"+
		"\2vq\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y~\3\2\2\2zx\3\2\2\2{}\7\35"+
		"\2\2|{\3\2\2\2}\u0080\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\21\3\2\2\2\u0080"+
		"~\3\2\2\2\u0081\u0082\7=\2\2\u0082\23\3\2\2\2\u0083\u0088\7\"\2\2\u0084"+
		"\u0087\5\6\4\2\u0085\u0087\5\30\r\2\u0086\u0084\3\2\2\2\u0086\u0085\3"+
		"\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089"+
		"\u008b\3\2\2\2\u008a\u0088\3\2\2\2\u008b\u008c\7#\2\2\u008c\25\3\2\2\2"+
		"\u008d\u008e\7+\2\2\u008e\u008f\7=\2\2\u008f\u0090\5\24\13\2\u0090\27"+
		"\3\2\2\2\u0091\u0093\7=\2\2\u0092\u0094\5$\23\2\u0093\u0092\3\2\2\2\u0093"+
		"\u0094\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0099\7\"\2\2\u0096\u0098\5\4"+
		"\3\2\u0097\u0096\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\u009c\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u009d\7#"+
		"\2\2\u009d\31\3\2\2\2\u009e\u00a2\5\f\7\2\u009f\u00a2\5\22\n\2\u00a0\u00a2"+
		"\5\16\b\2\u00a1\u009e\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a0\3\2\2\2"+
		"\u00a2\33\3\2\2\2\u00a3\u00a7\7\"\2\2\u00a4\u00a6\5\4\3\2\u00a5\u00a4"+
		"\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\7#\2\2\u00ab\35\3\2\2\2"+
		"\u00ac\u00ad\5\32\16\2\u00ad\u00ae\7=\2\2\u00ae\u00af\5$\23\2\u00af\u00b0"+
		"\5\34\17\2\u00b0\37\3\2\2\2\u00b1\u00b2\5\32\16\2\u00b2\u00b3\7=\2\2\u00b3"+
		"\u00b4\5$\23\2\u00b4\u00b5\5\34\17\2\u00b5!\3\2\2\2\u00b6\u00b7\7=\2\2"+
		"\u00b7\u00b8\5&\24\2\u00b8#\3\2\2\2\u00b9\u00c5\7 \2\2\u00ba\u00bb\5\n"+
		"\6\2\u00bb\u00c2\7=\2\2\u00bc\u00bd\7\3\2\2\u00bd\u00be\5\n\6\2\u00be"+
		"\u00bf\7=\2\2\u00bf\u00c1\3\2\2\2\u00c0\u00bc\3\2\2\2\u00c1\u00c4\3\2"+
		"\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4"+
		"\u00c2\3\2\2\2\u00c5\u00ba\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\3\2"+
		"\2\2\u00c7\u00c8\7!\2\2\u00c8%\3\2\2\2\u00c9\u00d2\7 \2\2\u00ca\u00cf"+
		"\5\62\32\2\u00cb\u00cc\7\3\2\2\u00cc\u00ce\5\62\32\2\u00cd\u00cb\3\2\2"+
		"\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d3"+
		"\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00ca\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3"+
		"\u00d4\3\2\2\2\u00d4\u00d5\7!\2\2\u00d5\'\3\2\2\2\u00d6\u00d8\7\36\2\2"+
		"\u00d7\u00d9\7\26\2\2\u00d8\u00d7\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da"+
		"\3\2\2\2\u00da\u00db\7\37\2\2\u00db\u00dc\5&\24\2\u00dc\u00dd\7\4\2\2"+
		"\u00dd\u00e1\7\"\2\2\u00de\u00e0\5\4\3\2\u00df\u00de\3\2\2\2\u00e0\u00e3"+
		"\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e4\3\2\2\2\u00e3"+
		"\u00e1\3\2\2\2\u00e4\u00e5\7#\2\2\u00e5)\3\2\2\2\u00e6\u00e7\5(\25\2\u00e7"+
		"\u00e8\5&\24\2\u00e8+\3\2\2\2\u00e9\u00f3\7\67\2\2\u00ea\u00f3\78\2\2"+
		"\u00eb\u00f3\7=\2\2\u00ec\u00f3\7/\2\2\u00ed\u00f3\5\"\22\2\u00ee\u00f3"+
		"\5\20\t\2\u00ef\u00f3\7-\2\2\u00f0\u00f3\7.\2\2\u00f1\u00f3\7,\2\2\u00f2"+
		"\u00e9\3\2\2\2\u00f2\u00ea\3\2\2\2\u00f2\u00eb\3\2\2\2\u00f2\u00ec\3\2"+
		"\2\2\u00f2\u00ed\3\2\2\2\u00f2\u00ee\3\2\2\2\u00f2\u00ef\3\2\2\2\u00f2"+
		"\u00f0\3\2\2\2\u00f2\u00f1\3\2\2\2\u00f3-\3\2\2\2\u00f4\u00f5\5\62\32"+
		"\2\u00f5\u00f6\7\5\2\2\u00f6/\3\2\2\2\u00f7\u00f8\t\3\2\2\u00f8\61\3\2"+
		"\2\2\u00f9\u00fa\b\32\1\2\u00fa\u00fb\7 \2\2\u00fb\u00fc\5\62\32\2\u00fc"+
		"\u00fd\7!\2\2\u00fd\u0104\3\2\2\2\u00fe\u0104\5,\27\2\u00ff\u0104\5\""+
		"\22\2\u0100\u0101\5\60\31\2\u0101\u0102\5\62\32\16\u0102\u0104\3\2\2\2"+
		"\u0103\u00f9\3\2\2\2\u0103\u00fe\3\2\2\2\u0103\u00ff\3\2\2\2\u0103\u0100"+
		"\3\2\2\2\u0104\u013a\3\2\2\2\u0105\u0106\f\r\2\2\u0106\u0107\t\4\2\2\u0107"+
		"\u0139\5\62\32\16\u0108\u0109\f\f\2\2\u0109\u010a\t\5\2\2\u010a\u0139"+
		"\5\62\32\r\u010b\u010c\f\13\2\2\u010c\u010d\t\6\2\2\u010d\u0139\5\62\32"+
		"\f\u010e\u010f\f\n\2\2\u010f\u0110\t\7\2\2\u0110\u0139\5\62\32\13\u0111"+
		"\u0112\f\t\2\2\u0112\u0113\t\b\2\2\u0113\u0139\5\62\32\n\u0114\u0115\f"+
		"\b\2\2\u0115\u0116\7\26\2\2\u0116\u0139\5\62\32\t\u0117\u0118\f\7\2\2"+
		"\u0118\u0119\7\30\2\2\u0119\u0139\5\62\32\b\u011a\u011b\f\6\2\2\u011b"+
		"\u011c\7\27\2\2\u011c\u0139\5\62\32\7\u011d\u011e\f\5\2\2\u011e\u011f"+
		"\7\21\2\2\u011f\u0139\5\62\32\6\u0120\u0121\f\4\2\2\u0121\u0122\7\22\2"+
		"\2\u0122\u0139\5\62\32\5\u0123\u0124\f\3\2\2\u0124\u0125\7\32\2\2\u0125"+
		"\u0139\5\62\32\4\u0126\u0127\f\23\2\2\u0127\u0128\7<\2\2\u0128\u0129\7"+
		"=\2\2\u0129\u0139\5&\24\2\u012a\u012b\f\22\2\2\u012b\u012c\7<\2\2\u012c"+
		"\u0139\7=\2\2\u012d\u0132\f\21\2\2\u012e\u012f\7\36\2\2\u012f\u0130\5"+
		"\62\32\2\u0130\u0131\7\37\2\2\u0131\u0133\3\2\2\2\u0132\u012e\3\2\2\2"+
		"\u0133\u0134\3\2\2\2\u0134\u0132\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0139"+
		"\3\2\2\2\u0136\u0137\f\17\2\2\u0137\u0139\t\t\2\2\u0138\u0105\3\2\2\2"+
		"\u0138\u0108\3\2\2\2\u0138\u010b\3\2\2\2\u0138\u010e\3\2\2\2\u0138\u0111"+
		"\3\2\2\2\u0138\u0114\3\2\2\2\u0138\u0117\3\2\2\2\u0138\u011a\3\2\2\2\u0138"+
		"\u011d\3\2\2\2\u0138\u0120\3\2\2\2\u0138\u0123\3\2\2\2\u0138\u0126\3\2"+
		"\2\2\u0138\u012a\3\2\2\2\u0138\u012d\3\2\2\2\u0138\u0136\3\2\2\2\u0139"+
		"\u013c\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b\63\3\2\2"+
		"\2\u013c\u013a\3\2\2\2\u013d\u0140\7=\2\2\u013e\u013f\7\32\2\2\u013f\u0141"+
		"\5\62\32\2\u0140\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141\65\3\2\2\2\u0142"+
		"\u0143\5\n\6\2\u0143\u0148\5\64\33\2\u0144\u0145\7\3\2\2\u0145\u0147\5"+
		"\64\33\2\u0146\u0144\3\2\2\2\u0147\u014a\3\2\2\2\u0148\u0146\3\2\2\2\u0148"+
		"\u0149\3\2\2\2\u0149\u014b\3\2\2\2\u014a\u0148\3\2\2\2\u014b\u014c\7\5"+
		"\2\2\u014c\67\3\2\2\2\u014d\u0151\7\"\2\2\u014e\u0150\5\4\3\2\u014f\u014e"+
		"\3\2\2\2\u0150\u0153\3\2\2\2\u0151\u014f\3\2\2\2\u0151\u0152\3\2\2\2\u0152"+
		"\u0154\3\2\2\2\u0153\u0151\3\2\2\2\u0154\u0157\7#\2\2\u0155\u0157\5\4"+
		"\3\2\u0156\u014d\3\2\2\2\u0156\u0155\3\2\2\2\u01579\3\2\2\2\u0158\u015a"+
		"\7\66\2\2\u0159\u015b\5\62\32\2\u015a\u0159\3\2\2\2\u015a\u015b\3\2\2"+
		"\2\u015b\u015f\3\2\2\2\u015c\u015f\7\64\2\2\u015d\u015f\7\65\2\2\u015e"+
		"\u0158\3\2\2\2\u015e\u015c\3\2\2\2\u015e\u015d\3\2\2\2\u015f\u0160\3\2"+
		"\2\2\u0160\u0161\7\5\2\2\u0161;\3\2\2\2\u0162\u0163\7\60\2\2\u0163\u0164"+
		"\7 \2\2\u0164\u0165\5\62\32\2\u0165\u0166\7!\2\2\u0166\u0169\58\35\2\u0167"+
		"\u0168\7\61\2\2\u0168\u016a\58\35\2\u0169\u0167\3\2\2\2\u0169\u016a\3"+
		"\2\2\2\u016a=\3\2\2\2\u016b\u016c\7\63\2\2\u016c\u016d\7 \2\2\u016d\u016e"+
		"\5\62\32\2\u016e\u016f\7!\2\2\u016f\u0170\58\35\2\u0170?\3\2\2\2\u0171"+
		"\u0176\5\66\34\2\u0172\u0173\5\62\32\2\u0173\u0174\7\5\2\2\u0174\u0176"+
		"\3\2\2\2\u0175\u0171\3\2\2\2\u0175\u0172\3\2\2\2\u0176\u0179\3\2\2\2\u0177"+
		"\u0179\7\5\2\2\u0178\u0175\3\2\2\2\u0178\u0177\3\2\2\2\u0179A\3\2\2\2"+
		"\u017a\u017c\5\62\32\2\u017b\u017a\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017d"+
		"\3\2\2\2\u017d\u017e\7\5\2\2\u017eC\3\2\2\2\u017f\u0181\5\62\32\2\u0180"+
		"\u017f\3\2\2\2\u0180\u0181\3\2\2\2\u0181E\3\2\2\2\u0182\u0183\7\62\2\2"+
		"\u0183\u0184\7 \2\2\u0184\u0185\5@!\2\u0185\u0186\5B\"\2\u0186\u0187\5"+
		"D#\2\u0187\u0188\7!\2\2\u0188\u0189\58\35\2\u0189G\3\2\2\2\'KVZ_chsx~"+
		"\u0086\u0088\u0093\u0099\u00a1\u00a7\u00c2\u00c5\u00cf\u00d2\u00d8\u00e1"+
		"\u00f2\u0103\u0134\u0138\u013a\u0140\u0148\u0151\u0156\u015a\u015e\u0169"+
		"\u0175\u0178\u017b\u0180";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}