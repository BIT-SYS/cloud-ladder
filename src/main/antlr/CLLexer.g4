lexer grammar CLLexer;

// Keywords

// 这两个要区分么？
BOOLEAN: 'Boolean';
ENUM: 'Enum';

STRING: 'String';
NUMBER: 'Number';
LIST: 'List';
SET: 'Set';
HASHMAP: 'HashMap';
AUDIO: 'Audio';
VIDEO: 'Video';
IMAGE: 'Image';
GENERICTYPE: 'Type' ('A'..'Z');

IF: 'if';
ELIF: 'elif';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
IN: 'in';
CONTINUE: 'continue';
BREAK: 'break';

PROC: 'fun' | 'proc';

// Literals

DECIMAL_LITERAL: ('0' | [1-9] (Digits? | '_'+ Digits)) [lL]?;
HEX_LITERAL:
	'0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL]?;
OCT_LITERAL: '0' '_'* [0-7] ([0-7_]* [0-7])? [lL]?;
BINARY_LITERAL: '0' [bB] [01] ([01_]* [01])? [lL]?;

FLOAT_LITERAL: (Digits '.' Digits | '.' Digits) ExponentPart? [fFdD]?
	| Digits (ExponentPart [fFdD]? | [fFdD]);

HEX_FLOAT_LITERAL:
	'0' [xX] HexDigits? '.' HexDigits [pP] [+-]? Digits [fFdD]?;

BOOL_LITERAL: 'true' | 'false';

CHAR_LITERAL: '\'' (~['\\\r\n] | EscapeSequence) '\'';

STRING_LITERAL: '"' (~["\\\r\n] | EscapeSequence)* '"';


// Separators

LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACK: '[';
RBRACK: ']';
SEMI: ';';
COMMA: ',';
DOT: '.';

// Operators

ASSIGN: '=';
GT: '>';
LT: '<';
BANG: '!';
EQUAL: '==';
LE: '<=';
GE: '>=';
NOTEQUAL: '!=';
AND: 'and';
OR: 'or';
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
MOD: '%';

ARROW: '->';

// Whitespace and comments
NL: '\n' | '\r' '\n'?;
WS: [\u0020\u0009\u000C]+ -> channel(HIDDEN);
MD_COMMENT: '///' ~[\r\n]* -> channel(HIDDEN);
LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN);

// Identifiers 小写开头？
IDENTIFIER: Letter LetterOrDigit*;

// Fragment rules

fragment ExponentPart: [eE] [+-]? Digits;

fragment EscapeSequence:
	'\\' [btnfr"'\\]
	| '\\' ([0-3]? [0-7])? [0-7]
	| '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit;

fragment HexDigits: HexDigit ((HexDigit | '_')* HexDigit)?;

fragment HexDigit: [0-9a-fA-F];

fragment Digits: [0-9] ([0-9_]* [0-9])?;

fragment LetterOrDigit: Letter | [0-9];

fragment Letter:
	[a-zA-Z$_] // these are the "java letters" below 0x7F
	| ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
	| [\uD800-\uDBFF] [\uDC00-\uDFFF]
		; // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
