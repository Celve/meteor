package frontend.parser;
// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MeteorParser extends Parser {
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
		RULE_prog = 0, RULE_suite = 1, RULE_block = 2, RULE_field = 3, RULE_decl = 4, 
		RULE_def = 5, RULE_varType = 6, RULE_primitiveType = 7, RULE_voidType = 8, 
		RULE_nonPrimitiveType = 9, RULE_classSuite = 10, RULE_classDef = 11, RULE_classCtor = 12, 
		RULE_returnType = 13, RULE_funcSuite = 14, RULE_funcDef = 15, RULE_paramDecl = 16, 
		RULE_paramDeclList = 17, RULE_paramInputList = 18, RULE_lambdaDef = 19, 
		RULE_basicExpr = 20, RULE_stmt = 21, RULE_prefixOps = 22, RULE_expr = 23, 
		RULE_assignUnit = 24, RULE_varDecl = 25, RULE_jump = 26, RULE_simpleSuite = 27, 
		RULE_extendedBlock = 28, RULE_cond = 29, RULE_while = 30, RULE_forInitUnit = 31, 
		RULE_forCondUnit = 32, RULE_forStepUnit = 33, RULE_for = 34;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "suite", "block", "field", "decl", "def", "varType", "primitiveType", 
			"voidType", "nonPrimitiveType", "classSuite", "classDef", "classCtor", 
			"returnType", "funcSuite", "funcDef", "paramDecl", "paramDeclList", "paramInputList", 
			"lambdaDef", "basicExpr", "stmt", "prefixOps", "expr", "assignUnit", 
			"varDecl", "jump", "simpleSuite", "extendedBlock", "cond", "while", "forInitUnit", 
			"forCondUnit", "forStepUnit", "for"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "','", "'->'", "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", 
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

	public MeteorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			suite();
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
	public static class SuiteContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<DefContext> def() {
			return getRuleContexts(DefContext.class);
		}
		public DefContext def(int i) {
			return getRuleContext(DefContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public List<JumpContext> jump() {
			return getRuleContexts(JumpContext.class);
		}
		public JumpContext jump(int i) {
			return getRuleContext(JumpContext.class,i);
		}
		public SuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteContext suite() throws RecognitionException {
		SuiteContext _localctx = new SuiteContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_suite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 612348748860882994L) != 0) {
				{
				setState(77);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(72);
					stmt();
					}
					break;
				case 2:
					{
					setState(73);
					def();
					}
					break;
				case 3:
					{
					setState(74);
					block();
					}
					break;
				case 4:
					{
					setState(75);
					decl();
					}
					break;
				case 5:
					{
					setState(76);
					jump();
					}
					break;
				}
				}
				setState(81);
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
	public static class BlockContext extends ParserRuleContext {
		public ForContext for_() {
			return getRuleContext(ForContext.class,0);
		}
		public WhileContext while_() {
			return getRuleContext(WhileContext.class,0);
		}
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		try {
			setState(86);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case For:
				enterOuterAlt(_localctx, 1);
				{
				setState(82);
				for_();
				}
				break;
			case While:
				enterOuterAlt(_localctx, 2);
				{
				setState(83);
				while_();
				}
				break;
			case If:
				enterOuterAlt(_localctx, 3);
				{
				setState(84);
				cond();
				}
				break;
			case LeftBrace:
				enterOuterAlt(_localctx, 4);
				{
				setState(85);
				field();
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
	public static class FieldContext extends ParserRuleContext {
		public TerminalNode LeftBrace() { return getToken(MeteorParser.LeftBrace, 0); }
		public FuncSuiteContext funcSuite() {
			return getRuleContext(FuncSuiteContext.class,0);
		}
		public TerminalNode RightBrace() { return getToken(MeteorParser.RightBrace, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(LeftBrace);
			setState(89);
			funcSuite();
			setState(90);
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
	public static class DeclContext extends ParserRuleContext {
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			varDecl();
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_def);
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Class:
				enterOuterAlt(_localctx, 1);
				{
				setState(94);
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
				setState(95);
				funcDef();
				}
				break;
			case LeftBracket:
				enterOuterAlt(_localctx, 3);
				{
				setState(96);
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
		public List<TerminalNode> Brackets() { return getTokens(MeteorParser.Brackets); }
		public TerminalNode Brackets(int i) {
			return getToken(MeteorParser.Brackets, i);
		}
		public VarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterVarType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitVarType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitVarType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarTypeContext varType() throws RecognitionException {
		VarTypeContext _localctx = new VarTypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_varType);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
				{
				setState(99);
				primitiveType();
				}
				break;
			case Id:
				{
				setState(100);
				nonPrimitiveType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(106);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(103);
					match(Brackets);
					}
					} 
				}
				setState(108);
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
		public TerminalNode Bool() { return getToken(MeteorParser.Bool, 0); }
		public TerminalNode Int() { return getToken(MeteorParser.Int, 0); }
		public TerminalNode String() { return getToken(MeteorParser.String, 0); }
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterPrimitiveType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitPrimitiveType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
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
		public TerminalNode Void() { return getToken(MeteorParser.Void, 0); }
		public VoidTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voidType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterVoidType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitVoidType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitVoidType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoidTypeContext voidType() throws RecognitionException {
		VoidTypeContext _localctx = new VoidTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_voidType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
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
	public static class NonPrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public NonPrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonPrimitiveType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterNonPrimitiveType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitNonPrimitiveType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitNonPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonPrimitiveTypeContext nonPrimitiveType() throws RecognitionException {
		NonPrimitiveTypeContext _localctx = new NonPrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_nonPrimitiveType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
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
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterClassSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitClassSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitClassSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassSuiteContext classSuite() throws RecognitionException {
		ClassSuiteContext _localctx = new ClassSuiteContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_classSuite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 576461783095574528L) != 0) {
				{
				setState(118);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(115);
					decl();
					}
					break;
				case 2:
					{
					setState(116);
					funcDef();
					}
					break;
				case 3:
					{
					setState(117);
					classCtor();
					}
					break;
				}
				}
				setState(122);
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
	public static class ClassDefContext extends ParserRuleContext {
		public Token className;
		public TerminalNode Class() { return getToken(MeteorParser.Class, 0); }
		public TerminalNode LeftBrace() { return getToken(MeteorParser.LeftBrace, 0); }
		public ClassSuiteContext classSuite() {
			return getRuleContext(ClassSuiteContext.class,0);
		}
		public TerminalNode RightBrace() { return getToken(MeteorParser.RightBrace, 0); }
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitClassDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_classDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(Class);
			setState(124);
			((ClassDefContext)_localctx).className = match(Id);
			setState(125);
			match(LeftBrace);
			setState(126);
			classSuite();
			setState(127);
			match(RightBrace);
			setState(128);
			match(T__0);
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
		public TerminalNode LeftBrace() { return getToken(MeteorParser.LeftBrace, 0); }
		public FuncSuiteContext funcSuite() {
			return getRuleContext(FuncSuiteContext.class,0);
		}
		public TerminalNode RightBrace() { return getToken(MeteorParser.RightBrace, 0); }
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public ParamDeclListContext paramDeclList() {
			return getRuleContext(ParamDeclListContext.class,0);
		}
		public ClassCtorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classCtor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterClassCtor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitClassCtor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitClassCtor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassCtorContext classCtor() throws RecognitionException {
		ClassCtorContext _localctx = new ClassCtorContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_classCtor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			((ClassCtorContext)_localctx).classId = match(Id);
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LeftParen) {
				{
				setState(131);
				paramDeclList();
				}
			}

			setState(134);
			match(LeftBrace);
			setState(135);
			funcSuite();
			setState(136);
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterReturnType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitReturnType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitReturnType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_returnType);
		try {
			setState(141);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				primitiveType();
				}
				break;
			case Id:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				nonPrimitiveType();
				}
				break;
			case Void:
				enterOuterAlt(_localctx, 3);
				{
				setState(140);
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
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public List<JumpContext> jump() {
			return getRuleContexts(JumpContext.class);
		}
		public JumpContext jump(int i) {
			return getRuleContext(JumpContext.class,i);
		}
		public FuncSuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcSuite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterFuncSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitFuncSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitFuncSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncSuiteContext funcSuite() throws RecognitionException {
		FuncSuiteContext _localctx = new FuncSuiteContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_funcSuite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 612346481118150706L) != 0) {
				{
				setState(147);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(143);
					stmt();
					}
					break;
				case 2:
					{
					setState(144);
					block();
					}
					break;
				case 3:
					{
					setState(145);
					decl();
					}
					break;
				case 4:
					{
					setState(146);
					jump();
					}
					break;
				}
				}
				setState(151);
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
	public static class FuncDefContext extends ParserRuleContext {
		public Token funcName;
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public ParamDeclListContext paramDeclList() {
			return getRuleContext(ParamDeclListContext.class,0);
		}
		public TerminalNode LeftBrace() { return getToken(MeteorParser.LeftBrace, 0); }
		public FuncSuiteContext funcSuite() {
			return getRuleContext(FuncSuiteContext.class,0);
		}
		public TerminalNode RightBrace() { return getToken(MeteorParser.RightBrace, 0); }
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public FuncDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncDefContext funcDef() throws RecognitionException {
		FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_funcDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			returnType();
			setState(153);
			((FuncDefContext)_localctx).funcName = match(Id);
			setState(154);
			paramDeclList();
			setState(155);
			match(LeftBrace);
			setState(156);
			funcSuite();
			setState(157);
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
	public static class ParamDeclContext extends ParserRuleContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public ParamDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterParamDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitParamDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitParamDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamDeclContext paramDecl() throws RecognitionException {
		ParamDeclContext _localctx = new ParamDeclContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_paramDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			varType();
			setState(160);
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
	public static class ParamDeclListContext extends ParserRuleContext {
		public TerminalNode LeftParen() { return getToken(MeteorParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MeteorParser.RightParen, 0); }
		public List<ParamDeclContext> paramDecl() {
			return getRuleContexts(ParamDeclContext.class);
		}
		public ParamDeclContext paramDecl(int i) {
			return getRuleContext(ParamDeclContext.class,i);
		}
		public ParamDeclListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramDeclList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterParamDeclList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitParamDeclList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitParamDeclList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamDeclListContext paramDeclList() throws RecognitionException {
		ParamDeclListContext _localctx = new ParamDeclListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_paramDeclList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(LeftParen);
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 576461714376097792L) != 0) {
				{
				setState(163);
				paramDecl();
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(164);
					match(T__1);
					setState(165);
					paramDecl();
					}
					}
					setState(170);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(173);
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
		public TerminalNode LeftParen() { return getToken(MeteorParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MeteorParser.RightParen, 0); }
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterParamInputList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitParamInputList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitParamInputList(this);
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
			setState(175);
			match(LeftParen);
			setState(184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421728301104L) != 0) {
				{
				setState(176);
				expr(0);
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(177);
					match(T__1);
					setState(178);
					expr(0);
					}
					}
					setState(183);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(186);
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
		public TerminalNode LeftBracket() { return getToken(MeteorParser.LeftBracket, 0); }
		public TerminalNode RightBracket() { return getToken(MeteorParser.RightBracket, 0); }
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public TerminalNode LeftBrace() { return getToken(MeteorParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(MeteorParser.RightBrace, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public TerminalNode BitwiseAnd() { return getToken(MeteorParser.BitwiseAnd, 0); }
		public LambdaDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterLambdaDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitLambdaDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitLambdaDef(this);
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
			setState(188);
			match(LeftBracket);
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitwiseAnd) {
				{
				setState(189);
				((LambdaDefContext)_localctx).op = match(BitwiseAnd);
				}
			}

			setState(192);
			match(RightBracket);
			setState(193);
			paramInputList();
			setState(194);
			match(T__2);
			setState(195);
			match(LeftBrace);
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421728301106L) != 0) {
				{
				{
				setState(196);
				stmt();
				}
				}
				setState(201);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(202);
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
	public static class BasicExprContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(MeteorParser.IntegerLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(MeteorParser.StringLiteral, 0); }
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public TerminalNode This() { return getToken(MeteorParser.This, 0); }
		public TerminalNode True() { return getToken(MeteorParser.True, 0); }
		public TerminalNode False() { return getToken(MeteorParser.False, 0); }
		public TerminalNode Null() { return getToken(MeteorParser.Null, 0); }
		public BasicExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterBasicExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitBasicExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitBasicExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BasicExprContext basicExpr() throws RecognitionException {
		BasicExprContext _localctx = new BasicExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_basicExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 603548320765313024L) != 0) ) {
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
	public static class StmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421728301104L) != 0) {
				{
				setState(206);
				expr(0);
				}
			}

			setState(209);
			match(T__0);
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
		public TerminalNode Increment() { return getToken(MeteorParser.Increment, 0); }
		public TerminalNode Decrement() { return getToken(MeteorParser.Decrement, 0); }
		public TerminalNode Add() { return getToken(MeteorParser.Add, 0); }
		public TerminalNode Sub() { return getToken(MeteorParser.Sub, 0); }
		public TerminalNode LogicalNot() { return getToken(MeteorParser.LogicalNot, 0); }
		public TerminalNode BitwiseNot() { return getToken(MeteorParser.BitwiseNot, 0); }
		public PrefixOpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixOps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterPrefixOps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitPrefixOps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitPrefixOps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixOpsContext prefixOps() throws RecognitionException {
		PrefixOpsContext _localctx = new PrefixOpsContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_prefixOps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterPrefixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitPrefixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitPrefixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MemberAccessContext extends ExprContext {
		public Token classMember;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Access() { return getToken(MeteorParser.Access, 0); }
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public MemberAccessContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterMemberAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitMemberAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitMemberAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MethodAccessContext extends ExprContext {
		public Token methodName;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Access() { return getToken(MeteorParser.Access, 0); }
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public MethodAccessContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterMethodAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitMethodAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitMethodAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SuffixExprContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Increment() { return getToken(MeteorParser.Increment, 0); }
		public TerminalNode Decrement() { return getToken(MeteorParser.Decrement, 0); }
		public SuffixExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterSuffixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitSuffixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitSuffixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PriorExprContext extends ExprContext {
		public TerminalNode LeftParen() { return getToken(MeteorParser.LeftParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(MeteorParser.RightParen, 0); }
		public PriorExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterPriorExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitPriorExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitPriorExpr(this);
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
		public TerminalNode Mul() { return getToken(MeteorParser.Mul, 0); }
		public TerminalNode Div() { return getToken(MeteorParser.Div, 0); }
		public TerminalNode Mod() { return getToken(MeteorParser.Mod, 0); }
		public TerminalNode Add() { return getToken(MeteorParser.Add, 0); }
		public TerminalNode Sub() { return getToken(MeteorParser.Sub, 0); }
		public TerminalNode LeftShift() { return getToken(MeteorParser.LeftShift, 0); }
		public TerminalNode RightShift() { return getToken(MeteorParser.RightShift, 0); }
		public TerminalNode Less() { return getToken(MeteorParser.Less, 0); }
		public TerminalNode LessEqual() { return getToken(MeteorParser.LessEqual, 0); }
		public TerminalNode Greater() { return getToken(MeteorParser.Greater, 0); }
		public TerminalNode GreaterEqual() { return getToken(MeteorParser.GreaterEqual, 0); }
		public TerminalNode Equal() { return getToken(MeteorParser.Equal, 0); }
		public TerminalNode NotEqual() { return getToken(MeteorParser.NotEqual, 0); }
		public TerminalNode BitwiseAnd() { return getToken(MeteorParser.BitwiseAnd, 0); }
		public TerminalNode BitwiseXor() { return getToken(MeteorParser.BitwiseXor, 0); }
		public TerminalNode BitwiseOr() { return getToken(MeteorParser.BitwiseOr, 0); }
		public TerminalNode LogicalAnd() { return getToken(MeteorParser.LogicalAnd, 0); }
		public TerminalNode LogicalOr() { return getToken(MeteorParser.LogicalOr, 0); }
		public BinaryExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitBinaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncCallContext extends ExprContext {
		public Token funcName;
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public FuncCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitFuncCall(this);
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
		public List<TerminalNode> LeftBracket() { return getTokens(MeteorParser.LeftBracket); }
		public TerminalNode LeftBracket(int i) {
			return getToken(MeteorParser.LeftBracket, i);
		}
		public List<TerminalNode> RightBracket() { return getTokens(MeteorParser.RightBracket); }
		public TerminalNode RightBracket(int i) {
			return getToken(MeteorParser.RightBracket, i);
		}
		public ArrayAccessContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterArrayAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitArrayAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitArrayAccess(this);
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InitExprContext extends ExprContext {
		public TerminalNode New() { return getToken(MeteorParser.New, 0); }
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<TerminalNode> LeftBracket() { return getTokens(MeteorParser.LeftBracket); }
		public TerminalNode LeftBracket(int i) {
			return getToken(MeteorParser.LeftBracket, i);
		}
		public List<TerminalNode> RightBracket() { return getTokens(MeteorParser.RightBracket); }
		public TerminalNode RightBracket(int i) {
			return getToken(MeteorParser.RightBracket, i);
		}
		public List<TerminalNode> Brackets() { return getTokens(MeteorParser.Brackets); }
		public TerminalNode Brackets(int i) {
			return getToken(MeteorParser.Brackets, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public InitExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterInitExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitInitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitInitExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LambdaCallContext extends ExprContext {
		public LambdaDefContext lambdaDef() {
			return getRuleContext(LambdaDefContext.class,0);
		}
		public ParamInputListContext paramInputList() {
			return getRuleContext(ParamInputListContext.class,0);
		}
		public LambdaCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterLambdaCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitLambdaCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitLambdaCall(this);
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
		public TerminalNode Assign() { return getToken(MeteorParser.Assign, 0); }
		public AssignExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterAssignExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitAssignExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitAssignExpr(this);
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
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				_localctx = new PriorExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(214);
				match(LeftParen);
				setState(215);
				expr(0);
				setState(216);
				match(RightParen);
				}
				break;
			case 2:
				{
				_localctx = new AtomContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(218);
				basicExpr();
				}
				break;
			case 3:
				{
				_localctx = new InitExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(219);
				match(New);
				setState(220);
				varType();
				setState(228);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(221);
						match(LeftBracket);
						setState(223);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421728301104L) != 0) {
							{
							setState(222);
							expr(0);
							}
						}

						setState(225);
						match(RightBracket);
						}
						} 
					}
					setState(230);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				setState(234);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(231);
						match(Brackets);
						}
						} 
					}
					setState(236);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				}
				}
				break;
			case 4:
				{
				_localctx = new LambdaCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(237);
				lambdaDef();
				setState(238);
				paramInputList();
				}
				break;
			case 5:
				{
				_localctx = new FuncCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				{
				setState(240);
				((FuncCallContext)_localctx).funcName = match(Id);
				}
				setState(241);
				paramInputList();
				}
				break;
			case 6:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(242);
				prefixOps();
				setState(243);
				expr(12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(300);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(298);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(247);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(248);
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
						setState(249);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(250);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(251);
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
						setState(252);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(253);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(254);
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
						setState(255);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(256);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(257);
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
						setState(258);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(259);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(260);
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
						setState(261);
						expr(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(262);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(263);
						((BinaryExprContext)_localctx).op = match(BitwiseAnd);
						setState(264);
						expr(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(265);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(266);
						((BinaryExprContext)_localctx).op = match(BitwiseXor);
						setState(267);
						expr(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(268);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(269);
						((BinaryExprContext)_localctx).op = match(BitwiseOr);
						setState(270);
						expr(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(271);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(272);
						((BinaryExprContext)_localctx).op = match(LogicalAnd);
						setState(273);
						expr(4);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(274);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(275);
						((BinaryExprContext)_localctx).op = match(LogicalOr);
						setState(276);
						expr(3);
						}
						break;
					case 11:
						{
						_localctx = new AssignExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(277);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(278);
						match(Assign);
						setState(279);
						expr(1);
						}
						break;
					case 12:
						{
						_localctx = new MethodAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(280);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(281);
						match(Access);
						{
						setState(282);
						((MethodAccessContext)_localctx).methodName = match(Id);
						}
						setState(283);
						paramInputList();
						}
						break;
					case 13:
						{
						_localctx = new MemberAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(284);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(285);
						match(Access);
						{
						setState(286);
						((MemberAccessContext)_localctx).classMember = match(Id);
						}
						}
						break;
					case 14:
						{
						_localctx = new ArrayAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(287);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(292); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(288);
								match(LeftBracket);
								setState(289);
								expr(0);
								setState(290);
								match(RightBracket);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(294); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 15:
						{
						_localctx = new SuffixExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(296);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(297);
						((SuffixExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Increment || _la==Decrement) ) {
							((SuffixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
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
				setState(302);
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
		public TerminalNode Id() { return getToken(MeteorParser.Id, 0); }
		public TerminalNode Assign() { return getToken(MeteorParser.Assign, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterAssignUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitAssignUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitAssignUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignUnitContext assignUnit() throws RecognitionException {
		AssignUnitContext _localctx = new AssignUnitContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_assignUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			match(Id);
			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(304);
				match(Assign);
				setState(305);
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitVarDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			varType();
			setState(309);
			assignUnit();
			setState(314);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(310);
				match(T__1);
				setState(311);
				assignUnit();
				}
				}
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(317);
			match(T__0);
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
	public static class JumpContext extends ParserRuleContext {
		public Token op;
		public TerminalNode Return() { return getToken(MeteorParser.Return, 0); }
		public TerminalNode Break() { return getToken(MeteorParser.Break, 0); }
		public TerminalNode Continue() { return getToken(MeteorParser.Continue, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public JumpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jump; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterJump(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitJump(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitJump(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpContext jump() throws RecognitionException {
		JumpContext _localctx = new JumpContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_jump);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Return:
				{
				setState(319);
				((JumpContext)_localctx).op = match(Return);
				setState(321);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421728301104L) != 0) {
					{
					setState(320);
					expr(0);
					}
				}

				}
				break;
			case Break:
				{
				setState(323);
				((JumpContext)_localctx).op = match(Break);
				}
				break;
			case Continue:
				{
				setState(324);
				((JumpContext)_localctx).op = match(Continue);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(327);
			match(T__0);
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
	public static class SimpleSuiteContext extends ParserRuleContext {
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public JumpContext jump() {
			return getRuleContext(JumpContext.class,0);
		}
		public SimpleSuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleSuite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterSimpleSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitSimpleSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitSimpleSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleSuiteContext simpleSuite() throws RecognitionException {
		SimpleSuiteContext _localctx = new SimpleSuiteContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_simpleSuite);
		try {
			setState(331);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case Add:
			case Sub:
			case LogicalNot:
			case BitwiseNot:
			case Increment:
			case Decrement:
			case LeftBracket:
			case LeftParen:
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
				setState(329);
				stmt();
				}
				break;
			case Break:
			case Continue:
			case Return:
				enterOuterAlt(_localctx, 2);
				{
				setState(330);
				jump();
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
	public static class ExtendedBlockContext extends ParserRuleContext {
		public SimpleSuiteContext simpleSuite() {
			return getRuleContext(SimpleSuiteContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExtendedBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extendedBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterExtendedBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitExtendedBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitExtendedBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExtendedBlockContext extendedBlock() throws RecognitionException {
		ExtendedBlockContext _localctx = new ExtendedBlockContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_extendedBlock);
		try {
			setState(335);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case Add:
			case Sub:
			case LogicalNot:
			case BitwiseNot:
			case Increment:
			case Decrement:
			case LeftBracket:
			case LeftParen:
			case New:
			case Null:
			case True:
			case False:
			case This:
			case Break:
			case Continue:
			case Return:
			case IntegerLiteral:
			case StringLiteral:
			case Id:
				enterOuterAlt(_localctx, 1);
				{
				setState(333);
				simpleSuite();
				}
				break;
			case LeftBrace:
			case If:
			case For:
			case While:
				enterOuterAlt(_localctx, 2);
				{
				setState(334);
				block();
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
	public static class CondContext extends ParserRuleContext {
		public TerminalNode If() { return getToken(MeteorParser.If, 0); }
		public TerminalNode LeftParen() { return getToken(MeteorParser.LeftParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(MeteorParser.RightParen, 0); }
		public List<ExtendedBlockContext> extendedBlock() {
			return getRuleContexts(ExtendedBlockContext.class);
		}
		public ExtendedBlockContext extendedBlock(int i) {
			return getRuleContext(ExtendedBlockContext.class,i);
		}
		public TerminalNode Else() { return getToken(MeteorParser.Else, 0); }
		public CondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterCond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitCond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitCond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondContext cond() throws RecognitionException {
		CondContext _localctx = new CondContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_cond);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(If);
			setState(338);
			match(LeftParen);
			setState(339);
			expr(0);
			setState(340);
			match(RightParen);
			setState(341);
			extendedBlock();
			setState(344);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(342);
				match(Else);
				setState(343);
				extendedBlock();
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
	public static class WhileContext extends ParserRuleContext {
		public TerminalNode While() { return getToken(MeteorParser.While, 0); }
		public TerminalNode LeftParen() { return getToken(MeteorParser.LeftParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(MeteorParser.RightParen, 0); }
		public ExtendedBlockContext extendedBlock() {
			return getRuleContext(ExtendedBlockContext.class,0);
		}
		public WhileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileContext while_() throws RecognitionException {
		WhileContext _localctx = new WhileContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_while);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			match(While);
			setState(347);
			match(LeftParen);
			setState(348);
			expr(0);
			setState(349);
			match(RightParen);
			setState(350);
			extendedBlock();
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterForInitUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitForInitUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitForInitUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitUnitContext forInitUnit() throws RecognitionException {
		ForInitUnitContext _localctx = new ForInitUnitContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_forInitUnit);
		try {
			setState(359);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Add:
			case Sub:
			case LogicalNot:
			case BitwiseNot:
			case Increment:
			case Decrement:
			case LeftBracket:
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
				setState(356);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(352);
					varDecl();
					}
					break;
				case 2:
					{
					setState(353);
					expr(0);
					setState(354);
					match(T__0);
					}
					break;
				}
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(358);
				match(T__0);
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterForCondUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitForCondUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitForCondUnit(this);
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
			setState(362);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421728301104L) != 0) {
				{
				setState(361);
				expr(0);
				}
			}

			setState(364);
			match(T__0);
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
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterForStepUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitForStepUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitForStepUnit(this);
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
			setState(367);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 603549421728301104L) != 0) {
				{
				setState(366);
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
	public static class ForContext extends ParserRuleContext {
		public TerminalNode For() { return getToken(MeteorParser.For, 0); }
		public TerminalNode LeftParen() { return getToken(MeteorParser.LeftParen, 0); }
		public ForInitUnitContext forInitUnit() {
			return getRuleContext(ForInitUnitContext.class,0);
		}
		public ForCondUnitContext forCondUnit() {
			return getRuleContext(ForCondUnitContext.class,0);
		}
		public ForStepUnitContext forStepUnit() {
			return getRuleContext(ForStepUnitContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(MeteorParser.RightParen, 0); }
		public ExtendedBlockContext extendedBlock() {
			return getRuleContext(ExtendedBlockContext.class,0);
		}
		public ForContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitFor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForContext for_() throws RecognitionException {
		ForContext _localctx = new ForContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_for);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			match(For);
			setState(370);
			match(LeftParen);
			setState(371);
			forInitUnit();
			setState(372);
			forCondUnit();
			setState(373);
			forStepUnit();
			setState(374);
			match(RightParen);
			setState(375);
			extendedBlock();
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
		case 23:
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
			return precpred(_ctx, 16);
		case 12:
			return precpred(_ctx, 15);
		case 13:
			return precpred(_ctx, 14);
		case 14:
			return precpred(_ctx, 13);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001;\u017a\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
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
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0005\u0001N\b\u0001\n\u0001\f\u0001Q\t\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002W\b\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0003\u0005b\b\u0005\u0001\u0006\u0001\u0006\u0003"+
		"\u0006f\b\u0006\u0001\u0006\u0005\u0006i\b\u0006\n\u0006\f\u0006l\t\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0005\nw\b\n\n\n\f\nz\t\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0003"+
		"\f\u0085\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0003"+
		"\r\u008e\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e"+
		"\u0094\b\u000e\n\u000e\f\u000e\u0097\t\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005"+
		"\u0011\u00a7\b\u0011\n\u0011\f\u0011\u00aa\t\u0011\u0003\u0011\u00ac\b"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0005\u0012\u00b4\b\u0012\n\u0012\f\u0012\u00b7\t\u0012\u0003\u0012"+
		"\u00b9\b\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0003\u0013"+
		"\u00bf\b\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0005\u0013\u00c6\b\u0013\n\u0013\f\u0013\u00c9\t\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0003\u0015\u00d0\b\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0003\u0017\u00e0\b\u0017\u0001\u0017\u0005\u0017\u00e3"+
		"\b\u0017\n\u0017\f\u0017\u00e6\t\u0017\u0001\u0017\u0005\u0017\u00e9\b"+
		"\u0017\n\u0017\f\u0017\u00ec\t\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017"+
		"\u00f6\b\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0004\u0017\u0125\b\u0017"+
		"\u000b\u0017\f\u0017\u0126\u0001\u0017\u0001\u0017\u0005\u0017\u012b\b"+
		"\u0017\n\u0017\f\u0017\u012e\t\u0017\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0003\u0018\u0133\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0005\u0019\u0139\b\u0019\n\u0019\f\u0019\u013c\t\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u001a\u0001\u001a\u0003\u001a\u0142\b\u001a\u0001\u001a\u0001"+
		"\u001a\u0003\u001a\u0146\b\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001"+
		"\u001b\u0003\u001b\u014c\b\u001b\u0001\u001c\u0001\u001c\u0003\u001c\u0150"+
		"\b\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0003\u001d\u0159\b\u001d\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0003\u001f\u0165\b\u001f\u0001\u001f\u0003\u001f\u0168"+
		"\b\u001f\u0001 \u0003 \u016b\b \u0001 \u0001 \u0001!\u0003!\u0170\b!\u0001"+
		"\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0000"+
		"\u0001.#\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.02468:<>@BD\u0000\t\u0001\u0000%\'\u0003\u0000"+
		"*-56;;\u0004\u0000\u0004\u0005\u0011\u0011\u0017\u0017\u0019\u001a\u0001"+
		"\u0000\u0006\b\u0001\u0000\u0004\u0005\u0001\u0000\u0012\u0013\u0001\u0000"+
		"\t\f\u0001\u0000\r\u000e\u0001\u0000\u0019\u001a\u0197\u0000F\u0001\u0000"+
		"\u0000\u0000\u0002O\u0001\u0000\u0000\u0000\u0004V\u0001\u0000\u0000\u0000"+
		"\u0006X\u0001\u0000\u0000\u0000\b\\\u0001\u0000\u0000\u0000\na\u0001\u0000"+
		"\u0000\u0000\fe\u0001\u0000\u0000\u0000\u000em\u0001\u0000\u0000\u0000"+
		"\u0010o\u0001\u0000\u0000\u0000\u0012q\u0001\u0000\u0000\u0000\u0014x"+
		"\u0001\u0000\u0000\u0000\u0016{\u0001\u0000\u0000\u0000\u0018\u0082\u0001"+
		"\u0000\u0000\u0000\u001a\u008d\u0001\u0000\u0000\u0000\u001c\u0095\u0001"+
		"\u0000\u0000\u0000\u001e\u0098\u0001\u0000\u0000\u0000 \u009f\u0001\u0000"+
		"\u0000\u0000\"\u00a2\u0001\u0000\u0000\u0000$\u00af\u0001\u0000\u0000"+
		"\u0000&\u00bc\u0001\u0000\u0000\u0000(\u00cc\u0001\u0000\u0000\u0000*"+
		"\u00cf\u0001\u0000\u0000\u0000,\u00d3\u0001\u0000\u0000\u0000.\u00f5\u0001"+
		"\u0000\u0000\u00000\u012f\u0001\u0000\u0000\u00002\u0134\u0001\u0000\u0000"+
		"\u00004\u0145\u0001\u0000\u0000\u00006\u014b\u0001\u0000\u0000\u00008"+
		"\u014f\u0001\u0000\u0000\u0000:\u0151\u0001\u0000\u0000\u0000<\u015a\u0001"+
		"\u0000\u0000\u0000>\u0167\u0001\u0000\u0000\u0000@\u016a\u0001\u0000\u0000"+
		"\u0000B\u016f\u0001\u0000\u0000\u0000D\u0171\u0001\u0000\u0000\u0000F"+
		"G\u0003\u0002\u0001\u0000G\u0001\u0001\u0000\u0000\u0000HN\u0003*\u0015"+
		"\u0000IN\u0003\n\u0005\u0000JN\u0003\u0004\u0002\u0000KN\u0003\b\u0004"+
		"\u0000LN\u00034\u001a\u0000MH\u0001\u0000\u0000\u0000MI\u0001\u0000\u0000"+
		"\u0000MJ\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000ML\u0001\u0000"+
		"\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000\u0000OP\u0001"+
		"\u0000\u0000\u0000P\u0003\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000"+
		"\u0000RW\u0003D\"\u0000SW\u0003<\u001e\u0000TW\u0003:\u001d\u0000UW\u0003"+
		"\u0006\u0003\u0000VR\u0001\u0000\u0000\u0000VS\u0001\u0000\u0000\u0000"+
		"VT\u0001\u0000\u0000\u0000VU\u0001\u0000\u0000\u0000W\u0005\u0001\u0000"+
		"\u0000\u0000XY\u0005 \u0000\u0000YZ\u0003\u001c\u000e\u0000Z[\u0005!\u0000"+
		"\u0000[\u0007\u0001\u0000\u0000\u0000\\]\u00032\u0019\u0000]\t\u0001\u0000"+
		"\u0000\u0000^b\u0003\u0016\u000b\u0000_b\u0003\u001e\u000f\u0000`b\u0003"+
		"&\u0013\u0000a^\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000a`\u0001"+
		"\u0000\u0000\u0000b\u000b\u0001\u0000\u0000\u0000cf\u0003\u000e\u0007"+
		"\u0000df\u0003\u0012\t\u0000ec\u0001\u0000\u0000\u0000ed\u0001\u0000\u0000"+
		"\u0000fj\u0001\u0000\u0000\u0000gi\u0005\u001b\u0000\u0000hg\u0001\u0000"+
		"\u0000\u0000il\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000jk\u0001"+
		"\u0000\u0000\u0000k\r\u0001\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000"+
		"mn\u0007\u0000\u0000\u0000n\u000f\u0001\u0000\u0000\u0000op\u0005$\u0000"+
		"\u0000p\u0011\u0001\u0000\u0000\u0000qr\u0005;\u0000\u0000r\u0013\u0001"+
		"\u0000\u0000\u0000sw\u0003\b\u0004\u0000tw\u0003\u001e\u000f\u0000uw\u0003"+
		"\u0018\f\u0000vs\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000vu\u0001"+
		"\u0000\u0000\u0000wz\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000"+
		"xy\u0001\u0000\u0000\u0000y\u0015\u0001\u0000\u0000\u0000zx\u0001\u0000"+
		"\u0000\u0000{|\u0005)\u0000\u0000|}\u0005;\u0000\u0000}~\u0005 \u0000"+
		"\u0000~\u007f\u0003\u0014\n\u0000\u007f\u0080\u0005!\u0000\u0000\u0080"+
		"\u0081\u0005\u0001\u0000\u0000\u0081\u0017\u0001\u0000\u0000\u0000\u0082"+
		"\u0084\u0005;\u0000\u0000\u0083\u0085\u0003\"\u0011\u0000\u0084\u0083"+
		"\u0001\u0000\u0000\u0000\u0084\u0085\u0001\u0000\u0000\u0000\u0085\u0086"+
		"\u0001\u0000\u0000\u0000\u0086\u0087\u0005 \u0000\u0000\u0087\u0088\u0003"+
		"\u001c\u000e\u0000\u0088\u0089\u0005!\u0000\u0000\u0089\u0019\u0001\u0000"+
		"\u0000\u0000\u008a\u008e\u0003\u000e\u0007\u0000\u008b\u008e\u0003\u0012"+
		"\t\u0000\u008c\u008e\u0003\u0010\b\u0000\u008d\u008a\u0001\u0000\u0000"+
		"\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008c\u0001\u0000\u0000"+
		"\u0000\u008e\u001b\u0001\u0000\u0000\u0000\u008f\u0094\u0003*\u0015\u0000"+
		"\u0090\u0094\u0003\u0004\u0002\u0000\u0091\u0094\u0003\b\u0004\u0000\u0092"+
		"\u0094\u00034\u001a\u0000\u0093\u008f\u0001\u0000\u0000\u0000\u0093\u0090"+
		"\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0093\u0092"+
		"\u0001\u0000\u0000\u0000\u0094\u0097\u0001\u0000\u0000\u0000\u0095\u0093"+
		"\u0001\u0000\u0000\u0000\u0095\u0096\u0001\u0000\u0000\u0000\u0096\u001d"+
		"\u0001\u0000\u0000\u0000\u0097\u0095\u0001\u0000\u0000\u0000\u0098\u0099"+
		"\u0003\u001a\r\u0000\u0099\u009a\u0005;\u0000\u0000\u009a\u009b\u0003"+
		"\"\u0011\u0000\u009b\u009c\u0005 \u0000\u0000\u009c\u009d\u0003\u001c"+
		"\u000e\u0000\u009d\u009e\u0005!\u0000\u0000\u009e\u001f\u0001\u0000\u0000"+
		"\u0000\u009f\u00a0\u0003\f\u0006\u0000\u00a0\u00a1\u0005;\u0000\u0000"+
		"\u00a1!\u0001\u0000\u0000\u0000\u00a2\u00ab\u0005\u001e\u0000\u0000\u00a3"+
		"\u00a8\u0003 \u0010\u0000\u00a4\u00a5\u0005\u0002\u0000\u0000\u00a5\u00a7"+
		"\u0003 \u0010\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001"+
		"\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001"+
		"\u0000\u0000\u0000\u00a9\u00ac\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001"+
		"\u0000\u0000\u0000\u00ab\u00a3\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001"+
		"\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005"+
		"\u001f\u0000\u0000\u00ae#\u0001\u0000\u0000\u0000\u00af\u00b8\u0005\u001e"+
		"\u0000\u0000\u00b0\u00b5\u0003.\u0017\u0000\u00b1\u00b2\u0005\u0002\u0000"+
		"\u0000\u00b2\u00b4\u0003.\u0017\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000"+
		"\u00b4\u00b7\u0001\u0000\u0000\u0000\u00b5\u00b3\u0001\u0000\u0000\u0000"+
		"\u00b5\u00b6\u0001\u0000\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000"+
		"\u00b7\u00b5\u0001\u0000\u0000\u0000\u00b8\u00b0\u0001\u0000\u0000\u0000"+
		"\u00b8\u00b9\u0001\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000"+
		"\u00ba\u00bb\u0005\u001f\u0000\u0000\u00bb%\u0001\u0000\u0000\u0000\u00bc"+
		"\u00be\u0005\u001c\u0000\u0000\u00bd\u00bf\u0005\u0014\u0000\u0000\u00be"+
		"\u00bd\u0001\u0000\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c0\u0001\u0000\u0000\u0000\u00c0\u00c1\u0005\u001d\u0000\u0000\u00c1"+
		"\u00c2\u0003$\u0012\u0000\u00c2\u00c3\u0005\u0003\u0000\u0000\u00c3\u00c7"+
		"\u0005 \u0000\u0000\u00c4\u00c6\u0003*\u0015\u0000\u00c5\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c6\u00c9\u0001\u0000\u0000\u0000\u00c7\u00c5\u0001\u0000"+
		"\u0000\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000\u00c8\u00ca\u0001\u0000"+
		"\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00ca\u00cb\u0005!\u0000"+
		"\u0000\u00cb\'\u0001\u0000\u0000\u0000\u00cc\u00cd\u0007\u0001\u0000\u0000"+
		"\u00cd)\u0001\u0000\u0000\u0000\u00ce\u00d0\u0003.\u0017\u0000\u00cf\u00ce"+
		"\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000\u00d0\u00d1"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\u0001\u0000\u0000\u00d2+\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0007\u0002\u0000\u0000\u00d4-\u0001\u0000"+
		"\u0000\u0000\u00d5\u00d6\u0006\u0017\uffff\uffff\u0000\u00d6\u00d7\u0005"+
		"\u001e\u0000\u0000\u00d7\u00d8\u0003.\u0017\u0000\u00d8\u00d9\u0005\u001f"+
		"\u0000\u0000\u00d9\u00f6\u0001\u0000\u0000\u0000\u00da\u00f6\u0003(\u0014"+
		"\u0000\u00db\u00dc\u0005(\u0000\u0000\u00dc\u00e4\u0003\f\u0006\u0000"+
		"\u00dd\u00df\u0005\u001c\u0000\u0000\u00de\u00e0\u0003.\u0017\u0000\u00df"+
		"\u00de\u0001\u0000\u0000\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0"+
		"\u00e1\u0001\u0000\u0000\u0000\u00e1\u00e3\u0005\u001d\u0000\u0000\u00e2"+
		"\u00dd\u0001\u0000\u0000\u0000\u00e3\u00e6\u0001\u0000\u0000\u0000\u00e4"+
		"\u00e2\u0001\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000\u0000\u0000\u00e5"+
		"\u00ea\u0001\u0000\u0000\u0000\u00e6\u00e4\u0001\u0000\u0000\u0000\u00e7"+
		"\u00e9\u0005\u001b\u0000\u0000\u00e8\u00e7\u0001\u0000\u0000\u0000\u00e9"+
		"\u00ec\u0001\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00ea"+
		"\u00eb\u0001\u0000\u0000\u0000\u00eb\u00f6\u0001\u0000\u0000\u0000\u00ec"+
		"\u00ea\u0001\u0000\u0000\u0000\u00ed\u00ee\u0003&\u0013\u0000\u00ee\u00ef"+
		"\u0003$\u0012\u0000\u00ef\u00f6\u0001\u0000\u0000\u0000\u00f0\u00f1\u0005"+
		";\u0000\u0000\u00f1\u00f6\u0003$\u0012\u0000\u00f2\u00f3\u0003,\u0016"+
		"\u0000\u00f3\u00f4\u0003.\u0017\f\u00f4\u00f6\u0001\u0000\u0000\u0000"+
		"\u00f5\u00d5\u0001\u0000\u0000\u0000\u00f5\u00da\u0001\u0000\u0000\u0000"+
		"\u00f5\u00db\u0001\u0000\u0000\u0000\u00f5\u00ed\u0001\u0000\u0000\u0000"+
		"\u00f5\u00f0\u0001\u0000\u0000\u0000\u00f5\u00f2\u0001\u0000\u0000\u0000"+
		"\u00f6\u012c\u0001\u0000\u0000\u0000\u00f7\u00f8\n\u000b\u0000\u0000\u00f8"+
		"\u00f9\u0007\u0003\u0000\u0000\u00f9\u012b\u0003.\u0017\f\u00fa\u00fb"+
		"\n\n\u0000\u0000\u00fb\u00fc\u0007\u0004\u0000\u0000\u00fc\u012b\u0003"+
		".\u0017\u000b\u00fd\u00fe\n\t\u0000\u0000\u00fe\u00ff\u0007\u0005\u0000"+
		"\u0000\u00ff\u012b\u0003.\u0017\n\u0100\u0101\n\b\u0000\u0000\u0101\u0102"+
		"\u0007\u0006\u0000\u0000\u0102\u012b\u0003.\u0017\t\u0103\u0104\n\u0007"+
		"\u0000\u0000\u0104\u0105\u0007\u0007\u0000\u0000\u0105\u012b\u0003.\u0017"+
		"\b\u0106\u0107\n\u0006\u0000\u0000\u0107\u0108\u0005\u0014\u0000\u0000"+
		"\u0108\u012b\u0003.\u0017\u0007\u0109\u010a\n\u0005\u0000\u0000\u010a"+
		"\u010b\u0005\u0016\u0000\u0000\u010b\u012b\u0003.\u0017\u0006\u010c\u010d"+
		"\n\u0004\u0000\u0000\u010d\u010e\u0005\u0015\u0000\u0000\u010e\u012b\u0003"+
		".\u0017\u0005\u010f\u0110\n\u0003\u0000\u0000\u0110\u0111\u0005\u000f"+
		"\u0000\u0000\u0111\u012b\u0003.\u0017\u0004\u0112\u0113\n\u0002\u0000"+
		"\u0000\u0113\u0114\u0005\u0010\u0000\u0000\u0114\u012b\u0003.\u0017\u0003"+
		"\u0115\u0116\n\u0001\u0000\u0000\u0116\u0117\u0005\u0018\u0000\u0000\u0117"+
		"\u012b\u0003.\u0017\u0001\u0118\u0119\n\u0010\u0000\u0000\u0119\u011a"+
		"\u0005:\u0000\u0000\u011a\u011b\u0005;\u0000\u0000\u011b\u012b\u0003$"+
		"\u0012\u0000\u011c\u011d\n\u000f\u0000\u0000\u011d\u011e\u0005:\u0000"+
		"\u0000\u011e\u012b\u0005;\u0000\u0000\u011f\u0124\n\u000e\u0000\u0000"+
		"\u0120\u0121\u0005\u001c\u0000\u0000\u0121\u0122\u0003.\u0017\u0000\u0122"+
		"\u0123\u0005\u001d\u0000\u0000\u0123\u0125\u0001\u0000\u0000\u0000\u0124"+
		"\u0120\u0001\u0000\u0000\u0000\u0125\u0126\u0001\u0000\u0000\u0000\u0126"+
		"\u0124\u0001\u0000\u0000\u0000\u0126\u0127\u0001\u0000\u0000\u0000\u0127"+
		"\u012b\u0001\u0000\u0000\u0000\u0128\u0129\n\r\u0000\u0000\u0129\u012b"+
		"\u0007\b\u0000\u0000\u012a\u00f7\u0001\u0000\u0000\u0000\u012a\u00fa\u0001"+
		"\u0000\u0000\u0000\u012a\u00fd\u0001\u0000\u0000\u0000\u012a\u0100\u0001"+
		"\u0000\u0000\u0000\u012a\u0103\u0001\u0000\u0000\u0000\u012a\u0106\u0001"+
		"\u0000\u0000\u0000\u012a\u0109\u0001\u0000\u0000\u0000\u012a\u010c\u0001"+
		"\u0000\u0000\u0000\u012a\u010f\u0001\u0000\u0000\u0000\u012a\u0112\u0001"+
		"\u0000\u0000\u0000\u012a\u0115\u0001\u0000\u0000\u0000\u012a\u0118\u0001"+
		"\u0000\u0000\u0000\u012a\u011c\u0001\u0000\u0000\u0000\u012a\u011f\u0001"+
		"\u0000\u0000\u0000\u012a\u0128\u0001\u0000\u0000\u0000\u012b\u012e\u0001"+
		"\u0000\u0000\u0000\u012c\u012a\u0001\u0000\u0000\u0000\u012c\u012d\u0001"+
		"\u0000\u0000\u0000\u012d/\u0001\u0000\u0000\u0000\u012e\u012c\u0001\u0000"+
		"\u0000\u0000\u012f\u0132\u0005;\u0000\u0000\u0130\u0131\u0005\u0018\u0000"+
		"\u0000\u0131\u0133\u0003.\u0017\u0000\u0132\u0130\u0001\u0000\u0000\u0000"+
		"\u0132\u0133\u0001\u0000\u0000\u0000\u01331\u0001\u0000\u0000\u0000\u0134"+
		"\u0135\u0003\f\u0006\u0000\u0135\u013a\u00030\u0018\u0000\u0136\u0137"+
		"\u0005\u0002\u0000\u0000\u0137\u0139\u00030\u0018\u0000\u0138\u0136\u0001"+
		"\u0000\u0000\u0000\u0139\u013c\u0001\u0000\u0000\u0000\u013a\u0138\u0001"+
		"\u0000\u0000\u0000\u013a\u013b\u0001\u0000\u0000\u0000\u013b\u013d\u0001"+
		"\u0000\u0000\u0000\u013c\u013a\u0001\u0000\u0000\u0000\u013d\u013e\u0005"+
		"\u0001\u0000\u0000\u013e3\u0001\u0000\u0000\u0000\u013f\u0141\u00054\u0000"+
		"\u0000\u0140\u0142\u0003.\u0017\u0000\u0141\u0140\u0001\u0000\u0000\u0000"+
		"\u0141\u0142\u0001\u0000\u0000\u0000\u0142\u0146\u0001\u0000\u0000\u0000"+
		"\u0143\u0146\u00052\u0000\u0000\u0144\u0146\u00053\u0000\u0000\u0145\u013f"+
		"\u0001\u0000\u0000\u0000\u0145\u0143\u0001\u0000\u0000\u0000\u0145\u0144"+
		"\u0001\u0000\u0000\u0000\u0146\u0147\u0001\u0000\u0000\u0000\u0147\u0148"+
		"\u0005\u0001\u0000\u0000\u01485\u0001\u0000\u0000\u0000\u0149\u014c\u0003"+
		"*\u0015\u0000\u014a\u014c\u00034\u001a\u0000\u014b\u0149\u0001\u0000\u0000"+
		"\u0000\u014b\u014a\u0001\u0000\u0000\u0000\u014c7\u0001\u0000\u0000\u0000"+
		"\u014d\u0150\u00036\u001b\u0000\u014e\u0150\u0003\u0004\u0002\u0000\u014f"+
		"\u014d\u0001\u0000\u0000\u0000\u014f\u014e\u0001\u0000\u0000\u0000\u0150"+
		"9\u0001\u0000\u0000\u0000\u0151\u0152\u0005.\u0000\u0000\u0152\u0153\u0005"+
		"\u001e\u0000\u0000\u0153\u0154\u0003.\u0017\u0000\u0154\u0155\u0005\u001f"+
		"\u0000\u0000\u0155\u0158\u00038\u001c\u0000\u0156\u0157\u0005/\u0000\u0000"+
		"\u0157\u0159\u00038\u001c\u0000\u0158\u0156\u0001\u0000\u0000\u0000\u0158"+
		"\u0159\u0001\u0000\u0000\u0000\u0159;\u0001\u0000\u0000\u0000\u015a\u015b"+
		"\u00051\u0000\u0000\u015b\u015c\u0005\u001e\u0000\u0000\u015c\u015d\u0003"+
		".\u0017\u0000\u015d\u015e\u0005\u001f\u0000\u0000\u015e\u015f\u00038\u001c"+
		"\u0000\u015f=\u0001\u0000\u0000\u0000\u0160\u0165\u00032\u0019\u0000\u0161"+
		"\u0162\u0003.\u0017\u0000\u0162\u0163\u0005\u0001\u0000\u0000\u0163\u0165"+
		"\u0001\u0000\u0000\u0000\u0164\u0160\u0001\u0000\u0000\u0000\u0164\u0161"+
		"\u0001\u0000\u0000\u0000\u0165\u0168\u0001\u0000\u0000\u0000\u0166\u0168"+
		"\u0005\u0001\u0000\u0000\u0167\u0164\u0001\u0000\u0000\u0000\u0167\u0166"+
		"\u0001\u0000\u0000\u0000\u0168?\u0001\u0000\u0000\u0000\u0169\u016b\u0003"+
		".\u0017\u0000\u016a\u0169\u0001\u0000\u0000\u0000\u016a\u016b\u0001\u0000"+
		"\u0000\u0000\u016b\u016c\u0001\u0000\u0000\u0000\u016c\u016d\u0005\u0001"+
		"\u0000\u0000\u016dA\u0001\u0000\u0000\u0000\u016e\u0170\u0003.\u0017\u0000"+
		"\u016f\u016e\u0001\u0000\u0000\u0000\u016f\u0170\u0001\u0000\u0000\u0000"+
		"\u0170C\u0001\u0000\u0000\u0000\u0171\u0172\u00050\u0000\u0000\u0172\u0173"+
		"\u0005\u001e\u0000\u0000\u0173\u0174\u0003>\u001f\u0000\u0174\u0175\u0003"+
		"@ \u0000\u0175\u0176\u0003B!\u0000\u0176\u0177\u0005\u001f\u0000\u0000"+
		"\u0177\u0178\u00038\u001c\u0000\u0178E\u0001\u0000\u0000\u0000%MOVaej"+
		"vx\u0084\u008d\u0093\u0095\u00a8\u00ab\u00b5\u00b8\u00be\u00c7\u00cf\u00df"+
		"\u00e4\u00ea\u00f5\u0126\u012a\u012c\u0132\u013a\u0141\u0145\u014b\u014f"+
		"\u0158\u0164\u0167\u016a\u016f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
