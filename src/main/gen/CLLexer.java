// Generated from E:/IdeaProjects/cloudladder/src/main/antlr\CLLexer.g4 by ANTLR 4.9
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		If=1, Else=2, While=3, For=4, Continue=5, Break=6, Function=7, Return=8, 
		Let=9, Import=10, Export=11, From=12, As=13, NumberLiteral=14, BoolLiteral=15, 
		StringLiteral=16, LParen=17, RParen=18, LBrace=19, RBrace=20, LBrack=21, 
		RBrack=22, Semi=23, Comma=24, Dot=25, Colon=26, Arrow=27, Assign=28, AddAssign=29, 
		SubAssign=30, MulAssign=31, DivAssign=32, ModAssign=33, GT=34, LT=35, 
		Equal=36, LE=37, GE=38, NotEqual=39, And=40, Or=41, Add=42, Sub=43, Mul=44, 
		Div=45, Mod=46, Exclamation=47, Pipe=48, WS=49, Comment=50, Identifier=51;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"If", "Else", "While", "For", "Continue", "Break", "Function", "Return", 
			"Let", "Import", "Export", "From", "As", "NumberLiteral", "IntegerLiteral", 
			"FloatLiteral", "BoolLiteral", "StringLiteral", "StringCharacters", "StringCharacter", 
			"EscapeSequence", "LParen", "RParen", "LBrace", "RBrace", "LBrack", "RBrack", 
			"Semi", "Comma", "Dot", "Colon", "Arrow", "Assign", "AddAssign", "SubAssign", 
			"MulAssign", "DivAssign", "ModAssign", "GT", "LT", "Equal", "LE", "GE", 
			"NotEqual", "And", "Or", "Add", "Sub", "Mul", "Div", "Mod", "Exclamation", 
			"Pipe", "WS", "Comment", "Identifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'while'", "'for'", "'continue'", "'break'", 
			"'function'", "'return'", "'let'", "'import'", "'export'", "'from'", 
			"'as'", null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", "';'", 
			"','", "'.'", "':'", "'->'", "'='", "'+='", "'-='", "'*='", "'/='", "'%='", 
			"'>'", "'<'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'+'", 
			"'-'", "'*'", "'/'", "'%'", "'!'", "'|'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "If", "Else", "While", "For", "Continue", "Break", "Function", 
			"Return", "Let", "Import", "Export", "From", "As", "NumberLiteral", "BoolLiteral", 
			"StringLiteral", "LParen", "RParen", "LBrace", "RBrace", "LBrack", "RBrack", 
			"Semi", "Comma", "Dot", "Colon", "Arrow", "Assign", "AddAssign", "SubAssign", 
			"MulAssign", "DivAssign", "ModAssign", "GT", "LT", "Equal", "LE", "GE", 
			"NotEqual", "And", "Or", "Add", "Sub", "Mul", "Div", "Mod", "Exclamation", 
			"Pipe", "WS", "Comment", "Identifier"
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


	public CLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CLLexer.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\65\u0167\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\3\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\16\3\17\3\17\5\17\u00c1\n\17\3\20\3\20\3\20\7\20\u00c6\n\20\f\20"+
		"\16\20\u00c9\13\20\5\20\u00cb\n\20\3\21\3\21\3\21\3\21\7\21\u00d1\n\21"+
		"\f\21\16\21\u00d4\13\21\3\21\3\21\7\21\u00d8\n\21\f\21\16\21\u00db\13"+
		"\21\3\21\3\21\7\21\u00df\n\21\f\21\16\21\u00e2\13\21\5\21\u00e4\n\21\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00ef\n\22\3\23\3\23"+
		"\5\23\u00f3\n\23\3\23\3\23\3\24\6\24\u00f8\n\24\r\24\16\24\u00f9\3\25"+
		"\3\25\5\25\u00fe\n\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3"+
		"!\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3)\3)"+
		"\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\61"+
		"\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\6\67\u0150"+
		"\n\67\r\67\16\67\u0151\3\67\3\67\38\38\38\38\78\u015a\n8\f8\168\u015d"+
		"\138\38\38\39\39\79\u0163\n9\f9\169\u0166\139\2\2:\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\2!\2#\21%\22\'\2"+
		")\2+\2-\23/\24\61\25\63\26\65\27\67\309\31;\32=\33?\34A\35C\36E\37G I"+
		"!K\"M#O$Q%S&U\'W(Y)[*]+_,a-c.e/g\60i\61k\62m\63o\64q\65\3\2\n\3\2\63;"+
		"\3\2\62;\6\2\f\f\17\17$$^^\n\2$$))^^ddhhppttvv\5\2\13\f\16\17\"\"\4\2"+
		"\f\f\17\17\5\2C\\aac|\6\2\62;C\\aac|\2\u016f\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3"+
		"\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2"+
		"\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2"+
		"q\3\2\2\2\3s\3\2\2\2\5v\3\2\2\2\7{\3\2\2\2\t\u0081\3\2\2\2\13\u0085\3"+
		"\2\2\2\r\u008e\3\2\2\2\17\u0094\3\2\2\2\21\u009d\3\2\2\2\23\u00a4\3\2"+
		"\2\2\25\u00a8\3\2\2\2\27\u00af\3\2\2\2\31\u00b6\3\2\2\2\33\u00bb\3\2\2"+
		"\2\35\u00c0\3\2\2\2\37\u00ca\3\2\2\2!\u00e3\3\2\2\2#\u00ee\3\2\2\2%\u00f0"+
		"\3\2\2\2\'\u00f7\3\2\2\2)\u00fd\3\2\2\2+\u00ff\3\2\2\2-\u0102\3\2\2\2"+
		"/\u0104\3\2\2\2\61\u0106\3\2\2\2\63\u0108\3\2\2\2\65\u010a\3\2\2\2\67"+
		"\u010c\3\2\2\29\u010e\3\2\2\2;\u0110\3\2\2\2=\u0112\3\2\2\2?\u0114\3\2"+
		"\2\2A\u0116\3\2\2\2C\u0119\3\2\2\2E\u011b\3\2\2\2G\u011e\3\2\2\2I\u0121"+
		"\3\2\2\2K\u0124\3\2\2\2M\u0127\3\2\2\2O\u012a\3\2\2\2Q\u012c\3\2\2\2S"+
		"\u012e\3\2\2\2U\u0131\3\2\2\2W\u0134\3\2\2\2Y\u0137\3\2\2\2[\u013a\3\2"+
		"\2\2]\u013d\3\2\2\2_\u0140\3\2\2\2a\u0142\3\2\2\2c\u0144\3\2\2\2e\u0146"+
		"\3\2\2\2g\u0148\3\2\2\2i\u014a\3\2\2\2k\u014c\3\2\2\2m\u014f\3\2\2\2o"+
		"\u0155\3\2\2\2q\u0160\3\2\2\2st\7k\2\2tu\7h\2\2u\4\3\2\2\2vw\7g\2\2wx"+
		"\7n\2\2xy\7u\2\2yz\7g\2\2z\6\3\2\2\2{|\7y\2\2|}\7j\2\2}~\7k\2\2~\177\7"+
		"n\2\2\177\u0080\7g\2\2\u0080\b\3\2\2\2\u0081\u0082\7h\2\2\u0082\u0083"+
		"\7q\2\2\u0083\u0084\7t\2\2\u0084\n\3\2\2\2\u0085\u0086\7e\2\2\u0086\u0087"+
		"\7q\2\2\u0087\u0088\7p\2\2\u0088\u0089\7v\2\2\u0089\u008a\7k\2\2\u008a"+
		"\u008b\7p\2\2\u008b\u008c\7w\2\2\u008c\u008d\7g\2\2\u008d\f\3\2\2\2\u008e"+
		"\u008f\7d\2\2\u008f\u0090\7t\2\2\u0090\u0091\7g\2\2\u0091\u0092\7c\2\2"+
		"\u0092\u0093\7m\2\2\u0093\16\3\2\2\2\u0094\u0095\7h\2\2\u0095\u0096\7"+
		"w\2\2\u0096\u0097\7p\2\2\u0097\u0098\7e\2\2\u0098\u0099\7v\2\2\u0099\u009a"+
		"\7k\2\2\u009a\u009b\7q\2\2\u009b\u009c\7p\2\2\u009c\20\3\2\2\2\u009d\u009e"+
		"\7t\2\2\u009e\u009f\7g\2\2\u009f\u00a0\7v\2\2\u00a0\u00a1\7w\2\2\u00a1"+
		"\u00a2\7t\2\2\u00a2\u00a3\7p\2\2\u00a3\22\3\2\2\2\u00a4\u00a5\7n\2\2\u00a5"+
		"\u00a6\7g\2\2\u00a6\u00a7\7v\2\2\u00a7\24\3\2\2\2\u00a8\u00a9\7k\2\2\u00a9"+
		"\u00aa\7o\2\2\u00aa\u00ab\7r\2\2\u00ab\u00ac\7q\2\2\u00ac\u00ad\7t\2\2"+
		"\u00ad\u00ae\7v\2\2\u00ae\26\3\2\2\2\u00af\u00b0\7g\2\2\u00b0\u00b1\7"+
		"z\2\2\u00b1\u00b2\7r\2\2\u00b2\u00b3\7q\2\2\u00b3\u00b4\7t\2\2\u00b4\u00b5"+
		"\7v\2\2\u00b5\30\3\2\2\2\u00b6\u00b7\7h\2\2\u00b7\u00b8\7t\2\2\u00b8\u00b9"+
		"\7q\2\2\u00b9\u00ba\7o\2\2\u00ba\32\3\2\2\2\u00bb\u00bc\7c\2\2\u00bc\u00bd"+
		"\7u\2\2\u00bd\34\3\2\2\2\u00be\u00c1\5\37\20\2\u00bf\u00c1\5!\21\2\u00c0"+
		"\u00be\3\2\2\2\u00c0\u00bf\3\2\2\2\u00c1\36\3\2\2\2\u00c2\u00cb\7\62\2"+
		"\2\u00c3\u00c7\t\2\2\2\u00c4\u00c6\t\3\2\2\u00c5\u00c4\3\2\2\2\u00c6\u00c9"+
		"\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9"+
		"\u00c7\3\2\2\2\u00ca\u00c2\3\2\2\2\u00ca\u00c3\3\2\2\2\u00cb \3\2\2\2"+
		"\u00cc\u00cd\7\62\2\2\u00cd\u00ce\7\60\2\2\u00ce\u00d2\3\2\2\2\u00cf\u00d1"+
		"\t\3\2\2\u00d0\u00cf\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2"+
		"\u00d3\3\2\2\2\u00d3\u00e4\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00d9\t\2"+
		"\2\2\u00d6\u00d8\t\3\2\2\u00d7\u00d6\3\2\2\2\u00d8\u00db\3\2\2\2\u00d9"+
		"\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dc\3\2\2\2\u00db\u00d9\3\2"+
		"\2\2\u00dc\u00e0\7\60\2\2\u00dd\u00df\t\3\2\2\u00de\u00dd\3\2\2\2\u00df"+
		"\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e4\3\2"+
		"\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00cc\3\2\2\2\u00e3\u00d5\3\2\2\2\u00e4"+
		"\"\3\2\2\2\u00e5\u00e6\7v\2\2\u00e6\u00e7\7t\2\2\u00e7\u00e8\7w\2\2\u00e8"+
		"\u00ef\7g\2\2\u00e9\u00ea\7h\2\2\u00ea\u00eb\7c\2\2\u00eb\u00ec\7n\2\2"+
		"\u00ec\u00ed\7u\2\2\u00ed\u00ef\7g\2\2\u00ee\u00e5\3\2\2\2\u00ee\u00e9"+
		"\3\2\2\2\u00ef$\3\2\2\2\u00f0\u00f2\7$\2\2\u00f1\u00f3\5\'\24\2\u00f2"+
		"\u00f1\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5\7$"+
		"\2\2\u00f5&\3\2\2\2\u00f6\u00f8\5)\25\2\u00f7\u00f6\3\2\2\2\u00f8\u00f9"+
		"\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa(\3\2\2\2\u00fb"+
		"\u00fe\n\4\2\2\u00fc\u00fe\5+\26\2\u00fd\u00fb\3\2\2\2\u00fd\u00fc\3\2"+
		"\2\2\u00fe*\3\2\2\2\u00ff\u0100\7^\2\2\u0100\u0101\t\5\2\2\u0101,\3\2"+
		"\2\2\u0102\u0103\7*\2\2\u0103.\3\2\2\2\u0104\u0105\7+\2\2\u0105\60\3\2"+
		"\2\2\u0106\u0107\7}\2\2\u0107\62\3\2\2\2\u0108\u0109\7\177\2\2\u0109\64"+
		"\3\2\2\2\u010a\u010b\7]\2\2\u010b\66\3\2\2\2\u010c\u010d\7_\2\2\u010d"+
		"8\3\2\2\2\u010e\u010f\7=\2\2\u010f:\3\2\2\2\u0110\u0111\7.\2\2\u0111<"+
		"\3\2\2\2\u0112\u0113\7\60\2\2\u0113>\3\2\2\2\u0114\u0115\7<\2\2\u0115"+
		"@\3\2\2\2\u0116\u0117\7/\2\2\u0117\u0118\7@\2\2\u0118B\3\2\2\2\u0119\u011a"+
		"\7?\2\2\u011aD\3\2\2\2\u011b\u011c\7-\2\2\u011c\u011d\7?\2\2\u011dF\3"+
		"\2\2\2\u011e\u011f\7/\2\2\u011f\u0120\7?\2\2\u0120H\3\2\2\2\u0121\u0122"+
		"\7,\2\2\u0122\u0123\7?\2\2\u0123J\3\2\2\2\u0124\u0125\7\61\2\2\u0125\u0126"+
		"\7?\2\2\u0126L\3\2\2\2\u0127\u0128\7\'\2\2\u0128\u0129\7?\2\2\u0129N\3"+
		"\2\2\2\u012a\u012b\7@\2\2\u012bP\3\2\2\2\u012c\u012d\7>\2\2\u012dR\3\2"+
		"\2\2\u012e\u012f\7?\2\2\u012f\u0130\7?\2\2\u0130T\3\2\2\2\u0131\u0132"+
		"\7>\2\2\u0132\u0133\7?\2\2\u0133V\3\2\2\2\u0134\u0135\7@\2\2\u0135\u0136"+
		"\7?\2\2\u0136X\3\2\2\2\u0137\u0138\7#\2\2\u0138\u0139\7?\2\2\u0139Z\3"+
		"\2\2\2\u013a\u013b\7(\2\2\u013b\u013c\7(\2\2\u013c\\\3\2\2\2\u013d\u013e"+
		"\7~\2\2\u013e\u013f\7~\2\2\u013f^\3\2\2\2\u0140\u0141\7-\2\2\u0141`\3"+
		"\2\2\2\u0142\u0143\7/\2\2\u0143b\3\2\2\2\u0144\u0145\7,\2\2\u0145d\3\2"+
		"\2\2\u0146\u0147\7\61\2\2\u0147f\3\2\2\2\u0148\u0149\7\'\2\2\u0149h\3"+
		"\2\2\2\u014a\u014b\7#\2\2\u014bj\3\2\2\2\u014c\u014d\7~\2\2\u014dl\3\2"+
		"\2\2\u014e\u0150\t\6\2\2\u014f\u014e\3\2\2\2\u0150\u0151\3\2\2\2\u0151"+
		"\u014f\3\2\2\2\u0151\u0152\3\2\2\2\u0152\u0153\3\2\2\2\u0153\u0154\b\67"+
		"\2\2\u0154n\3\2\2\2\u0155\u0156\7\61\2\2\u0156\u0157\7\61\2\2\u0157\u015b"+
		"\3\2\2\2\u0158\u015a\n\7\2\2\u0159\u0158\3\2\2\2\u015a\u015d\3\2\2\2\u015b"+
		"\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015e\3\2\2\2\u015d\u015b\3\2"+
		"\2\2\u015e\u015f\b8\2\2\u015fp\3\2\2\2\u0160\u0164\t\b\2\2\u0161\u0163"+
		"\t\t\2\2\u0162\u0161\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162\3\2\2\2\u0164"+
		"\u0165\3\2\2\2\u0165r\3\2\2\2\u0166\u0164\3\2\2\2\21\2\u00c0\u00c7\u00ca"+
		"\u00d2\u00d9\u00e0\u00e3\u00ee\u00f2\u00f9\u00fd\u0151\u015b\u0164\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}