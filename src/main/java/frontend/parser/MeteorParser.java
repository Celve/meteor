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
		LeftBracket=27, RightBracket=28, LeftParen=29, RightParen=30, LeftBrace=31, 
		RightBrace=32, LineComment=33, BlockComment=34, Void=35, Bool=36, Int=37, 
		String=38, New=39, Class=40, Null=41, True=42, False=43, This=44, If=45, 
		Else=46, For=47, While=48, Break=49, Continue=50, Return=51, IntegerLiteral=52, 
		StringLiteral=53, Escape=54, WhiteSpace=55, NewLine=56, Access=57, Id=58;
	public static final int
		RULE_prog = 0, RULE_progSuite = 1, RULE_block = 2, RULE_field = 3, RULE_decl = 4, 
		RULE_def = 5, RULE_varType = 6, RULE_classType = 7, RULE_primitiveType = 8, 
		RULE_voidType = 9, RULE_nonPrimitiveType = 10, RULE_classSuite = 11, RULE_classDef = 12, 
		RULE_classCtor = 13, RULE_returnType = 14, RULE_funcSuite = 15, RULE_funcDef = 16, 
		RULE_paramDecl = 17, RULE_paramDeclList = 18, RULE_paramInputList = 19, 
		RULE_lambdaDef = 20, RULE_basicExpr = 21, RULE_short = 22, RULE_prefixOps = 23, 
		RULE_bracketedExpr = 24, RULE_expr = 25, RULE_assignUnit = 26, RULE_varDecl = 27, 
		RULE_jump = 28, RULE_simpleSuite = 29, RULE_extendedBlock = 30, RULE_cond = 31, 
		RULE_while = 32, RULE_forInitUnit = 33, RULE_forCondUnit = 34, RULE_forStepUnit = 35, 
		RULE_for = 36;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "progSuite", "block", "field", "decl", "def", "varType", "classType", 
			"primitiveType", "voidType", "nonPrimitiveType", "classSuite", "classDef", 
			"classCtor", "returnType", "funcSuite", "funcDef", "paramDecl", "paramDeclList", 
			"paramInputList", "lambdaDef", "basicExpr", "short", "prefixOps", "bracketedExpr", 
			"expr", "assignUnit", "varDecl", "jump", "simpleSuite", "extendedBlock", 
			"cond", "while", "forInitUnit", "forCondUnit", "forStepUnit", "for"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "','", "'->'", "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", 
			"'<'", "'>='", "'<='", "'=='", "'!='", "'&&'", "'||'", "'!'", "'>>'", 
			"'<<'", "'&'", "'|'", "'^'", "'~'", "'='", "'++'", "'--'", "'['", "']'", 
			"'('", "')'", "'{'", "'}'", null, null, "'void'", "'bool'", "'int'", 
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
			"BitwiseXor", "BitwiseNot", "Assign", "Increment", "Decrement", "LeftBracket", 
			"RightBracket", "LeftParen", "RightParen", "LeftBrace", "RightBrace", 
			"LineComment", "BlockComment", "Void", "Bool", "Int", "String", "New", 
			"Class", "Null", "True", "False", "This", "If", "Else", "For", "While", 
			"Break", "Continue", "Return", "IntegerLiteral", "StringLiteral", "Escape", 
			"WhiteSpace", "NewLine", "Access", "Id"
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
		public ProgSuiteContext progSuite() {
			return getRuleContext(ProgSuiteContext.class,0);
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
			setState(74);
			progSuite();
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
	public static class ProgSuiteContext extends ParserRuleContext {
		public List<ShortContext> short_() {
			return getRuleContexts(ShortContext.class);
		}
		public ShortContext short_(int i) {
			return getRuleContext(ShortContext.class,i);
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
		public ProgSuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_progSuite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterProgSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitProgSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitProgSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgSuiteContext progSuite() throws RecognitionException {
		ProgSuiteContext _localctx = new ProgSuiteContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_progSuite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 306174374485033010L) != 0) {
				{
				setState(81);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(76);
					short_();
					}
					break;
				case 2:
					{
					setState(77);
					def();
					}
					break;
				case 3:
					{
					setState(78);
					block();
					}
					break;
				case 4:
					{
					setState(79);
					decl();
					}
					break;
				case 5:
					{
					setState(80);
					jump();
					}
					break;
				}
				}
				setState(85);
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
			setState(90);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case For:
				enterOuterAlt(_localctx, 1);
				{
				setState(86);
				for_();
				}
				break;
			case While:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				while_();
				}
				break;
			case If:
				enterOuterAlt(_localctx, 3);
				{
				setState(88);
				cond();
				}
				break;
			case LeftBrace:
				enterOuterAlt(_localctx, 4);
				{
				setState(89);
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
			setState(92);
			match(LeftBrace);
			setState(93);
			funcSuite();
			setState(94);
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
			setState(96);
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
			setState(100);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Class:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
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
				setState(99);
				funcDef();
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
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public List<TerminalNode> LeftBracket() { return getTokens(MeteorParser.LeftBracket); }
		public TerminalNode LeftBracket(int i) {
			return getToken(MeteorParser.LeftBracket, i);
		}
		public List<TerminalNode> RightBracket() { return getTokens(MeteorParser.RightBracket); }
		public TerminalNode RightBracket(int i) {
			return getToken(MeteorParser.RightBracket, i);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			classType();
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LeftBracket) {
				{
				{
				setState(103);
				match(LeftBracket);
				setState(104);
				match(RightBracket);
				}
				}
				setState(109);
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
	public static class ClassTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public NonPrimitiveTypeContext nonPrimitiveType() {
			return getRuleContext(NonPrimitiveTypeContext.class,0);
		}
		public ClassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterClassType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitClassType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitClassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeContext classType() throws RecognitionException {
		ClassTypeContext _localctx = new ClassTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_classType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
				{
				setState(110);
				primitiveType();
				}
				break;
			case Id:
				{
				setState(111);
				nonPrimitiveType();
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		enterRule(_localctx, 16, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 481036337152L) != 0) ) {
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
		enterRule(_localctx, 18, RULE_voidType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
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
		enterRule(_localctx, 20, RULE_nonPrimitiveType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
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
		enterRule(_localctx, 22, RULE_classSuite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 288230891547787264L) != 0) {
				{
				setState(123);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(120);
					decl();
					}
					break;
				case 2:
					{
					setState(121);
					funcDef();
					}
					break;
				case 3:
					{
					setState(122);
					classCtor();
					}
					break;
				}
				}
				setState(127);
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
		enterRule(_localctx, 24, RULE_classDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(Class);
			setState(129);
			((ClassDefContext)_localctx).className = match(Id);
			setState(130);
			match(LeftBrace);
			setState(131);
			classSuite();
			setState(132);
			match(RightBrace);
			setState(133);
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
		enterRule(_localctx, 26, RULE_classCtor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			((ClassCtorContext)_localctx).classId = match(Id);
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LeftParen) {
				{
				setState(136);
				paramDeclList();
				}
			}

			setState(139);
			match(LeftBrace);
			setState(140);
			funcSuite();
			setState(141);
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
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
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
		enterRule(_localctx, 28, RULE_returnType);
		try {
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
			case Id:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				varType();
				}
				break;
			case Void:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
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
		public List<ShortContext> short_() {
			return getRuleContexts(ShortContext.class);
		}
		public ShortContext short_(int i) {
			return getRuleContext(ShortContext.class,i);
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
		enterRule(_localctx, 30, RULE_funcSuite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 306173240613666866L) != 0) {
				{
				setState(151);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(147);
					short_();
					}
					break;
				case 2:
					{
					setState(148);
					block();
					}
					break;
				case 3:
					{
					setState(149);
					decl();
					}
					break;
				case 4:
					{
					setState(150);
					jump();
					}
					break;
				}
				}
				setState(155);
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
		enterRule(_localctx, 32, RULE_funcDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			returnType();
			setState(157);
			((FuncDefContext)_localctx).funcName = match(Id);
			setState(158);
			paramDeclList();
			setState(159);
			match(LeftBrace);
			setState(160);
			funcSuite();
			setState(161);
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
		enterRule(_localctx, 34, RULE_paramDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			varType();
			setState(164);
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
		enterRule(_localctx, 36, RULE_paramDeclList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(LeftParen);
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 288230857188048896L) != 0) {
				{
				setState(167);
				paramDecl();
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(168);
					match(T__1);
					setState(169);
					paramDecl();
					}
					}
					setState(174);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(177);
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
		enterRule(_localctx, 38, RULE_paramInputList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(LeftParen);
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 301774710918742064L) != 0) {
				{
				setState(180);
				expr(0);
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(181);
					match(T__1);
					setState(182);
					expr(0);
					}
					}
					setState(187);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(190);
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
		public ParamDeclListContext paramDeclList() {
			return getRuleContext(ParamDeclListContext.class,0);
		}
		public TerminalNode LeftBrace() { return getToken(MeteorParser.LeftBrace, 0); }
		public FuncSuiteContext funcSuite() {
			return getRuleContext(FuncSuiteContext.class,0);
		}
		public TerminalNode RightBrace() { return getToken(MeteorParser.RightBrace, 0); }
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
		enterRule(_localctx, 40, RULE_lambdaDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(LeftBracket);
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitwiseAnd) {
				{
				setState(193);
				((LambdaDefContext)_localctx).op = match(BitwiseAnd);
				}
			}

			setState(196);
			match(RightBracket);
			setState(197);
			paramDeclList();
			setState(198);
			match(T__2);
			setState(199);
			match(LeftBrace);
			setState(200);
			funcSuite();
			setState(201);
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
		enterRule(_localctx, 42, RULE_basicExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 301774160382656512L) != 0) ) {
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
	public static class ShortContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ShortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_short; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterShort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitShort(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitShort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShortContext short_() throws RecognitionException {
		ShortContext _localctx = new ShortContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_short);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 301774710918742064L) != 0) {
				{
				setState(205);
				expr(0);
				}
			}

			setState(208);
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
		enterRule(_localctx, 46, RULE_prefixOps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
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
	public static class BracketedExprContext extends ParserRuleContext {
		public TerminalNode LeftBracket() { return getToken(MeteorParser.LeftBracket, 0); }
		public TerminalNode RightBracket() { return getToken(MeteorParser.RightBracket, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BracketedExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracketedExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).enterBracketedExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MeteorListener ) ((MeteorListener)listener).exitBracketedExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MeteorVisitor ) return ((MeteorVisitor<? extends T>)visitor).visitBracketedExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracketedExprContext bracketedExpr() throws RecognitionException {
		BracketedExprContext _localctx = new BracketedExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_bracketedExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(LeftBracket);
			setState(214);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 301774710918742064L) != 0) {
				{
				setState(213);
				expr(0);
				}
			}

			setState(216);
			match(RightBracket);
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
		public TerminalNode LeftBracket() { return getToken(MeteorParser.LeftBracket, 0); }
		public TerminalNode RightBracket() { return getToken(MeteorParser.RightBracket, 0); }
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
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public List<BracketedExprContext> bracketedExpr() {
			return getRuleContexts(BracketedExprContext.class);
		}
		public BracketedExprContext bracketedExpr(int i) {
			return getRuleContext(BracketedExprContext.class,i);
		}
		public TerminalNode LeftParen() { return getToken(MeteorParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MeteorParser.RightParen, 0); }
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
		int _startState = 50;
		enterRecursionRule(_localctx, 50, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				_localctx = new PriorExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(219);
				match(LeftParen);
				setState(220);
				expr(0);
				setState(221);
				match(RightParen);
				}
				break;
			case 2:
				{
				_localctx = new AtomContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(223);
				basicExpr();
				}
				break;
			case 3:
				{
				_localctx = new InitExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(224);
				match(New);
				setState(225);
				classType();
				setState(229);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(226);
						bracketedExpr();
						}
						} 
					}
					setState(231);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				}
				setState(234);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(232);
					match(LeftParen);
					setState(233);
					match(RightParen);
					}
					break;
				}
				}
				break;
			case 4:
				{
				_localctx = new LambdaCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				lambdaDef();
				setState(237);
				paramInputList();
				}
				break;
			case 5:
				{
				_localctx = new FuncCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				{
				setState(239);
				((FuncCallContext)_localctx).funcName = match(Id);
				}
				setState(240);
				paramInputList();
				}
				break;
			case 6:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(241);
				prefixOps();
				setState(242);
				expr(12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(295);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(293);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(246);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(247);
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
						setState(248);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(249);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(250);
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
						setState(251);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(252);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(253);
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
						setState(254);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(255);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(256);
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
						setState(257);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(258);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(259);
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
						setState(260);
						expr(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(261);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(262);
						((BinaryExprContext)_localctx).op = match(BitwiseAnd);
						setState(263);
						expr(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(264);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(265);
						((BinaryExprContext)_localctx).op = match(BitwiseXor);
						setState(266);
						expr(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(267);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(268);
						((BinaryExprContext)_localctx).op = match(BitwiseOr);
						setState(269);
						expr(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(270);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(271);
						((BinaryExprContext)_localctx).op = match(LogicalAnd);
						setState(272);
						expr(4);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(273);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(274);
						((BinaryExprContext)_localctx).op = match(LogicalOr);
						setState(275);
						expr(3);
						}
						break;
					case 11:
						{
						_localctx = new AssignExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(276);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(277);
						match(Assign);
						setState(278);
						expr(1);
						}
						break;
					case 12:
						{
						_localctx = new MethodAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(279);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(280);
						match(Access);
						{
						setState(281);
						((MethodAccessContext)_localctx).methodName = match(Id);
						}
						setState(282);
						paramInputList();
						}
						break;
					case 13:
						{
						_localctx = new MemberAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(283);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(284);
						match(Access);
						{
						setState(285);
						((MemberAccessContext)_localctx).classMember = match(Id);
						}
						}
						break;
					case 14:
						{
						_localctx = new ArrayAccessContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(286);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(287);
						match(LeftBracket);
						setState(288);
						expr(0);
						setState(289);
						match(RightBracket);
						}
						break;
					case 15:
						{
						_localctx = new SuffixExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(291);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(292);
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
				setState(297);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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
		enterRule(_localctx, 52, RULE_assignUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			match(Id);
			setState(301);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(299);
				match(Assign);
				setState(300);
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
		enterRule(_localctx, 54, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			varType();
			setState(304);
			assignUnit();
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(305);
				match(T__1);
				setState(306);
				assignUnit();
				}
				}
				setState(311);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(312);
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
		enterRule(_localctx, 56, RULE_jump);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Return:
				{
				setState(314);
				((JumpContext)_localctx).op = match(Return);
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 301774710918742064L) != 0) {
					{
					setState(315);
					expr(0);
					}
				}

				}
				break;
			case Break:
				{
				setState(318);
				((JumpContext)_localctx).op = match(Break);
				}
				break;
			case Continue:
				{
				setState(319);
				((JumpContext)_localctx).op = match(Continue);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(322);
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
		public ShortContext short_() {
			return getRuleContext(ShortContext.class,0);
		}
		public JumpContext jump() {
			return getRuleContext(JumpContext.class,0);
		}
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
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
		enterRule(_localctx, 58, RULE_simpleSuite);
		try {
			setState(327);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(324);
				short_();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(325);
				jump();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(326);
				decl();
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
		enterRule(_localctx, 60, RULE_extendedBlock);
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
			case Bool:
			case Int:
			case String:
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
				setState(329);
				simpleSuite();
				}
				break;
			case LeftBrace:
			case If:
			case For:
			case While:
				enterOuterAlt(_localctx, 2);
				{
				setState(330);
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
		enterRule(_localctx, 62, RULE_cond);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			match(If);
			setState(334);
			match(LeftParen);
			setState(335);
			expr(0);
			setState(336);
			match(RightParen);
			setState(337);
			extendedBlock();
			setState(340);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(338);
				match(Else);
				setState(339);
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
		enterRule(_localctx, 64, RULE_while);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			match(While);
			setState(343);
			match(LeftParen);
			setState(344);
			expr(0);
			setState(345);
			match(RightParen);
			setState(346);
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
		enterRule(_localctx, 66, RULE_forInitUnit);
		try {
			setState(355);
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
				setState(352);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(348);
					varDecl();
					}
					break;
				case 2:
					{
					setState(349);
					expr(0);
					setState(350);
					match(T__0);
					}
					break;
				}
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(354);
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
		enterRule(_localctx, 68, RULE_forCondUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 301774710918742064L) != 0) {
				{
				setState(357);
				expr(0);
				}
			}

			setState(360);
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
		enterRule(_localctx, 70, RULE_forStepUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 301774710918742064L) != 0) {
				{
				setState(362);
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
		enterRule(_localctx, 72, RULE_for);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			match(For);
			setState(366);
			match(LeftParen);
			setState(367);
			forInitUnit();
			setState(368);
			forCondUnit();
			setState(369);
			forStepUnit();
			setState(370);
			match(RightParen);
			setState(371);
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
		case 25:
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
		"\u0004\u0001:\u0176\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001R\b\u0001\n\u0001\f\u0001"+
		"U\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"[\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0003\u0005e\b\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0005\u0006j\b\u0006\n\u0006\f\u0006m\t\u0006"+
		"\u0001\u0007\u0001\u0007\u0003\u0007q\b\u0007\u0001\b\u0001\b\u0001\t"+
		"\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b"+
		"|\b\u000b\n\u000b\f\u000b\u007f\t\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0003\r\u008a\b\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0003\u000e\u0092\b\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u0098\b\u000f\n"+
		"\u000f\f\u000f\u009b\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u00ab"+
		"\b\u0012\n\u0012\f\u0012\u00ae\t\u0012\u0003\u0012\u00b0\b\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005"+
		"\u0013\u00b8\b\u0013\n\u0013\f\u0013\u00bb\t\u0013\u0003\u0013\u00bd\b"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0003\u0014\u00c3"+
		"\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0003\u0016\u00cf"+
		"\b\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0003\u0018\u00d7\b\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0005\u0019\u00e4\b\u0019\n\u0019\f\u0019\u00e7\t\u0019"+
		"\u0001\u0019\u0001\u0019\u0003\u0019\u00eb\b\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0003\u0019\u00f5\b\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0005\u0019\u0126\b\u0019\n\u0019\f\u0019\u0129\t\u0019\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u012e\b\u001a\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0005\u001b\u0134\b\u001b\n\u001b\f\u001b"+
		"\u0137\t\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0003\u001c"+
		"\u013d\b\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u0141\b\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u0148"+
		"\b\u001d\u0001\u001e\u0001\u001e\u0003\u001e\u014c\b\u001e\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0003\u001f\u0155\b\u001f\u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0001"+
		"!\u0001!\u0001!\u0001!\u0003!\u0161\b!\u0001!\u0003!\u0164\b!\u0001\""+
		"\u0003\"\u0167\b\"\u0001\"\u0001\"\u0001#\u0003#\u016c\b#\u0001$\u0001"+
		"$\u0001$\u0001$\u0001$\u0001$\u0001$\u0001$\u0001$\u0000\u00012%\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(*,.02468:<>@BDFH\u0000\t\u0001\u0000$&\u0003\u0000),45::\u0004"+
		"\u0000\u0004\u0005\u0011\u0011\u0017\u0017\u0019\u001a\u0001\u0000\u0006"+
		"\b\u0001\u0000\u0004\u0005\u0001\u0000\u0012\u0013\u0001\u0000\t\f\u0001"+
		"\u0000\r\u000e\u0001\u0000\u0019\u001a\u018e\u0000J\u0001\u0000\u0000"+
		"\u0000\u0002S\u0001\u0000\u0000\u0000\u0004Z\u0001\u0000\u0000\u0000\u0006"+
		"\\\u0001\u0000\u0000\u0000\b`\u0001\u0000\u0000\u0000\nd\u0001\u0000\u0000"+
		"\u0000\ff\u0001\u0000\u0000\u0000\u000ep\u0001\u0000\u0000\u0000\u0010"+
		"r\u0001\u0000\u0000\u0000\u0012t\u0001\u0000\u0000\u0000\u0014v\u0001"+
		"\u0000\u0000\u0000\u0016}\u0001\u0000\u0000\u0000\u0018\u0080\u0001\u0000"+
		"\u0000\u0000\u001a\u0087\u0001\u0000\u0000\u0000\u001c\u0091\u0001\u0000"+
		"\u0000\u0000\u001e\u0099\u0001\u0000\u0000\u0000 \u009c\u0001\u0000\u0000"+
		"\u0000\"\u00a3\u0001\u0000\u0000\u0000$\u00a6\u0001\u0000\u0000\u0000"+
		"&\u00b3\u0001\u0000\u0000\u0000(\u00c0\u0001\u0000\u0000\u0000*\u00cb"+
		"\u0001\u0000\u0000\u0000,\u00ce\u0001\u0000\u0000\u0000.\u00d2\u0001\u0000"+
		"\u0000\u00000\u00d4\u0001\u0000\u0000\u00002\u00f4\u0001\u0000\u0000\u0000"+
		"4\u012a\u0001\u0000\u0000\u00006\u012f\u0001\u0000\u0000\u00008\u0140"+
		"\u0001\u0000\u0000\u0000:\u0147\u0001\u0000\u0000\u0000<\u014b\u0001\u0000"+
		"\u0000\u0000>\u014d\u0001\u0000\u0000\u0000@\u0156\u0001\u0000\u0000\u0000"+
		"B\u0163\u0001\u0000\u0000\u0000D\u0166\u0001\u0000\u0000\u0000F\u016b"+
		"\u0001\u0000\u0000\u0000H\u016d\u0001\u0000\u0000\u0000JK\u0003\u0002"+
		"\u0001\u0000K\u0001\u0001\u0000\u0000\u0000LR\u0003,\u0016\u0000MR\u0003"+
		"\n\u0005\u0000NR\u0003\u0004\u0002\u0000OR\u0003\b\u0004\u0000PR\u0003"+
		"8\u001c\u0000QL\u0001\u0000\u0000\u0000QM\u0001\u0000\u0000\u0000QN\u0001"+
		"\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000QP\u0001\u0000\u0000\u0000"+
		"RU\u0001\u0000\u0000\u0000SQ\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000"+
		"\u0000T\u0003\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000V[\u0003"+
		"H$\u0000W[\u0003@ \u0000X[\u0003>\u001f\u0000Y[\u0003\u0006\u0003\u0000"+
		"ZV\u0001\u0000\u0000\u0000ZW\u0001\u0000\u0000\u0000ZX\u0001\u0000\u0000"+
		"\u0000ZY\u0001\u0000\u0000\u0000[\u0005\u0001\u0000\u0000\u0000\\]\u0005"+
		"\u001f\u0000\u0000]^\u0003\u001e\u000f\u0000^_\u0005 \u0000\u0000_\u0007"+
		"\u0001\u0000\u0000\u0000`a\u00036\u001b\u0000a\t\u0001\u0000\u0000\u0000"+
		"be\u0003\u0018\f\u0000ce\u0003 \u0010\u0000db\u0001\u0000\u0000\u0000"+
		"dc\u0001\u0000\u0000\u0000e\u000b\u0001\u0000\u0000\u0000fk\u0003\u000e"+
		"\u0007\u0000gh\u0005\u001b\u0000\u0000hj\u0005\u001c\u0000\u0000ig\u0001"+
		"\u0000\u0000\u0000jm\u0001\u0000\u0000\u0000ki\u0001\u0000\u0000\u0000"+
		"kl\u0001\u0000\u0000\u0000l\r\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000"+
		"\u0000nq\u0003\u0010\b\u0000oq\u0003\u0014\n\u0000pn\u0001\u0000\u0000"+
		"\u0000po\u0001\u0000\u0000\u0000q\u000f\u0001\u0000\u0000\u0000rs\u0007"+
		"\u0000\u0000\u0000s\u0011\u0001\u0000\u0000\u0000tu\u0005#\u0000\u0000"+
		"u\u0013\u0001\u0000\u0000\u0000vw\u0005:\u0000\u0000w\u0015\u0001\u0000"+
		"\u0000\u0000x|\u0003\b\u0004\u0000y|\u0003 \u0010\u0000z|\u0003\u001a"+
		"\r\u0000{x\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000{z\u0001\u0000"+
		"\u0000\u0000|\u007f\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000\u0000"+
		"}~\u0001\u0000\u0000\u0000~\u0017\u0001\u0000\u0000\u0000\u007f}\u0001"+
		"\u0000\u0000\u0000\u0080\u0081\u0005(\u0000\u0000\u0081\u0082\u0005:\u0000"+
		"\u0000\u0082\u0083\u0005\u001f\u0000\u0000\u0083\u0084\u0003\u0016\u000b"+
		"\u0000\u0084\u0085\u0005 \u0000\u0000\u0085\u0086\u0005\u0001\u0000\u0000"+
		"\u0086\u0019\u0001\u0000\u0000\u0000\u0087\u0089\u0005:\u0000\u0000\u0088"+
		"\u008a\u0003$\u0012\u0000\u0089\u0088\u0001\u0000\u0000\u0000\u0089\u008a"+
		"\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u008c"+
		"\u0005\u001f\u0000\u0000\u008c\u008d\u0003\u001e\u000f\u0000\u008d\u008e"+
		"\u0005 \u0000\u0000\u008e\u001b\u0001\u0000\u0000\u0000\u008f\u0092\u0003"+
		"\f\u0006\u0000\u0090\u0092\u0003\u0012\t\u0000\u0091\u008f\u0001\u0000"+
		"\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0092\u001d\u0001\u0000"+
		"\u0000\u0000\u0093\u0098\u0003,\u0016\u0000\u0094\u0098\u0003\u0004\u0002"+
		"\u0000\u0095\u0098\u0003\b\u0004\u0000\u0096\u0098\u00038\u001c\u0000"+
		"\u0097\u0093\u0001\u0000\u0000\u0000\u0097\u0094\u0001\u0000\u0000\u0000"+
		"\u0097\u0095\u0001\u0000\u0000\u0000\u0097\u0096\u0001\u0000\u0000\u0000"+
		"\u0098\u009b\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000"+
		"\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u001f\u0001\u0000\u0000\u0000"+
		"\u009b\u0099\u0001\u0000\u0000\u0000\u009c\u009d\u0003\u001c\u000e\u0000"+
		"\u009d\u009e\u0005:\u0000\u0000\u009e\u009f\u0003$\u0012\u0000\u009f\u00a0"+
		"\u0005\u001f\u0000\u0000\u00a0\u00a1\u0003\u001e\u000f\u0000\u00a1\u00a2"+
		"\u0005 \u0000\u0000\u00a2!\u0001\u0000\u0000\u0000\u00a3\u00a4\u0003\f"+
		"\u0006\u0000\u00a4\u00a5\u0005:\u0000\u0000\u00a5#\u0001\u0000\u0000\u0000"+
		"\u00a6\u00af\u0005\u001d\u0000\u0000\u00a7\u00ac\u0003\"\u0011\u0000\u00a8"+
		"\u00a9\u0005\u0002\u0000\u0000\u00a9\u00ab\u0003\"\u0011\u0000\u00aa\u00a8"+
		"\u0001\u0000\u0000\u0000\u00ab\u00ae\u0001\u0000\u0000\u0000\u00ac\u00aa"+
		"\u0001\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00b0"+
		"\u0001\u0000\u0000\u0000\u00ae\u00ac\u0001\u0000\u0000\u0000\u00af\u00a7"+
		"\u0001\u0000\u0000\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005\u001e\u0000\u0000\u00b2%\u0001"+
		"\u0000\u0000\u0000\u00b3\u00bc\u0005\u001d\u0000\u0000\u00b4\u00b9\u0003"+
		"2\u0019\u0000\u00b5\u00b6\u0005\u0002\u0000\u0000\u00b6\u00b8\u00032\u0019"+
		"\u0000\u00b7\u00b5\u0001\u0000\u0000\u0000\u00b8\u00bb\u0001\u0000\u0000"+
		"\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000"+
		"\u0000\u00ba\u00bd\u0001\u0000\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000"+
		"\u0000\u00bc\u00b4\u0001\u0000\u0000\u0000\u00bc\u00bd\u0001\u0000\u0000"+
		"\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u00bf\u0005\u001e\u0000"+
		"\u0000\u00bf\'\u0001\u0000\u0000\u0000\u00c0\u00c2\u0005\u001b\u0000\u0000"+
		"\u00c1\u00c3\u0005\u0014\u0000\u0000\u00c2\u00c1\u0001\u0000\u0000\u0000"+
		"\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c5\u0005\u001c\u0000\u0000\u00c5\u00c6\u0003$\u0012\u0000\u00c6"+
		"\u00c7\u0005\u0003\u0000\u0000\u00c7\u00c8\u0005\u001f\u0000\u0000\u00c8"+
		"\u00c9\u0003\u001e\u000f\u0000\u00c9\u00ca\u0005 \u0000\u0000\u00ca)\u0001"+
		"\u0000\u0000\u0000\u00cb\u00cc\u0007\u0001\u0000\u0000\u00cc+\u0001\u0000"+
		"\u0000\u0000\u00cd\u00cf\u00032\u0019\u0000\u00ce\u00cd\u0001\u0000\u0000"+
		"\u0000\u00ce\u00cf\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000\u0000"+
		"\u0000\u00d0\u00d1\u0005\u0001\u0000\u0000\u00d1-\u0001\u0000\u0000\u0000"+
		"\u00d2\u00d3\u0007\u0002\u0000\u0000\u00d3/\u0001\u0000\u0000\u0000\u00d4"+
		"\u00d6\u0005\u001b\u0000\u0000\u00d5\u00d7\u00032\u0019\u0000\u00d6\u00d5"+
		"\u0001\u0000\u0000\u0000\u00d6\u00d7\u0001\u0000\u0000\u0000\u00d7\u00d8"+
		"\u0001\u0000\u0000\u0000\u00d8\u00d9\u0005\u001c\u0000\u0000\u00d91\u0001"+
		"\u0000\u0000\u0000\u00da\u00db\u0006\u0019\uffff\uffff\u0000\u00db\u00dc"+
		"\u0005\u001d\u0000\u0000\u00dc\u00dd\u00032\u0019\u0000\u00dd\u00de\u0005"+
		"\u001e\u0000\u0000\u00de\u00f5\u0001\u0000\u0000\u0000\u00df\u00f5\u0003"+
		"*\u0015\u0000\u00e0\u00e1\u0005\'\u0000\u0000\u00e1\u00e5\u0003\u000e"+
		"\u0007\u0000\u00e2\u00e4\u00030\u0018\u0000\u00e3\u00e2\u0001\u0000\u0000"+
		"\u0000\u00e4\u00e7\u0001\u0000\u0000\u0000\u00e5\u00e3\u0001\u0000\u0000"+
		"\u0000\u00e5\u00e6\u0001\u0000\u0000\u0000\u00e6\u00ea\u0001\u0000\u0000"+
		"\u0000\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005\u001d\u0000"+
		"\u0000\u00e9\u00eb\u0005\u001e\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000"+
		"\u0000\u00ea\u00eb\u0001\u0000\u0000\u0000\u00eb\u00f5\u0001\u0000\u0000"+
		"\u0000\u00ec\u00ed\u0003(\u0014\u0000\u00ed\u00ee\u0003&\u0013\u0000\u00ee"+
		"\u00f5\u0001\u0000\u0000\u0000\u00ef\u00f0\u0005:\u0000\u0000\u00f0\u00f5"+
		"\u0003&\u0013\u0000\u00f1\u00f2\u0003.\u0017\u0000\u00f2\u00f3\u00032"+
		"\u0019\f\u00f3\u00f5\u0001\u0000\u0000\u0000\u00f4\u00da\u0001\u0000\u0000"+
		"\u0000\u00f4\u00df\u0001\u0000\u0000\u0000\u00f4\u00e0\u0001\u0000\u0000"+
		"\u0000\u00f4\u00ec\u0001\u0000\u0000\u0000\u00f4\u00ef\u0001\u0000\u0000"+
		"\u0000\u00f4\u00f1\u0001\u0000\u0000\u0000\u00f5\u0127\u0001\u0000\u0000"+
		"\u0000\u00f6\u00f7\n\u000b\u0000\u0000\u00f7\u00f8\u0007\u0003\u0000\u0000"+
		"\u00f8\u0126\u00032\u0019\f\u00f9\u00fa\n\n\u0000\u0000\u00fa\u00fb\u0007"+
		"\u0004\u0000\u0000\u00fb\u0126\u00032\u0019\u000b\u00fc\u00fd\n\t\u0000"+
		"\u0000\u00fd\u00fe\u0007\u0005\u0000\u0000\u00fe\u0126\u00032\u0019\n"+
		"\u00ff\u0100\n\b\u0000\u0000\u0100\u0101\u0007\u0006\u0000\u0000\u0101"+
		"\u0126\u00032\u0019\t\u0102\u0103\n\u0007\u0000\u0000\u0103\u0104\u0007"+
		"\u0007\u0000\u0000\u0104\u0126\u00032\u0019\b\u0105\u0106\n\u0006\u0000"+
		"\u0000\u0106\u0107\u0005\u0014\u0000\u0000\u0107\u0126\u00032\u0019\u0007"+
		"\u0108\u0109\n\u0005\u0000\u0000\u0109\u010a\u0005\u0016\u0000\u0000\u010a"+
		"\u0126\u00032\u0019\u0006\u010b\u010c\n\u0004\u0000\u0000\u010c\u010d"+
		"\u0005\u0015\u0000\u0000\u010d\u0126\u00032\u0019\u0005\u010e\u010f\n"+
		"\u0003\u0000\u0000\u010f\u0110\u0005\u000f\u0000\u0000\u0110\u0126\u0003"+
		"2\u0019\u0004\u0111\u0112\n\u0002\u0000\u0000\u0112\u0113\u0005\u0010"+
		"\u0000\u0000\u0113\u0126\u00032\u0019\u0003\u0114\u0115\n\u0001\u0000"+
		"\u0000\u0115\u0116\u0005\u0018\u0000\u0000\u0116\u0126\u00032\u0019\u0001"+
		"\u0117\u0118\n\u0010\u0000\u0000\u0118\u0119\u00059\u0000\u0000\u0119"+
		"\u011a\u0005:\u0000\u0000\u011a\u0126\u0003&\u0013\u0000\u011b\u011c\n"+
		"\u000f\u0000\u0000\u011c\u011d\u00059\u0000\u0000\u011d\u0126\u0005:\u0000"+
		"\u0000\u011e\u011f\n\u000e\u0000\u0000\u011f\u0120\u0005\u001b\u0000\u0000"+
		"\u0120\u0121\u00032\u0019\u0000\u0121\u0122\u0005\u001c\u0000\u0000\u0122"+
		"\u0126\u0001\u0000\u0000\u0000\u0123\u0124\n\r\u0000\u0000\u0124\u0126"+
		"\u0007\b\u0000\u0000\u0125\u00f6\u0001\u0000\u0000\u0000\u0125\u00f9\u0001"+
		"\u0000\u0000\u0000\u0125\u00fc\u0001\u0000\u0000\u0000\u0125\u00ff\u0001"+
		"\u0000\u0000\u0000\u0125\u0102\u0001\u0000\u0000\u0000\u0125\u0105\u0001"+
		"\u0000\u0000\u0000\u0125\u0108\u0001\u0000\u0000\u0000\u0125\u010b\u0001"+
		"\u0000\u0000\u0000\u0125\u010e\u0001\u0000\u0000\u0000\u0125\u0111\u0001"+
		"\u0000\u0000\u0000\u0125\u0114\u0001\u0000\u0000\u0000\u0125\u0117\u0001"+
		"\u0000\u0000\u0000\u0125\u011b\u0001\u0000\u0000\u0000\u0125\u011e\u0001"+
		"\u0000\u0000\u0000\u0125\u0123\u0001\u0000\u0000\u0000\u0126\u0129\u0001"+
		"\u0000\u0000\u0000\u0127\u0125\u0001\u0000\u0000\u0000\u0127\u0128\u0001"+
		"\u0000\u0000\u0000\u01283\u0001\u0000\u0000\u0000\u0129\u0127\u0001\u0000"+
		"\u0000\u0000\u012a\u012d\u0005:\u0000\u0000\u012b\u012c\u0005\u0018\u0000"+
		"\u0000\u012c\u012e\u00032\u0019\u0000\u012d\u012b\u0001\u0000\u0000\u0000"+
		"\u012d\u012e\u0001\u0000\u0000\u0000\u012e5\u0001\u0000\u0000\u0000\u012f"+
		"\u0130\u0003\f\u0006\u0000\u0130\u0135\u00034\u001a\u0000\u0131\u0132"+
		"\u0005\u0002\u0000\u0000\u0132\u0134\u00034\u001a\u0000\u0133\u0131\u0001"+
		"\u0000\u0000\u0000\u0134\u0137\u0001\u0000\u0000\u0000\u0135\u0133\u0001"+
		"\u0000\u0000\u0000\u0135\u0136\u0001\u0000\u0000\u0000\u0136\u0138\u0001"+
		"\u0000\u0000\u0000\u0137\u0135\u0001\u0000\u0000\u0000\u0138\u0139\u0005"+
		"\u0001\u0000\u0000\u01397\u0001\u0000\u0000\u0000\u013a\u013c\u00053\u0000"+
		"\u0000\u013b\u013d\u00032\u0019\u0000\u013c\u013b\u0001\u0000\u0000\u0000"+
		"\u013c\u013d\u0001\u0000\u0000\u0000\u013d\u0141\u0001\u0000\u0000\u0000"+
		"\u013e\u0141\u00051\u0000\u0000\u013f\u0141\u00052\u0000\u0000\u0140\u013a"+
		"\u0001\u0000\u0000\u0000\u0140\u013e\u0001\u0000\u0000\u0000\u0140\u013f"+
		"\u0001\u0000\u0000\u0000\u0141\u0142\u0001\u0000\u0000\u0000\u0142\u0143"+
		"\u0005\u0001\u0000\u0000\u01439\u0001\u0000\u0000\u0000\u0144\u0148\u0003"+
		",\u0016\u0000\u0145\u0148\u00038\u001c\u0000\u0146\u0148\u0003\b\u0004"+
		"\u0000\u0147\u0144\u0001\u0000\u0000\u0000\u0147\u0145\u0001\u0000\u0000"+
		"\u0000\u0147\u0146\u0001\u0000\u0000\u0000\u0148;\u0001\u0000\u0000\u0000"+
		"\u0149\u014c\u0003:\u001d\u0000\u014a\u014c\u0003\u0004\u0002\u0000\u014b"+
		"\u0149\u0001\u0000\u0000\u0000\u014b\u014a\u0001\u0000\u0000\u0000\u014c"+
		"=\u0001\u0000\u0000\u0000\u014d\u014e\u0005-\u0000\u0000\u014e\u014f\u0005"+
		"\u001d\u0000\u0000\u014f\u0150\u00032\u0019\u0000\u0150\u0151\u0005\u001e"+
		"\u0000\u0000\u0151\u0154\u0003<\u001e\u0000\u0152\u0153\u0005.\u0000\u0000"+
		"\u0153\u0155\u0003<\u001e\u0000\u0154\u0152\u0001\u0000\u0000\u0000\u0154"+
		"\u0155\u0001\u0000\u0000\u0000\u0155?\u0001\u0000\u0000\u0000\u0156\u0157"+
		"\u00050\u0000\u0000\u0157\u0158\u0005\u001d\u0000\u0000\u0158\u0159\u0003"+
		"2\u0019\u0000\u0159\u015a\u0005\u001e\u0000\u0000\u015a\u015b\u0003<\u001e"+
		"\u0000\u015bA\u0001\u0000\u0000\u0000\u015c\u0161\u00036\u001b\u0000\u015d"+
		"\u015e\u00032\u0019\u0000\u015e\u015f\u0005\u0001\u0000\u0000\u015f\u0161"+
		"\u0001\u0000\u0000\u0000\u0160\u015c\u0001\u0000\u0000\u0000\u0160\u015d"+
		"\u0001\u0000\u0000\u0000\u0161\u0164\u0001\u0000\u0000\u0000\u0162\u0164"+
		"\u0005\u0001\u0000\u0000\u0163\u0160\u0001\u0000\u0000\u0000\u0163\u0162"+
		"\u0001\u0000\u0000\u0000\u0164C\u0001\u0000\u0000\u0000\u0165\u0167\u0003"+
		"2\u0019\u0000\u0166\u0165\u0001\u0000\u0000\u0000\u0166\u0167\u0001\u0000"+
		"\u0000\u0000\u0167\u0168\u0001\u0000\u0000\u0000\u0168\u0169\u0005\u0001"+
		"\u0000\u0000\u0169E\u0001\u0000\u0000\u0000\u016a\u016c\u00032\u0019\u0000"+
		"\u016b\u016a\u0001\u0000\u0000\u0000\u016b\u016c\u0001\u0000\u0000\u0000"+
		"\u016cG\u0001\u0000\u0000\u0000\u016d\u016e\u0005/\u0000\u0000\u016e\u016f"+
		"\u0005\u001d\u0000\u0000\u016f\u0170\u0003B!\u0000\u0170\u0171\u0003D"+
		"\"\u0000\u0171\u0172\u0003F#\u0000\u0172\u0173\u0005\u001e\u0000\u0000"+
		"\u0173\u0174\u0003<\u001e\u0000\u0174I\u0001\u0000\u0000\u0000#QSZdkp"+
		"{}\u0089\u0091\u0097\u0099\u00ac\u00af\u00b9\u00bc\u00c2\u00ce\u00d6\u00e5"+
		"\u00ea\u00f4\u0125\u0127\u012d\u0135\u013c\u0140\u0147\u014b\u0154\u0160"+
		"\u0163\u0166\u016b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
