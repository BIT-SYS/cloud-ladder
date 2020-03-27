grammar CLParser;
import CLLexer;

// options {
// 	tokenVocab = CLLexer;
// }

// TYPE
program: statement+;

basicType:
	BOOLEAN
	| STRING
	| NUMBER
	| LIST '<' typeType '>'
	| SET '<' typeType '>'
	| HASHMAP '<' key = typeType ',' value = typeType '>'
	| AUDIO
	| VIDEO
	| IMAGE;

typeType: basicType; //可能会有自定义类型

// LITERAL

literal:
	integerLiteral
	| floatLiteral // 其实这俩不用分
	| CHAR_LITERAL
	| STRING_LITERAL
	| BOOL_LITERAL;

integerLiteral:
	DECIMAL_LITERAL
	| HEX_LITERAL
	| OCT_LITERAL
	| BINARY_LITERAL;

floatLiteral: FLOAT_LITERAL | HEX_FLOAT_LITERAL;

// STATEMENT / BLOCK

block: '{' NL* statement* NL* '}';

statement:
	IF expression NL? block (ELIF expression NL? block)* (ELSE NL? block)? //block还是controlStructureBody？
	| FOR typeType? IDENTIFIER IN expression NL? block //TODO
	| WHILE expression NL? block
	// | RETURN expression? ';' // 需要吗？
	| BREAK NL
	| CONTINUE NL
	| procedureDeclaration NL
	| variableDeclaration NL
	| assignment NL
        | expression NL
	| emptyLines;

assignment: IDENTIFIER '=' expression;

variableDeclaration: typeType IDENTIFIER '=' expression;

emptyLines: NL+;

// EXPRESSION

primary: '(' expression ')' | literal | IDENTIFIER;

// TODO 初始化列表/哈希表
expression returns [String type]
	: primary
	| expression NL? bop = '.' ( IDENTIFIER | procedureCall)
        // list initialization with `..`
        | listInitializer
        // list initialization
        // | '[' (expression ',')* expression? ']'
	| procedureCall
	| prefix = ('+' | '-') expression
	// | prefix = ('!') expression
	| expression bop = ('*' | '/' | '%') expression
	| expression bop = ('+' | '-') expression
        | expression bop = '^' expression
	| expression bop = ('<=' | '>=' | '>' | '<') expression
	| expression bop = ('==' | '!=') expression
	| expression bop = ('and' | 'or') expression
        | lambda
        ;

listInitializer: 
        '[' (expression ',')* expression ']'
        |'['expression ('..'|'..=') expression  ']'
        ;

expressionList: expression (',' expression)*;

// PROCEDURE

lambdaParameter: typeType? IDENTIFIER ;
lambdaParameterList: lambdaParameter (',' lambdaParameter)*;
lambda: '|' lambdaParameterList '|' expression;


procedureCall: IDENTIFIER '(' (expressionList | lambda)? ')';

procedureDeclaration:
	PROC IDENTIFIER '(' parameterList ')' ARROW typeType procedureBody;

procedureBody: block; // '=' expression ?

parameter: typeType IDENTIFIER ('=' expression)?;

parameterList: parameter (',' parameter)*;
