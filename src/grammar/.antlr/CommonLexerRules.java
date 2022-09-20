// Generated from /Users/celve/Repositories/Courses/University/meteor/src/grammar/CommonLexerRules.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommonLexerRules extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Add=1, Sub=2, Mul=3, Div=4, Mod=5, Greater=6, Less=7, GreaterEqual=8, 
		LessEqual=9, Equal=10, NotEqual=11, LogicalAnd=12, LogicalOr=13, LogicalNot=14, 
		RightShift=15, LeftShift=16, BitwiseAnd=17, BitwiseOr=18, BitwiseXor=19, 
		BitwiseNot=20, Assign=21, Increment=22, Decrement=23, Brackets=24, LeftBracket=25, 
		RightBracket=26, LeftParen=27, RightParen=28, LeftBrace=29, RightBrace=30, 
		LineComment=31, BlockComment=32, Void=33, Bool=34, Int=35, String=36, 
		New=37, Class=38, Null=39, True=40, False=41, This=42, If=43, Else=44, 
		For=45, While=46, Break=47, Continue=48, Return=49, IntegerLiteral=50, 
		StringLiteral=51, Escape=52, WhiteSpace=53, NewLine=54, Access=55, Id=56;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Add", "Sub", "Mul", "Div", "Mod", "Greater", "Less", "GreaterEqual", 
			"LessEqual", "Equal", "NotEqual", "LogicalAnd", "LogicalOr", "LogicalNot", 
			"RightShift", "LeftShift", "BitwiseAnd", "BitwiseOr", "BitwiseXor", "BitwiseNot", 
			"Assign", "Increment", "Decrement", "Brackets", "LeftBracket", "RightBracket", 
			"LeftParen", "RightParen", "LeftBrace", "RightBrace", "LineComment", 
			"BlockComment", "Void", "Bool", "Int", "String", "New", "Class", "Null", 
			"True", "False", "This", "If", "Else", "For", "While", "Break", "Continue", 
			"Return", "IntegerLiteral", "StringLiteral", "Escape", "WhiteSpace", 
			"NewLine", "Access", "Id"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'<'", "'>='", "'<='", 
			"'=='", "'!='", "'&&'", "'||'", "'!'", "'>>'", "'<<'", "'&'", "'|'", 
			"'^'", "'~'", "'='", "'++'", "'--'", "'[]'", "'['", "']'", "'('", "')'", 
			"'{'", "'}'", null, null, "'void'", "'bool'", "'int'", "'string'", "'new'", 
			"'class'", "'null'", "'true'", "'false'", "'this'", "'if'", "'else'", 
			"'for'", "'while'", "'break'", "'continue'", "'return'", null, null, 
			null, null, null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Add", "Sub", "Mul", "Div", "Mod", "Greater", "Less", "GreaterEqual", 
			"LessEqual", "Equal", "NotEqual", "LogicalAnd", "LogicalOr", "LogicalNot", 
			"RightShift", "LeftShift", "BitwiseAnd", "BitwiseOr", "BitwiseXor", "BitwiseNot", 
			"Assign", "Increment", "Decrement", "Brackets", "LeftBracket", "RightBracket", 
			"LeftParen", "RightParen", "LeftBrace", "RightBrace", "LineComment", 
			"BlockComment", "Void", "Bool", "Int", "String", "New", "Class", "Null", 
			"True", "False", "This", "If", "Else", "For", "While", "Break", "Continue", 
			"Return", "IntegerLiteral", "StringLiteral", "Escape", "WhiteSpace", 
			"NewLine", "Access", "Id"
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


	public CommonLexerRules(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CommonLexerRules.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2:\u0167\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35"+
		"\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3 \7 \u00bf\n \f \16 \u00c2\13 \3 "+
		"\5 \u00c5\n \3 \3 \3 \3 \3!\3!\3!\3!\7!\u00cf\n!\f!\16!\u00d2\13!\3!\3"+
		"!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%"+
		"\3%\3%\3%\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)"+
		"\3)\3)\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3-\3-\3-\3-\3-\3.\3."+
		"\3.\3.\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63"+
		"\3\63\3\63\7\63\u0138\n\63\f\63\16\63\u013b\13\63\5\63\u013d\n\63\3\64"+
		"\3\64\3\64\7\64\u0142\n\64\f\64\16\64\u0145\13\64\3\64\3\64\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\5\65\u014f\n\65\3\66\6\66\u0152\n\66\r\66\16\66\u0153"+
		"\3\66\3\66\3\67\3\67\3\67\5\67\u015b\n\67\3\67\3\67\38\38\39\39\79\u0163"+
		"\n9\f9\169\u0166\139\5\u00c0\u00d0\u0143\2:\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+"+
		"U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:\3\2\b\3\2\63;\3\2\62;\4"+
		"\2\13\13\"\"\4\2\f\f\17\17\4\2C\\c|\6\2\62;C\\aac|\2\u0172\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2"+
		"\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2"+
		"\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2"+
		"\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2"+
		"\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W"+
		"\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2"+
		"\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2"+
		"\2q\3\2\2\2\3s\3\2\2\2\5u\3\2\2\2\7w\3\2\2\2\ty\3\2\2\2\13{\3\2\2\2\r"+
		"}\3\2\2\2\17\177\3\2\2\2\21\u0081\3\2\2\2\23\u0084\3\2\2\2\25\u0087\3"+
		"\2\2\2\27\u008a\3\2\2\2\31\u008d\3\2\2\2\33\u0090\3\2\2\2\35\u0093\3\2"+
		"\2\2\37\u0095\3\2\2\2!\u0098\3\2\2\2#\u009b\3\2\2\2%\u009d\3\2\2\2\'\u009f"+
		"\3\2\2\2)\u00a1\3\2\2\2+\u00a3\3\2\2\2-\u00a5\3\2\2\2/\u00a8\3\2\2\2\61"+
		"\u00ab\3\2\2\2\63\u00ae\3\2\2\2\65\u00b0\3\2\2\2\67\u00b2\3\2\2\29\u00b4"+
		"\3\2\2\2;\u00b6\3\2\2\2=\u00b8\3\2\2\2?\u00ba\3\2\2\2A\u00ca\3\2\2\2C"+
		"\u00d8\3\2\2\2E\u00dd\3\2\2\2G\u00e2\3\2\2\2I\u00e6\3\2\2\2K\u00ed\3\2"+
		"\2\2M\u00f1\3\2\2\2O\u00f7\3\2\2\2Q\u00fc\3\2\2\2S\u0101\3\2\2\2U\u0107"+
		"\3\2\2\2W\u010c\3\2\2\2Y\u010f\3\2\2\2[\u0114\3\2\2\2]\u0118\3\2\2\2_"+
		"\u011e\3\2\2\2a\u0124\3\2\2\2c\u012d\3\2\2\2e\u013c\3\2\2\2g\u013e\3\2"+
		"\2\2i\u014e\3\2\2\2k\u0151\3\2\2\2m\u015a\3\2\2\2o\u015e\3\2\2\2q\u0160"+
		"\3\2\2\2st\7-\2\2t\4\3\2\2\2uv\7/\2\2v\6\3\2\2\2wx\7,\2\2x\b\3\2\2\2y"+
		"z\7\61\2\2z\n\3\2\2\2{|\7\'\2\2|\f\3\2\2\2}~\7@\2\2~\16\3\2\2\2\177\u0080"+
		"\7>\2\2\u0080\20\3\2\2\2\u0081\u0082\7@\2\2\u0082\u0083\7?\2\2\u0083\22"+
		"\3\2\2\2\u0084\u0085\7>\2\2\u0085\u0086\7?\2\2\u0086\24\3\2\2\2\u0087"+
		"\u0088\7?\2\2\u0088\u0089\7?\2\2\u0089\26\3\2\2\2\u008a\u008b\7#\2\2\u008b"+
		"\u008c\7?\2\2\u008c\30\3\2\2\2\u008d\u008e\7(\2\2\u008e\u008f\7(\2\2\u008f"+
		"\32\3\2\2\2\u0090\u0091\7~\2\2\u0091\u0092\7~\2\2\u0092\34\3\2\2\2\u0093"+
		"\u0094\7#\2\2\u0094\36\3\2\2\2\u0095\u0096\7@\2\2\u0096\u0097\7@\2\2\u0097"+
		" \3\2\2\2\u0098\u0099\7>\2\2\u0099\u009a\7>\2\2\u009a\"\3\2\2\2\u009b"+
		"\u009c\7(\2\2\u009c$\3\2\2\2\u009d\u009e\7~\2\2\u009e&\3\2\2\2\u009f\u00a0"+
		"\7`\2\2\u00a0(\3\2\2\2\u00a1\u00a2\7\u0080\2\2\u00a2*\3\2\2\2\u00a3\u00a4"+
		"\7?\2\2\u00a4,\3\2\2\2\u00a5\u00a6\7-\2\2\u00a6\u00a7\7-\2\2\u00a7.\3"+
		"\2\2\2\u00a8\u00a9\7/\2\2\u00a9\u00aa\7/\2\2\u00aa\60\3\2\2\2\u00ab\u00ac"+
		"\7]\2\2\u00ac\u00ad\7_\2\2\u00ad\62\3\2\2\2\u00ae\u00af\7]\2\2\u00af\64"+
		"\3\2\2\2\u00b0\u00b1\7_\2\2\u00b1\66\3\2\2\2\u00b2\u00b3\7*\2\2\u00b3"+
		"8\3\2\2\2\u00b4\u00b5\7+\2\2\u00b5:\3\2\2\2\u00b6\u00b7\7}\2\2\u00b7<"+
		"\3\2\2\2\u00b8\u00b9\7\177\2\2\u00b9>\3\2\2\2\u00ba\u00bb\7\61\2\2\u00bb"+
		"\u00bc\7\61\2\2\u00bc\u00c0\3\2\2\2\u00bd\u00bf\13\2\2\2\u00be\u00bd\3"+
		"\2\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1"+
		"\u00c4\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00c5\7\17\2\2\u00c4\u00c3\3"+
		"\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\7\f\2\2\u00c7"+
		"\u00c8\3\2\2\2\u00c8\u00c9\b \2\2\u00c9@\3\2\2\2\u00ca\u00cb\7\61\2\2"+
		"\u00cb\u00cc\7,\2\2\u00cc\u00d0\3\2\2\2\u00cd\u00cf\13\2\2\2\u00ce\u00cd"+
		"\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1"+
		"\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00d4\7,\2\2\u00d4\u00d5\7\61"+
		"\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\b!\2\2\u00d7B\3\2\2\2\u00d8\u00d9"+
		"\7x\2\2\u00d9\u00da\7q\2\2\u00da\u00db\7k\2\2\u00db\u00dc\7f\2\2\u00dc"+
		"D\3\2\2\2\u00dd\u00de\7d\2\2\u00de\u00df\7q\2\2\u00df\u00e0\7q\2\2\u00e0"+
		"\u00e1\7n\2\2\u00e1F\3\2\2\2\u00e2\u00e3\7k\2\2\u00e3\u00e4\7p\2\2\u00e4"+
		"\u00e5\7v\2\2\u00e5H\3\2\2\2\u00e6\u00e7\7u\2\2\u00e7\u00e8\7v\2\2\u00e8"+
		"\u00e9\7t\2\2\u00e9\u00ea\7k\2\2\u00ea\u00eb\7p\2\2\u00eb\u00ec\7i\2\2"+
		"\u00ecJ\3\2\2\2\u00ed\u00ee\7p\2\2\u00ee\u00ef\7g\2\2\u00ef\u00f0\7y\2"+
		"\2\u00f0L\3\2\2\2\u00f1\u00f2\7e\2\2\u00f2\u00f3\7n\2\2\u00f3\u00f4\7"+
		"c\2\2\u00f4\u00f5\7u\2\2\u00f5\u00f6\7u\2\2\u00f6N\3\2\2\2\u00f7\u00f8"+
		"\7p\2\2\u00f8\u00f9\7w\2\2\u00f9\u00fa\7n\2\2\u00fa\u00fb\7n\2\2\u00fb"+
		"P\3\2\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe\7t\2\2\u00fe\u00ff\7w\2\2\u00ff"+
		"\u0100\7g\2\2\u0100R\3\2\2\2\u0101\u0102\7h\2\2\u0102\u0103\7c\2\2\u0103"+
		"\u0104\7n\2\2\u0104\u0105\7u\2\2\u0105\u0106\7g\2\2\u0106T\3\2\2\2\u0107"+
		"\u0108\7v\2\2\u0108\u0109\7j\2\2\u0109\u010a\7k\2\2\u010a\u010b\7u\2\2"+
		"\u010bV\3\2\2\2\u010c\u010d\7k\2\2\u010d\u010e\7h\2\2\u010eX\3\2\2\2\u010f"+
		"\u0110\7g\2\2\u0110\u0111\7n\2\2\u0111\u0112\7u\2\2\u0112\u0113\7g\2\2"+
		"\u0113Z\3\2\2\2\u0114\u0115\7h\2\2\u0115\u0116\7q\2\2\u0116\u0117\7t\2"+
		"\2\u0117\\\3\2\2\2\u0118\u0119\7y\2\2\u0119\u011a\7j\2\2\u011a\u011b\7"+
		"k\2\2\u011b\u011c\7n\2\2\u011c\u011d\7g\2\2\u011d^\3\2\2\2\u011e\u011f"+
		"\7d\2\2\u011f\u0120\7t\2\2\u0120\u0121\7g\2\2\u0121\u0122\7c\2\2\u0122"+
		"\u0123\7m\2\2\u0123`\3\2\2\2\u0124\u0125\7e\2\2\u0125\u0126\7q\2\2\u0126"+
		"\u0127\7p\2\2\u0127\u0128\7v\2\2\u0128\u0129\7k\2\2\u0129\u012a\7p\2\2"+
		"\u012a\u012b\7w\2\2\u012b\u012c\7g\2\2\u012cb\3\2\2\2\u012d\u012e\7t\2"+
		"\2\u012e\u012f\7g\2\2\u012f\u0130\7v\2\2\u0130\u0131\7w\2\2\u0131\u0132"+
		"\7t\2\2\u0132\u0133\7p\2\2\u0133d\3\2\2\2\u0134\u013d\7\62\2\2\u0135\u0139"+
		"\t\2\2\2\u0136\u0138\t\3\2\2\u0137\u0136\3\2\2\2\u0138\u013b\3\2\2\2\u0139"+
		"\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u013d\3\2\2\2\u013b\u0139\3\2"+
		"\2\2\u013c\u0134\3\2\2\2\u013c\u0135\3\2\2\2\u013df\3\2\2\2\u013e\u0143"+
		"\7$\2\2\u013f\u0142\5i\65\2\u0140\u0142\13\2\2\2\u0141\u013f\3\2\2\2\u0141"+
		"\u0140\3\2\2\2\u0142\u0145\3\2\2\2\u0143\u0144\3\2\2\2\u0143\u0141\3\2"+
		"\2\2\u0144\u0146\3\2\2\2\u0145\u0143\3\2\2\2\u0146\u0147\7$\2\2\u0147"+
		"h\3\2\2\2\u0148\u0149\7^\2\2\u0149\u014f\7$\2\2\u014a\u014b\7^\2\2\u014b"+
		"\u014f\7^\2\2\u014c\u014d\7^\2\2\u014d\u014f\7p\2\2\u014e\u0148\3\2\2"+
		"\2\u014e\u014a\3\2\2\2\u014e\u014c\3\2\2\2\u014fj\3\2\2\2\u0150\u0152"+
		"\t\4\2\2\u0151\u0150\3\2\2\2\u0152\u0153\3\2\2\2\u0153\u0151\3\2\2\2\u0153"+
		"\u0154\3\2\2\2\u0154\u0155\3\2\2\2\u0155\u0156\b\66\2\2\u0156l\3\2\2\2"+
		"\u0157\u0158\7\17\2\2\u0158\u015b\7\f\2\2\u0159\u015b\t\5\2\2\u015a\u0157"+
		"\3\2\2\2\u015a\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015d\b\67\2\2"+
		"\u015dn\3\2\2\2\u015e\u015f\7\60\2\2\u015fp\3\2\2\2\u0160\u0164\t\6\2"+
		"\2\u0161\u0163\t\7\2\2\u0162\u0161\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162"+
		"\3\2\2\2\u0164\u0165\3\2\2\2\u0165r\3\2\2\2\u0166\u0164\3\2\2\2\16\2\u00c0"+
		"\u00c4\u00d0\u0139\u013c\u0141\u0143\u014e\u0153\u015a\u0164\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}