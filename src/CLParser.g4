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

block: '{' statement* '}';

statement:
	blockLabel = block // 这是什么？
	| IF expression block (ELIF block)* (ELSE block)? //block还是controlStructureBody？
	| FOR typeType? IDENTIFIER IN expression block //TODO
	| WHILE expression block
	// | RETURN expression? ';' // 需要吗？
	| BREAK NL
	| CONTINUE NL
	| procedureDeclaration
	| typeType IDENTIFIER '=' expression ';'? NL
	| IDENTIFIER '=' expression NL
        | expression ';'? NL
	| NL;

// EXPRESSION

primary: '(' expression ')' | literal | IDENTIFIER;

// TODO 初始化列表/哈希表
expression:
	primary
	| expression bop = '.' ( IDENTIFIER | procedureCall)
	| expression '[' expression ']'
	| procedureCall
	| prefix = ('+' | '-') expression
	// | prefix = ('!') expression
	| expression bop = ('*' | '/' | '%') expression
	| expression bop = ('+' | '-') expression
        | expression bop = '^' expression
	| expression bop = ('<=' | '>=' | '>' | '<') expression
	| expression bop = ('==' | '!=') expression
	| expression bop = ('and' | 'or') expression
        | expression hop = '|' parameterList '|' expression
        ;

expressionList: expression (',' expression)*;

// PROCEDURE

procedureCall: IDENTIFIER '(' expressionList? ')';

procedureDeclaration:
	PROC IDENTIFIER '(' parameterList ')' ARROW typeType procedureBody;

procedureBody: block; // '=' expression ?

parameter: typeType IDENTIFIER ('=' expression)?;

parameterList: parameter (',' parameter)*;
