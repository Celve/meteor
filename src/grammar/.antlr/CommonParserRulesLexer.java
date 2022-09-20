// Generated from /Users/celve/Repositories/Courses/University/meteor/src/grammar/CommonParserRules.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommonParserRulesLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "Add", "Sub", "Mul", "Div", "Mod", "Greater", 
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


	public CommonParserRulesLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CommonParserRules.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2=\u0174\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\3\2\3"+
		"\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n"+
		"\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\""+
		"\3#\3#\3#\3#\7#\u00cc\n#\f#\16#\u00cf\13#\3#\5#\u00d2\n#\3#\3#\3#\3#\3"+
		"$\3$\3$\3$\7$\u00dc\n$\f$\16$\u00df\13$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%"+
		"\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3*\3"+
		"*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3.\3.\3"+
		".\3.\3.\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66"+
		"\3\66\7\66\u0145\n\66\f\66\16\66\u0148\13\66\5\66\u014a\n\66\3\67\3\67"+
		"\3\67\7\67\u014f\n\67\f\67\16\67\u0152\13\67\3\67\3\67\38\38\38\38\38"+
		"\38\58\u015c\n8\39\69\u015f\n9\r9\169\u0160\39\39\3:\3:\3:\5:\u0168\n"+
		":\3:\3:\3;\3;\3<\3<\7<\u0170\n<\f<\16<\u0173\13<\5\u00cd\u00dd\u0150\2"+
		"=\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37"+
		"= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o"+
		"9q:s;u<w=\3\2\b\3\2\63;\3\2\62;\4\2\13\13\"\"\4\2\f\f\17\17\4\2C\\c|\6"+
		"\2\62;C\\aac|\2\u017f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3"+
		"\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2"+
		"\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2"+
		"w\3\2\2\2\3y\3\2\2\2\5{\3\2\2\2\7~\3\2\2\2\t\u0080\3\2\2\2\13\u0082\3"+
		"\2\2\2\r\u0084\3\2\2\2\17\u0086\3\2\2\2\21\u0088\3\2\2\2\23\u008a\3\2"+
		"\2\2\25\u008c\3\2\2\2\27\u008e\3\2\2\2\31\u0091\3\2\2\2\33\u0094\3\2\2"+
		"\2\35\u0097\3\2\2\2\37\u009a\3\2\2\2!\u009d\3\2\2\2#\u00a0\3\2\2\2%\u00a2"+
		"\3\2\2\2\'\u00a5\3\2\2\2)\u00a8\3\2\2\2+\u00aa\3\2\2\2-\u00ac\3\2\2\2"+
		"/\u00ae\3\2\2\2\61\u00b0\3\2\2\2\63\u00b2\3\2\2\2\65\u00b5\3\2\2\2\67"+
		"\u00b8\3\2\2\29\u00bb\3\2\2\2;\u00bd\3\2\2\2=\u00bf\3\2\2\2?\u00c1\3\2"+
		"\2\2A\u00c3\3\2\2\2C\u00c5\3\2\2\2E\u00c7\3\2\2\2G\u00d7\3\2\2\2I\u00e5"+
		"\3\2\2\2K\u00ea\3\2\2\2M\u00ef\3\2\2\2O\u00f3\3\2\2\2Q\u00fa\3\2\2\2S"+
		"\u00fe\3\2\2\2U\u0104\3\2\2\2W\u0109\3\2\2\2Y\u010e\3\2\2\2[\u0114\3\2"+
		"\2\2]\u0119\3\2\2\2_\u011c\3\2\2\2a\u0121\3\2\2\2c\u0125\3\2\2\2e\u012b"+
		"\3\2\2\2g\u0131\3\2\2\2i\u013a\3\2\2\2k\u0149\3\2\2\2m\u014b\3\2\2\2o"+
		"\u015b\3\2\2\2q\u015e\3\2\2\2s\u0167\3\2\2\2u\u016b\3\2\2\2w\u016d\3\2"+
		"\2\2yz\7.\2\2z\4\3\2\2\2{|\7/\2\2|}\7@\2\2}\6\3\2\2\2~\177\7=\2\2\177"+
		"\b\3\2\2\2\u0080\u0081\7-\2\2\u0081\n\3\2\2\2\u0082\u0083\7/\2\2\u0083"+
		"\f\3\2\2\2\u0084\u0085\7,\2\2\u0085\16\3\2\2\2\u0086\u0087\7\61\2\2\u0087"+
		"\20\3\2\2\2\u0088\u0089\7\'\2\2\u0089\22\3\2\2\2\u008a\u008b\7@\2\2\u008b"+
		"\24\3\2\2\2\u008c\u008d\7>\2\2\u008d\26\3\2\2\2\u008e\u008f\7@\2\2\u008f"+
		"\u0090\7?\2\2\u0090\30\3\2\2\2\u0091\u0092\7>\2\2\u0092\u0093\7?\2\2\u0093"+
		"\32\3\2\2\2\u0094\u0095\7?\2\2\u0095\u0096\7?\2\2\u0096\34\3\2\2\2\u0097"+
		"\u0098\7#\2\2\u0098\u0099\7?\2\2\u0099\36\3\2\2\2\u009a\u009b\7(\2\2\u009b"+
		"\u009c\7(\2\2\u009c \3\2\2\2\u009d\u009e\7~\2\2\u009e\u009f\7~\2\2\u009f"+
		"\"\3\2\2\2\u00a0\u00a1\7#\2\2\u00a1$\3\2\2\2\u00a2\u00a3\7@\2\2\u00a3"+
		"\u00a4\7@\2\2\u00a4&\3\2\2\2\u00a5\u00a6\7>\2\2\u00a6\u00a7\7>\2\2\u00a7"+
		"(\3\2\2\2\u00a8\u00a9\7(\2\2\u00a9*\3\2\2\2\u00aa\u00ab\7~\2\2\u00ab,"+
		"\3\2\2\2\u00ac\u00ad\7`\2\2\u00ad.\3\2\2\2\u00ae\u00af\7\u0080\2\2\u00af"+
		"\60\3\2\2\2\u00b0\u00b1\7?\2\2\u00b1\62\3\2\2\2\u00b2\u00b3\7-\2\2\u00b3"+
		"\u00b4\7-\2\2\u00b4\64\3\2\2\2\u00b5\u00b6\7/\2\2\u00b6\u00b7\7/\2\2\u00b7"+
		"\66\3\2\2\2\u00b8\u00b9\7]\2\2\u00b9\u00ba\7_\2\2\u00ba8\3\2\2\2\u00bb"+
		"\u00bc\7]\2\2\u00bc:\3\2\2\2\u00bd\u00be\7_\2\2\u00be<\3\2\2\2\u00bf\u00c0"+
		"\7*\2\2\u00c0>\3\2\2\2\u00c1\u00c2\7+\2\2\u00c2@\3\2\2\2\u00c3\u00c4\7"+
		"}\2\2\u00c4B\3\2\2\2\u00c5\u00c6\7\177\2\2\u00c6D\3\2\2\2\u00c7\u00c8"+
		"\7\61\2\2\u00c8\u00c9\7\61\2\2\u00c9\u00cd\3\2\2\2\u00ca\u00cc\13\2\2"+
		"\2\u00cb\u00ca\3\2\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00ce\3\2\2\2\u00cd\u00cb"+
		"\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0\u00d2\7\17\2\2"+
		"\u00d1\u00d0\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4"+
		"\7\f\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d6\b#\2\2\u00d6F\3\2\2\2\u00d7\u00d8"+
		"\7\61\2\2\u00d8\u00d9\7,\2\2\u00d9\u00dd\3\2\2\2\u00da\u00dc\13\2\2\2"+
		"\u00db\u00da\3\2\2\2\u00dc\u00df\3\2\2\2\u00dd\u00de\3\2\2\2\u00dd\u00db"+
		"\3\2\2\2\u00de\u00e0\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0\u00e1\7,\2\2\u00e1"+
		"\u00e2\7\61\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\b$\2\2\u00e4H\3\2\2\2"+
		"\u00e5\u00e6\7x\2\2\u00e6\u00e7\7q\2\2\u00e7\u00e8\7k\2\2\u00e8\u00e9"+
		"\7f\2\2\u00e9J\3\2\2\2\u00ea\u00eb\7d\2\2\u00eb\u00ec\7q\2\2\u00ec\u00ed"+
		"\7q\2\2\u00ed\u00ee\7n\2\2\u00eeL\3\2\2\2\u00ef\u00f0\7k\2\2\u00f0\u00f1"+
		"\7p\2\2\u00f1\u00f2\7v\2\2\u00f2N\3\2\2\2\u00f3\u00f4\7u\2\2\u00f4\u00f5"+
		"\7v\2\2\u00f5\u00f6\7t\2\2\u00f6\u00f7\7k\2\2\u00f7\u00f8\7p\2\2\u00f8"+
		"\u00f9\7i\2\2\u00f9P\3\2\2\2\u00fa\u00fb\7p\2\2\u00fb\u00fc\7g\2\2\u00fc"+
		"\u00fd\7y\2\2\u00fdR\3\2\2\2\u00fe\u00ff\7e\2\2\u00ff\u0100\7n\2\2\u0100"+
		"\u0101\7c\2\2\u0101\u0102\7u\2\2\u0102\u0103\7u\2\2\u0103T\3\2\2\2\u0104"+
		"\u0105\7p\2\2\u0105\u0106\7w\2\2\u0106\u0107\7n\2\2\u0107\u0108\7n\2\2"+
		"\u0108V\3\2\2\2\u0109\u010a\7v\2\2\u010a\u010b\7t\2\2\u010b\u010c\7w\2"+
		"\2\u010c\u010d\7g\2\2\u010dX\3\2\2\2\u010e\u010f\7h\2\2\u010f\u0110\7"+
		"c\2\2\u0110\u0111\7n\2\2\u0111\u0112\7u\2\2\u0112\u0113\7g\2\2\u0113Z"+
		"\3\2\2\2\u0114\u0115\7v\2\2\u0115\u0116\7j\2\2\u0116\u0117\7k\2\2\u0117"+
		"\u0118\7u\2\2\u0118\\\3\2\2\2\u0119\u011a\7k\2\2\u011a\u011b\7h\2\2\u011b"+
		"^\3\2\2\2\u011c\u011d\7g\2\2\u011d\u011e\7n\2\2\u011e\u011f\7u\2\2\u011f"+
		"\u0120\7g\2\2\u0120`\3\2\2\2\u0121\u0122\7h\2\2\u0122\u0123\7q\2\2\u0123"+
		"\u0124\7t\2\2\u0124b\3\2\2\2\u0125\u0126\7y\2\2\u0126\u0127\7j\2\2\u0127"+
		"\u0128\7k\2\2\u0128\u0129\7n\2\2\u0129\u012a\7g\2\2\u012ad\3\2\2\2\u012b"+
		"\u012c\7d\2\2\u012c\u012d\7t\2\2\u012d\u012e\7g\2\2\u012e\u012f\7c\2\2"+
		"\u012f\u0130\7m\2\2\u0130f\3\2\2\2\u0131\u0132\7e\2\2\u0132\u0133\7q\2"+
		"\2\u0133\u0134\7p\2\2\u0134\u0135\7v\2\2\u0135\u0136\7k\2\2\u0136\u0137"+
		"\7p\2\2\u0137\u0138\7w\2\2\u0138\u0139\7g\2\2\u0139h\3\2\2\2\u013a\u013b"+
		"\7t\2\2\u013b\u013c\7g\2\2\u013c\u013d\7v\2\2\u013d\u013e\7w\2\2\u013e"+
		"\u013f\7t\2\2\u013f\u0140\7p\2\2\u0140j\3\2\2\2\u0141\u014a\7\62\2\2\u0142"+
		"\u0146\t\2\2\2\u0143\u0145\t\3\2\2\u0144\u0143\3\2\2\2\u0145\u0148\3\2"+
		"\2\2\u0146\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u014a\3\2\2\2\u0148"+
		"\u0146\3\2\2\2\u0149\u0141\3\2\2\2\u0149\u0142\3\2\2\2\u014al\3\2\2\2"+
		"\u014b\u0150\7$\2\2\u014c\u014f\5o8\2\u014d\u014f\13\2\2\2\u014e\u014c"+
		"\3\2\2\2\u014e\u014d\3\2\2\2\u014f\u0152\3\2\2\2\u0150\u0151\3\2\2\2\u0150"+
		"\u014e\3\2\2\2\u0151\u0153\3\2\2\2\u0152\u0150\3\2\2\2\u0153\u0154\7$"+
		"\2\2\u0154n\3\2\2\2\u0155\u0156\7^\2\2\u0156\u015c\7$\2\2\u0157\u0158"+
		"\7^\2\2\u0158\u015c\7^\2\2\u0159\u015a\7^\2\2\u015a\u015c\7p\2\2\u015b"+
		"\u0155\3\2\2\2\u015b\u0157\3\2\2\2\u015b\u0159\3\2\2\2\u015cp\3\2\2\2"+
		"\u015d\u015f\t\4\2\2\u015e\u015d\3\2\2\2\u015f\u0160\3\2\2\2\u0160\u015e"+
		"\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0163\b9\2\2\u0163"+
		"r\3\2\2\2\u0164\u0165\7\17\2\2\u0165\u0168\7\f\2\2\u0166\u0168\t\5\2\2"+
		"\u0167\u0164\3\2\2\2\u0167\u0166\3\2\2\2\u0168\u0169\3\2\2\2\u0169\u016a"+
		"\b:\2\2\u016at\3\2\2\2\u016b\u016c\7\60\2\2\u016cv\3\2\2\2\u016d\u0171"+
		"\t\6\2\2\u016e\u0170\t\7\2\2\u016f\u016e\3\2\2\2\u0170\u0173\3\2\2\2\u0171"+
		"\u016f\3\2\2\2\u0171\u0172\3\2\2\2\u0172x\3\2\2\2\u0173\u0171\3\2\2\2"+
		"\16\2\u00cd\u00d1\u00dd\u0146\u0149\u014e\u0150\u015b\u0160\u0167\u0171"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}