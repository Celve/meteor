package parser;// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class CommonParserRulesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

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
	public String getGrammarFileName() { return "java-escape"; }

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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
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
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 612348748860882992L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitDef(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterVarType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitVarType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitVarType(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
	public static class PrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode Bool() { return getToken(CommonParserRulesParser.Bool, 0); }
		public TerminalNode Int() { return getToken(CommonParserRulesParser.Int, 0); }
		public TerminalNode String() { return getToken(CommonParserRulesParser.String, 0); }
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterPrimitiveType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitPrimitiveType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
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
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class VoidTypeContext extends ParserRuleContext {
		public TerminalNode Void() { return getToken(CommonParserRulesParser.Void, 0); }
		public VoidTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voidType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterVoidType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitVoidType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitVoidType(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterInitExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitInitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitInitExpr(this);
			else return visitor.visitChildren(this);
		}
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
					if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421459865648L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class NonPrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public NonPrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonPrimitiveType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterNonPrimitiveType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitNonPrimitiveType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitNonPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterClassSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitClassSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitClassSuite(this);
			else return visitor.visitChildren(this);
		}
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
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 576461783095574528L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitClassDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterClassCtor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitClassCtor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitClassCtor(this);
			else return visitor.visitChildren(this);
		}
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
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 612348748860882992L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterReturnType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitReturnType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitReturnType(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterFuncSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitFuncSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitFuncSuite(this);
			else return visitor.visitChildren(this);
		}
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
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 612348748860882992L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterFuncDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitFuncDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitFuncDecl(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterParamDefList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitParamDefList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitParamDefList(this);
			else return visitor.visitChildren(this);
		}
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
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 576461714376097792L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterParamInputList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitParamInputList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitParamInputList(this);
			else return visitor.visitChildren(this);
		}
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
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421459865648L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterLambdaDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitLambdaDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitLambdaDef(this);
			else return visitor.visitChildren(this);
		}
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
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 612348748860882992L) != 0) {
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterLambdaCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitLambdaCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitLambdaCall(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterBasicExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitBasicExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitBasicExpr(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExprStmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterPrefixOps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitPrefixOps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitPrefixOps(this);
			else return visitor.visitChildren(this);
		}
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
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 109183024L) != 0) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrefixExprContext extends ExprContext {
		public PrefixOpsContext prefixOps() {
			return getRuleContext(PrefixOpsContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrefixExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterPrefixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitPrefixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitPrefixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MemberAccessContext extends ExprContext {
		public Token classMember;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Access() { return getToken(CommonParserRulesParser.Access, 0); }
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public MemberAccessContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterMemberAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitMemberAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitMemberAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MethodAccessContext extends ExprContext {
		public Token methodName;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Access() { return getToken(CommonParserRulesParser.Access, 0); }
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public TerminalNode Id() { return getToken(CommonParserRulesParser.Id, 0); }
		public MethodAccessContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterMethodAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitMethodAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitMethodAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SuffixExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Increment() { return getToken(CommonParserRulesParser.Increment, 0); }
		public TerminalNode Decrement() { return getToken(CommonParserRulesParser.Decrement, 0); }
		public SuffixExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterSuffixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitSuffixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitSuffixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PriorExprContext extends ExprContext {
		public TerminalNode LeftParen() { return getToken(CommonParserRulesParser.LeftParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(CommonParserRulesParser.RightParen, 0); }
		public PriorExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterPriorExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitPriorExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitPriorExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
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
		public TerminalNode BitwiseAnd() { return getToken(CommonParserRulesParser.BitwiseAnd, 0); }
		public TerminalNode BitwiseXor() { return getToken(CommonParserRulesParser.BitwiseXor, 0); }
		public TerminalNode BitwiseOr() { return getToken(CommonParserRulesParser.BitwiseOr, 0); }
		public TerminalNode LogicalAnd() { return getToken(CommonParserRulesParser.LogicalAnd, 0); }
		public TerminalNode LogicalOr() { return getToken(CommonParserRulesParser.LogicalOr, 0); }
		public TerminalNode Equal() { return getToken(CommonParserRulesParser.Equal, 0); }
		public TerminalNode NotEqual() { return getToken(CommonParserRulesParser.NotEqual, 0); }
		public BinaryExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitBinaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayAccessContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> LeftBracket() { return getTokens(CommonParserRulesParser.LeftBracket); }
		public TerminalNode LeftBracket(int i) {
			return getToken(CommonParserRulesParser.LeftBracket, i);
		}
		public List<TerminalNode> RightBracket() { return getTokens(CommonParserRulesParser.RightBracket); }
		public TerminalNode RightBracket(int i) {
			return getToken(CommonParserRulesParser.RightBracket, i);
		}
		public ArrayAccessContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterArrayAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitArrayAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitArrayAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomContext extends ExprContext {
		public BasicExprContext basicExpr() {
			return getRuleContext(BasicExprContext.class,0);
		}
		public AtomContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode Assign() { return getToken(CommonParserRulesParser.Assign, 0); }
		public AssignExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterAssignExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitAssignExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitAssignExpr(this);
			else return visitor.visitChildren(this);
		}
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
			setState(256);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LeftParen:
				{
				_localctx = new PriorExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(248);
				match(LeftParen);
				setState(249);
				expr(0);
				setState(250);
				match(RightParen);
				}
				break;
			case New:
			case Null:
			case True:
			case False:
			case This:
			case IntegerLiteral:
			case StringLiteral:
			case Id:
				{
				_localctx = new AtomContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(252);
				basicExpr();
				}
				break;
			case Add:
			case Sub:
			case LogicalNot:
			case BitwiseNot:
			case Increment:
			case Decrement:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(253);
				prefixOps();
				setState(254);
				expr(12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(310);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(308);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(258);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(259);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 448L) != 0) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(260);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(261);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(262);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Add || _la==Sub) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(263);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(264);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(265);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==RightShift || _la==LeftShift) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(266);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(267);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(268);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 7680L) != 0) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(269);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(270);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(271);
						match(BitwiseAnd);
						setState(272);
						expr(7);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(273);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(274);
						match(BitwiseXor);
						setState(275);
						expr(6);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(276);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(277);
						match(BitwiseOr);
						setState(278);
						expr(5);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(279);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(280);
						match(LogicalAnd);
						setState(281);
						expr(4);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(282);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(283);
						match(LogicalOr);
						setState(284);
						expr(3);
						}
						break;
					case 10:
						{
						_localctx = new AssignExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(285);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(286);
						match(Assign);
						setState(287);
						expr(2);
						}
						break;
					case 11:
						{
						_localctx = new MethodAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(288);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(289);
						match(Access);
						{
						setState(290);
						((MethodAccessContext)_localctx).methodName = match(Id);
						}
						setState(291);
						paramInputList();
						}
						break;
					case 12:
						{
						_localctx = new MemberAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(292);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(293);
						match(Access);
						{
						setState(294);
						((MemberAccessContext)_localctx).classMember = match(Id);
						}
						}
						break;
					case 13:
						{
						_localctx = new ArrayAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(295);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(300); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(296);
								match(LeftBracket);
								setState(297);
								expr(0);
								setState(298);
								match(RightBracket);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(302); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 14:
						{
						_localctx = new SuffixExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(304);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(305);
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
					case 15:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(306);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(307);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Equal || _la==NotEqual) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
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
				setState(312);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterAssignUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitAssignUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitAssignUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignUnitContext assignUnit() throws RecognitionException {
		AssignUnitContext _localctx = new AssignUnitContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_assignUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(Id);
			setState(316);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(314);
				match(Assign);
				setState(315);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitVarDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			varType();
			setState(319);
			assignUnit();
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(320);
				match(T__0);
				setState(321);
				assignUnit();
				}
				}
				setState(326);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(327);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterBlockSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitBlockSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitBlockSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockSuiteContext blockSuite() throws RecognitionException {
		BlockSuiteContext _localctx = new BlockSuiteContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_blockSuite);
		int _la;
		try {
			setState(338);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				match(LeftBrace);
				setState(333);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((_la) & ~0x3f) == 0 && ((1L << _la) & 612348748860882992L) != 0) {
					{
					{
					setState(330);
					stmt();
					}
					}
					setState(335);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(336);
				match(RightBrace);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(337);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterJumpStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitJumpStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitJumpStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStmtContext jumpStmt() throws RecognitionException {
		JumpStmtContext _localctx = new JumpStmtContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_jumpStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Return:
				{
				setState(340);
				match(Return);
				setState(342);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421459865648L) != 0) {
					{
					setState(341);
					expr(0);
					}
				}

				}
				break;
			case Break:
				{
				setState(344);
				match(Break);
				}
				break;
			case Continue:
				{
				setState(345);
				match(Continue);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(348);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterCondStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitCondStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitCondStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondStmtContext condStmt() throws RecognitionException {
		CondStmtContext _localctx = new CondStmtContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_condStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(350);
			match(If);
			setState(351);
			match(LeftParen);
			setState(352);
			expr(0);
			setState(353);
			match(RightParen);
			setState(354);
			blockSuite();
			setState(357);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(355);
				match(Else);
				setState(356);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
			match(While);
			setState(360);
			match(LeftParen);
			setState(361);
			expr(0);
			setState(362);
			match(RightParen);
			setState(363);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterForInitUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitForInitUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitForInitUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitUnitContext forInitUnit() throws RecognitionException {
		ForInitUnitContext _localctx = new ForInitUnitContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_forInitUnit);
		try {
			setState(372);
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
				setState(369);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(365);
					varDecl();
					}
					break;
				case 2:
					{
					setState(366);
					expr(0);
					setState(367);
					match(T__2);
					}
					break;
				}
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(371);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ForCondUnitContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForCondUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forCondUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterForCondUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitForCondUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitForCondUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForCondUnitContext forCondUnit() throws RecognitionException {
		ForCondUnitContext _localctx = new ForCondUnitContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_forCondUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421459865648L) != 0) {
				{
				setState(374);
				expr(0);
				}
			}

			setState(377);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ForStepUnitContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForStepUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStepUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterForStepUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitForStepUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitForStepUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStepUnitContext forStepUnit() throws RecognitionException {
		ForStepUnitContext _localctx = new ForStepUnitContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_forStepUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421459865648L) != 0) {
				{
				setState(379);
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

	@SuppressWarnings("CheckReturnValue")
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).enterForStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonParserRulesListener ) ((CommonParserRulesListener)listener).exitForStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommonParserRulesVisitor ) return ((CommonParserRulesVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStmtContext forStmt() throws RecognitionException {
		ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_forStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			match(For);
			setState(383);
			match(LeftParen);
			setState(384);
			forInitUnit();
			setState(385);
			forCondUnit();
			setState(386);
			forStepUnit();
			setState(387);
			match(RightParen);
			setState(388);
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
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 1);
		case 10:
			return precpred(_ctx, 16);
		case 11:
			return precpred(_ctx, 15);
		case 12:
			return precpred(_ctx, 14);
		case 13:
			return precpred(_ctx, 13);
		case 14:
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001;\u0187\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0001"+
		"\u0000\u0005\u0000H\b\u0000\n\u0000\f\u0000K\t\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0003\u0001U\b\u0001\u0001\u0002\u0001\u0002\u0003\u0002Y\b\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003^\b\u0003\u0001\u0004"+
		"\u0001\u0004\u0003\u0004b\b\u0004\u0001\u0004\u0005\u0004e\b\u0004\n\u0004"+
		"\f\u0004h\t\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007r\b\u0007\u0001"+
		"\u0007\u0005\u0007u\b\u0007\n\u0007\f\u0007x\t\u0007\u0001\u0007\u0005"+
		"\u0007{\b\u0007\n\u0007\f\u0007~\t\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0005\t\u0085\b\t\n\t\f\t\u0088\t\t\u0001\t\u0001\t\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0003\u000b\u0092\b\u000b"+
		"\u0001\u000b\u0001\u000b\u0005\u000b\u0096\b\u000b\n\u000b\f\u000b\u0099"+
		"\t\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0003\f\u00a0"+
		"\b\f\u0001\r\u0001\r\u0005\r\u00a4\b\r\n\r\f\r\u00a7\t\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0005\u0011\u00bf\b\u0011\n\u0011\f\u0011\u00c2"+
		"\t\u0011\u0003\u0011\u00c4\b\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u00cc\b\u0012\n\u0012"+
		"\f\u0012\u00cf\t\u0012\u0003\u0012\u00d1\b\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0003\u0013\u00d7\b\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00de\b\u0013\n\u0013"+
		"\f\u0013\u00e1\t\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u00f1\b\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0003\u0018\u0101\b\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0004\u0018\u012d\b\u0018"+
		"\u000b\u0018\f\u0018\u012e\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0005\u0018\u0135\b\u0018\n\u0018\f\u0018\u0138\t\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0003\u0019\u013d\b\u0019\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0005\u001a\u0143\b\u001a\n\u001a\f\u001a\u0146\t\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0005\u001b\u014c\b\u001b"+
		"\n\u001b\f\u001b\u014f\t\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u0153"+
		"\b\u001b\u0001\u001c\u0001\u001c\u0003\u001c\u0157\b\u001c\u0001\u001c"+
		"\u0001\u001c\u0003\u001c\u015b\b\u001c\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0003\u001d\u0166\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0003\u001f\u0172\b\u001f\u0001\u001f\u0003\u001f\u0175\b\u001f\u0001"+
		" \u0003 \u0178\b \u0001 \u0001 \u0001!\u0003!\u017d\b!\u0001\"\u0001\""+
		"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0000\u00010"+
		"#\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a"+
		"\u001c\u001e \"$&(*,.02468:<>@BD\u0000\b\u0001\u0000%\'\u0004\u0000\u0004"+
		"\u0005\u0011\u0011\u0017\u0017\u0019\u001a\u0001\u0000\u0006\b\u0001\u0000"+
		"\u0004\u0005\u0001\u0000\u0012\u0013\u0001\u0000\t\f\u0001\u0000\u0019"+
		"\u001a\u0001\u0000\r\u000e\u01a6\u0000I\u0001\u0000\u0000\u0000\u0002"+
		"T\u0001\u0000\u0000\u0000\u0004X\u0001\u0000\u0000\u0000\u0006]\u0001"+
		"\u0000\u0000\u0000\ba\u0001\u0000\u0000\u0000\ni\u0001\u0000\u0000\u0000"+
		"\fk\u0001\u0000\u0000\u0000\u000em\u0001\u0000\u0000\u0000\u0010\u007f"+
		"\u0001\u0000\u0000\u0000\u0012\u0081\u0001\u0000\u0000\u0000\u0014\u008b"+
		"\u0001\u0000\u0000\u0000\u0016\u008f\u0001\u0000\u0000\u0000\u0018\u009f"+
		"\u0001\u0000\u0000\u0000\u001a\u00a1\u0001\u0000\u0000\u0000\u001c\u00aa"+
		"\u0001\u0000\u0000\u0000\u001e\u00af\u0001\u0000\u0000\u0000 \u00b4\u0001"+
		"\u0000\u0000\u0000\"\u00b7\u0001\u0000\u0000\u0000$\u00c7\u0001\u0000"+
		"\u0000\u0000&\u00d4\u0001\u0000\u0000\u0000(\u00e4\u0001\u0000\u0000\u0000"+
		"*\u00f0\u0001\u0000\u0000\u0000,\u00f2\u0001\u0000\u0000\u0000.\u00f5"+
		"\u0001\u0000\u0000\u00000\u0100\u0001\u0000\u0000\u00002\u0139\u0001\u0000"+
		"\u0000\u00004\u013e\u0001\u0000\u0000\u00006\u0152\u0001\u0000\u0000\u0000"+
		"8\u015a\u0001\u0000\u0000\u0000:\u015e\u0001\u0000\u0000\u0000<\u0167"+
		"\u0001\u0000\u0000\u0000>\u0174\u0001\u0000\u0000\u0000@\u0177\u0001\u0000"+
		"\u0000\u0000B\u017c\u0001\u0000\u0000\u0000D\u017e\u0001\u0000\u0000\u0000"+
		"FH\u0003\u0002\u0001\u0000GF\u0001\u0000\u0000\u0000HK\u0001\u0000\u0000"+
		"\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000J\u0001\u0001"+
		"\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000LU\u00038\u001c\u0000MU\u0003"+
		",\u0016\u0000NU\u0003:\u001d\u0000OU\u0003D\"\u0000PU\u0003<\u001e\u0000"+
		"QU\u0003\u0006\u0003\u0000RU\u0003\u0004\u0002\u0000SU\u0003\u001a\r\u0000"+
		"TL\u0001\u0000\u0000\u0000TM\u0001\u0000\u0000\u0000TN\u0001\u0000\u0000"+
		"\u0000TO\u0001\u0000\u0000\u0000TP\u0001\u0000\u0000\u0000TQ\u0001\u0000"+
		"\u0000\u0000TR\u0001\u0000\u0000\u0000TS\u0001\u0000\u0000\u0000U\u0003"+
		"\u0001\u0000\u0000\u0000VY\u00034\u001a\u0000WY\u0003\u001e\u000f\u0000"+
		"XV\u0001\u0000\u0000\u0000XW\u0001\u0000\u0000\u0000Y\u0005\u0001\u0000"+
		"\u0000\u0000Z^\u0003\u0014\n\u0000[^\u0003\u001c\u000e\u0000\\^\u0003"+
		"&\u0013\u0000]Z\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000]\\\u0001"+
		"\u0000\u0000\u0000^\u0007\u0001\u0000\u0000\u0000_b\u0003\n\u0005\u0000"+
		"`b\u0003\u0010\b\u0000a_\u0001\u0000\u0000\u0000a`\u0001\u0000\u0000\u0000"+
		"bf\u0001\u0000\u0000\u0000ce\u0005\u001b\u0000\u0000dc\u0001\u0000\u0000"+
		"\u0000eh\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000fg\u0001\u0000"+
		"\u0000\u0000g\t\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000ij\u0007"+
		"\u0000\u0000\u0000j\u000b\u0001\u0000\u0000\u0000kl\u0005$\u0000\u0000"+
		"l\r\u0001\u0000\u0000\u0000mn\u0005(\u0000\u0000nv\u0003\b\u0004\u0000"+
		"oq\u0005\u001c\u0000\u0000pr\u00030\u0018\u0000qp\u0001\u0000\u0000\u0000"+
		"qr\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000su\u0005\u001d\u0000"+
		"\u0000to\u0001\u0000\u0000\u0000ux\u0001\u0000\u0000\u0000vt\u0001\u0000"+
		"\u0000\u0000vw\u0001\u0000\u0000\u0000w|\u0001\u0000\u0000\u0000xv\u0001"+
		"\u0000\u0000\u0000y{\u0005\u001b\u0000\u0000zy\u0001\u0000\u0000\u0000"+
		"{~\u0001\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000"+
		"\u0000}\u000f\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000\u007f"+
		"\u0080\u0005;\u0000\u0000\u0080\u0011\u0001\u0000\u0000\u0000\u0081\u0086"+
		"\u0005 \u0000\u0000\u0082\u0085\u0003\u0004\u0002\u0000\u0083\u0085\u0003"+
		"\u0016\u000b\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0084\u0083\u0001"+
		"\u0000\u0000\u0000\u0085\u0088\u0001\u0000\u0000\u0000\u0086\u0084\u0001"+
		"\u0000\u0000\u0000\u0086\u0087\u0001\u0000\u0000\u0000\u0087\u0089\u0001"+
		"\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0089\u008a\u0005"+
		"!\u0000\u0000\u008a\u0013\u0001\u0000\u0000\u0000\u008b\u008c\u0005)\u0000"+
		"\u0000\u008c\u008d\u0005;\u0000\u0000\u008d\u008e\u0003\u0012\t\u0000"+
		"\u008e\u0015\u0001\u0000\u0000\u0000\u008f\u0091\u0005;\u0000\u0000\u0090"+
		"\u0092\u0003\"\u0011\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0091\u0092"+
		"\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0097"+
		"\u0005 \u0000\u0000\u0094\u0096\u0003\u0002\u0001\u0000\u0095\u0094\u0001"+
		"\u0000\u0000\u0000\u0096\u0099\u0001\u0000\u0000\u0000\u0097\u0095\u0001"+
		"\u0000\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098\u009a\u0001"+
		"\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u009a\u009b\u0005"+
		"!\u0000\u0000\u009b\u0017\u0001\u0000\u0000\u0000\u009c\u00a0\u0003\n"+
		"\u0005\u0000\u009d\u00a0\u0003\u0010\b\u0000\u009e\u00a0\u0003\f\u0006"+
		"\u0000\u009f\u009c\u0001\u0000\u0000\u0000\u009f\u009d\u0001\u0000\u0000"+
		"\u0000\u009f\u009e\u0001\u0000\u0000\u0000\u00a0\u0019\u0001\u0000\u0000"+
		"\u0000\u00a1\u00a5\u0005 \u0000\u0000\u00a2\u00a4\u0003\u0002\u0001\u0000"+
		"\u00a3\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a7\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a3\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a8\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a9\u0005!\u0000\u0000\u00a9\u001b\u0001\u0000\u0000\u0000\u00aa"+
		"\u00ab\u0003\u0018\f\u0000\u00ab\u00ac\u0005;\u0000\u0000\u00ac\u00ad"+
		"\u0003\"\u0011\u0000\u00ad\u00ae\u0003\u001a\r\u0000\u00ae\u001d\u0001"+
		"\u0000\u0000\u0000\u00af\u00b0\u0003\u0018\f\u0000\u00b0\u00b1\u0005;"+
		"\u0000\u0000\u00b1\u00b2\u0003\"\u0011\u0000\u00b2\u00b3\u0003\u001a\r"+
		"\u0000\u00b3\u001f\u0001\u0000\u0000\u0000\u00b4\u00b5\u0005;\u0000\u0000"+
		"\u00b5\u00b6\u0003$\u0012\u0000\u00b6!\u0001\u0000\u0000\u0000\u00b7\u00c3"+
		"\u0005\u001e\u0000\u0000\u00b8\u00b9\u0003\b\u0004\u0000\u00b9\u00c0\u0005"+
		";\u0000\u0000\u00ba\u00bb\u0005\u0001\u0000\u0000\u00bb\u00bc\u0003\b"+
		"\u0004\u0000\u00bc\u00bd\u0005;\u0000\u0000\u00bd\u00bf\u0001\u0000\u0000"+
		"\u0000\u00be\u00ba\u0001\u0000\u0000\u0000\u00bf\u00c2\u0001\u0000\u0000"+
		"\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000\u0000"+
		"\u0000\u00c1\u00c4\u0001\u0000\u0000\u0000\u00c2\u00c0\u0001\u0000\u0000"+
		"\u0000\u00c3\u00b8\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001\u0000\u0000"+
		"\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\u001f\u0000"+
		"\u0000\u00c6#\u0001\u0000\u0000\u0000\u00c7\u00d0\u0005\u001e\u0000\u0000"+
		"\u00c8\u00cd\u00030\u0018\u0000\u00c9\u00ca\u0005\u0001\u0000\u0000\u00ca"+
		"\u00cc\u00030\u0018\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000\u00cc\u00cf"+
		"\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000\u0000\u00cd\u00ce"+
		"\u0001\u0000\u0000\u0000\u00ce\u00d1\u0001\u0000\u0000\u0000\u00cf\u00cd"+
		"\u0001\u0000\u0000\u0000\u00d0\u00c8\u0001\u0000\u0000\u0000\u00d0\u00d1"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0001\u0000\u0000\u0000\u00d2\u00d3"+
		"\u0005\u001f\u0000\u0000\u00d3%\u0001\u0000\u0000\u0000\u00d4\u00d6\u0005"+
		"\u001c\u0000\u0000\u00d5\u00d7\u0005\u0014\u0000\u0000\u00d6\u00d5\u0001"+
		"\u0000\u0000\u0000\u00d6\u00d7\u0001\u0000\u0000\u0000\u00d7\u00d8\u0001"+
		"\u0000\u0000\u0000\u00d8\u00d9\u0005\u001d\u0000\u0000\u00d9\u00da\u0003"+
		"$\u0012\u0000\u00da\u00db\u0005\u0002\u0000\u0000\u00db\u00df\u0005 \u0000"+
		"\u0000\u00dc\u00de\u0003\u0002\u0001\u0000\u00dd\u00dc\u0001\u0000\u0000"+
		"\u0000\u00de\u00e1\u0001\u0000\u0000\u0000\u00df\u00dd\u0001\u0000\u0000"+
		"\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0\u00e2\u0001\u0000\u0000"+
		"\u0000\u00e1\u00df\u0001\u0000\u0000\u0000\u00e2\u00e3\u0005!\u0000\u0000"+
		"\u00e3\'\u0001\u0000\u0000\u0000\u00e4\u00e5\u0003&\u0013\u0000\u00e5"+
		"\u00e6\u0003$\u0012\u0000\u00e6)\u0001\u0000\u0000\u0000\u00e7\u00f1\u0005"+
		"5\u0000\u0000\u00e8\u00f1\u00056\u0000\u0000\u00e9\u00f1\u0005;\u0000"+
		"\u0000\u00ea\u00f1\u0005-\u0000\u0000\u00eb\u00f1\u0003 \u0010\u0000\u00ec"+
		"\u00f1\u0003\u000e\u0007\u0000\u00ed\u00f1\u0005+\u0000\u0000\u00ee\u00f1"+
		"\u0005,\u0000\u0000\u00ef\u00f1\u0005*\u0000\u0000\u00f0\u00e7\u0001\u0000"+
		"\u0000\u0000\u00f0\u00e8\u0001\u0000\u0000\u0000\u00f0\u00e9\u0001\u0000"+
		"\u0000\u0000\u00f0\u00ea\u0001\u0000\u0000\u0000\u00f0\u00eb\u0001\u0000"+
		"\u0000\u0000\u00f0\u00ec\u0001\u0000\u0000\u0000\u00f0\u00ed\u0001\u0000"+
		"\u0000\u0000\u00f0\u00ee\u0001\u0000\u0000\u0000\u00f0\u00ef\u0001\u0000"+
		"\u0000\u0000\u00f1+\u0001\u0000\u0000\u0000\u00f2\u00f3\u00030\u0018\u0000"+
		"\u00f3\u00f4\u0005\u0003\u0000\u0000\u00f4-\u0001\u0000\u0000\u0000\u00f5"+
		"\u00f6\u0007\u0001\u0000\u0000\u00f6/\u0001\u0000\u0000\u0000\u00f7\u00f8"+
		"\u0006\u0018\uffff\uffff\u0000\u00f8\u00f9\u0005\u001e\u0000\u0000\u00f9"+
		"\u00fa\u00030\u0018\u0000\u00fa\u00fb\u0005\u001f\u0000\u0000\u00fb\u0101"+
		"\u0001\u0000\u0000\u0000\u00fc\u0101\u0003*\u0015\u0000\u00fd\u00fe\u0003"+
		".\u0017\u0000\u00fe\u00ff\u00030\u0018\f\u00ff\u0101\u0001\u0000\u0000"+
		"\u0000\u0100\u00f7\u0001\u0000\u0000\u0000\u0100\u00fc\u0001\u0000\u0000"+
		"\u0000\u0100\u00fd\u0001\u0000\u0000\u0000\u0101\u0136\u0001\u0000\u0000"+
		"\u0000\u0102\u0103\n\u000b\u0000\u0000\u0103\u0104\u0007\u0002\u0000\u0000"+
		"\u0104\u0135\u00030\u0018\f\u0105\u0106\n\n\u0000\u0000\u0106\u0107\u0007"+
		"\u0003\u0000\u0000\u0107\u0135\u00030\u0018\u000b\u0108\u0109\n\t\u0000"+
		"\u0000\u0109\u010a\u0007\u0004\u0000\u0000\u010a\u0135\u00030\u0018\n"+
		"\u010b\u010c\n\b\u0000\u0000\u010c\u010d\u0007\u0005\u0000\u0000\u010d"+
		"\u0135\u00030\u0018\t\u010e\u010f\n\u0006\u0000\u0000\u010f\u0110\u0005"+
		"\u0014\u0000\u0000\u0110\u0135\u00030\u0018\u0007\u0111\u0112\n\u0005"+
		"\u0000\u0000\u0112\u0113\u0005\u0016\u0000\u0000\u0113\u0135\u00030\u0018"+
		"\u0006\u0114\u0115\n\u0004\u0000\u0000\u0115\u0116\u0005\u0015\u0000\u0000"+
		"\u0116\u0135\u00030\u0018\u0005\u0117\u0118\n\u0003\u0000\u0000\u0118"+
		"\u0119\u0005\u000f\u0000\u0000\u0119\u0135\u00030\u0018\u0004\u011a\u011b"+
		"\n\u0002\u0000\u0000\u011b\u011c\u0005\u0010\u0000\u0000\u011c\u0135\u0003"+
		"0\u0018\u0003\u011d\u011e\n\u0001\u0000\u0000\u011e\u011f\u0005\u0018"+
		"\u0000\u0000\u011f\u0135\u00030\u0018\u0002\u0120\u0121\n\u0010\u0000"+
		"\u0000\u0121\u0122\u0005:\u0000\u0000\u0122\u0123\u0005;\u0000\u0000\u0123"+
		"\u0135\u0003$\u0012\u0000\u0124\u0125\n\u000f\u0000\u0000\u0125\u0126"+
		"\u0005:\u0000\u0000\u0126\u0135\u0005;\u0000\u0000\u0127\u012c\n\u000e"+
		"\u0000\u0000\u0128\u0129\u0005\u001c\u0000\u0000\u0129\u012a\u00030\u0018"+
		"\u0000\u012a\u012b\u0005\u001d\u0000\u0000\u012b\u012d\u0001\u0000\u0000"+
		"\u0000\u012c\u0128\u0001\u0000\u0000\u0000\u012d\u012e\u0001\u0000\u0000"+
		"\u0000\u012e\u012c\u0001\u0000\u0000\u0000\u012e\u012f\u0001\u0000\u0000"+
		"\u0000\u012f\u0135\u0001\u0000\u0000\u0000\u0130\u0131\n\r\u0000\u0000"+
		"\u0131\u0135\u0007\u0006\u0000\u0000\u0132\u0133\n\u0007\u0000\u0000\u0133"+
		"\u0135\u0007\u0007\u0000\u0000\u0134\u0102\u0001\u0000\u0000\u0000\u0134"+
		"\u0105\u0001\u0000\u0000\u0000\u0134\u0108\u0001\u0000\u0000\u0000\u0134"+
		"\u010b\u0001\u0000\u0000\u0000\u0134\u010e\u0001\u0000\u0000\u0000\u0134"+
		"\u0111\u0001\u0000\u0000\u0000\u0134\u0114\u0001\u0000\u0000\u0000\u0134"+
		"\u0117\u0001\u0000\u0000\u0000\u0134\u011a\u0001\u0000\u0000\u0000\u0134"+
		"\u011d\u0001\u0000\u0000\u0000\u0134\u0120\u0001\u0000\u0000\u0000\u0134"+
		"\u0124\u0001\u0000\u0000\u0000\u0134\u0127\u0001\u0000\u0000\u0000\u0134"+
		"\u0130\u0001\u0000\u0000\u0000\u0134\u0132\u0001\u0000\u0000\u0000\u0135"+
		"\u0138\u0001\u0000\u0000\u0000\u0136\u0134\u0001\u0000\u0000\u0000\u0136"+
		"\u0137\u0001\u0000\u0000\u0000\u01371\u0001\u0000\u0000\u0000\u0138\u0136"+
		"\u0001\u0000\u0000\u0000\u0139\u013c\u0005;\u0000\u0000\u013a\u013b\u0005"+
		"\u0018\u0000\u0000\u013b\u013d\u00030\u0018\u0000\u013c\u013a\u0001\u0000"+
		"\u0000\u0000\u013c\u013d\u0001\u0000\u0000\u0000\u013d3\u0001\u0000\u0000"+
		"\u0000\u013e\u013f\u0003\b\u0004\u0000\u013f\u0144\u00032\u0019\u0000"+
		"\u0140\u0141\u0005\u0001\u0000\u0000\u0141\u0143\u00032\u0019\u0000\u0142"+
		"\u0140\u0001\u0000\u0000\u0000\u0143\u0146\u0001\u0000\u0000\u0000\u0144"+
		"\u0142\u0001\u0000\u0000\u0000\u0144\u0145\u0001\u0000\u0000\u0000\u0145"+
		"\u0147\u0001\u0000\u0000\u0000\u0146\u0144\u0001\u0000\u0000\u0000\u0147"+
		"\u0148\u0005\u0003\u0000\u0000\u01485\u0001\u0000\u0000\u0000\u0149\u014d"+
		"\u0005 \u0000\u0000\u014a\u014c\u0003\u0002\u0001\u0000\u014b\u014a\u0001"+
		"\u0000\u0000\u0000\u014c\u014f\u0001\u0000\u0000\u0000\u014d\u014b\u0001"+
		"\u0000\u0000\u0000\u014d\u014e\u0001\u0000\u0000\u0000\u014e\u0150\u0001"+
		"\u0000\u0000\u0000\u014f\u014d\u0001\u0000\u0000\u0000\u0150\u0153\u0005"+
		"!\u0000\u0000\u0151\u0153\u0003\u0002\u0001\u0000\u0152\u0149\u0001\u0000"+
		"\u0000\u0000\u0152\u0151\u0001\u0000\u0000\u0000\u01537\u0001\u0000\u0000"+
		"\u0000\u0154\u0156\u00054\u0000\u0000\u0155\u0157\u00030\u0018\u0000\u0156"+
		"\u0155\u0001\u0000\u0000\u0000\u0156\u0157\u0001\u0000\u0000\u0000\u0157"+
		"\u015b\u0001\u0000\u0000\u0000\u0158\u015b\u00052\u0000\u0000\u0159\u015b"+
		"\u00053\u0000\u0000\u015a\u0154\u0001\u0000\u0000\u0000\u015a\u0158\u0001"+
		"\u0000\u0000\u0000\u015a\u0159\u0001\u0000\u0000\u0000\u015b\u015c\u0001"+
		"\u0000\u0000\u0000\u015c\u015d\u0005\u0003\u0000\u0000\u015d9\u0001\u0000"+
		"\u0000\u0000\u015e\u015f\u0005.\u0000\u0000\u015f\u0160\u0005\u001e\u0000"+
		"\u0000\u0160\u0161\u00030\u0018\u0000\u0161\u0162\u0005\u001f\u0000\u0000"+
		"\u0162\u0165\u00036\u001b\u0000\u0163\u0164\u0005/\u0000\u0000\u0164\u0166"+
		"\u00036\u001b\u0000\u0165\u0163\u0001\u0000\u0000\u0000\u0165\u0166\u0001"+
		"\u0000\u0000\u0000\u0166;\u0001\u0000\u0000\u0000\u0167\u0168\u00051\u0000"+
		"\u0000\u0168\u0169\u0005\u001e\u0000\u0000\u0169\u016a\u00030\u0018\u0000"+
		"\u016a\u016b\u0005\u001f\u0000\u0000\u016b\u016c\u00036\u001b\u0000\u016c"+
		"=\u0001\u0000\u0000\u0000\u016d\u0172\u00034\u001a\u0000\u016e\u016f\u0003"+
		"0\u0018\u0000\u016f\u0170\u0005\u0003\u0000\u0000\u0170\u0172\u0001\u0000"+
		"\u0000\u0000\u0171\u016d\u0001\u0000\u0000\u0000\u0171\u016e\u0001\u0000"+
		"\u0000\u0000\u0172\u0175\u0001\u0000\u0000\u0000\u0173\u0175\u0005\u0003"+
		"\u0000\u0000\u0174\u0171\u0001\u0000\u0000\u0000\u0174\u0173\u0001\u0000"+
		"\u0000\u0000\u0175?\u0001\u0000\u0000\u0000\u0176\u0178\u00030\u0018\u0000"+
		"\u0177\u0176\u0001\u0000\u0000\u0000\u0177\u0178\u0001\u0000\u0000\u0000"+
		"\u0178\u0179\u0001\u0000\u0000\u0000\u0179\u017a\u0005\u0003\u0000\u0000"+
		"\u017aA\u0001\u0000\u0000\u0000\u017b\u017d\u00030\u0018\u0000\u017c\u017b"+
		"\u0001\u0000\u0000\u0000\u017c\u017d\u0001\u0000\u0000\u0000\u017dC\u0001"+
		"\u0000\u0000\u0000\u017e\u017f\u00050\u0000\u0000\u017f\u0180\u0005\u001e"+
		"\u0000\u0000\u0180\u0181\u0003>\u001f\u0000\u0181\u0182\u0003@ \u0000"+
		"\u0182\u0183\u0003B!\u0000\u0183\u0184\u0005\u001f\u0000\u0000\u0184\u0185"+
		"\u00036\u001b\u0000\u0185E\u0001\u0000\u0000\u0000%ITX]afqv|\u0084\u0086"+
		"\u0091\u0097\u009f\u00a5\u00c0\u00c3\u00cd\u00d0\u00d6\u00df\u00f0\u0100"+
		"\u012e\u0134\u0136\u013c\u0144\u014d\u0152\u0156\u015a\u0165\u0171\u0174"+
		"\u0177\u017c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}