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
	integerLiteral  # int
	| floatLiteral  # float // 其实这俩不用分
	| CHAR_LITERAL  # char
	| STRING_LITERAL # string
	| BOOL_LITERAL # bool
        ;

integerLiteral:
	DECIMAL_LITERAL
	| HEX_LITERAL
	| OCT_LITERAL
	| BINARY_LITERAL;

floatLiteral: FLOAT_LITERAL | HEX_FLOAT_LITERAL;

// STATEMENT / BLOCK

block: '{' NL* statement* NL* '}';

statement:
	IF expression NL? block (ELIF expression NL? block)* (ELSE NL? block)? # ifBlock //block还是controlStructureBody？
	| FOR typeType? IDENTIFIER IN expression NL? block # forBlock //TODO
	| WHILE expression NL? block # whileBlock
	// | RETURN expression? ';' // 需要吗？
	| BREAK NL # Break
	| CONTINUE NL # Continue
	| procedureDeclaration NL # procedureDecl
	| typeType IDENTIFIER '=' expression NL  # variableDecl
	| assignment NL # assign
        | expression NL # expr
	| emptyLines # empty
        ;

assignment: IDENTIFIER '=' expression;

emptyLines: NL+;

// EXPRESSION

// TODO 初始化列表/哈希表
expression:
        '(' expression ')'                                        # parens
        | literal                                                 # Lit
        | IDENTIFIER                                              # id
	| expression NL? bop = '.' ( IDENTIFIER | procedureCall)  # member
        | listInitializer                                         # listInit
	| procedureCall                                           # procedure
	| prefix = ('+' | '-' | 'not') expression                 # prefix
	| expression bop = ('*' | '/' | '%') expression           # MulDivMod
	| expression bop = ('+' | '-') expression                 # AddSub
        | expression bop = '^' expression                         # Exp
	| expression bop = ('<=' | '>=' | '>' | '<') expression   # Compare
	| expression bop = ('==' | '!=') expression               # PartialEqual
	| expression bop = ('and' | 'or'| 'xor') expression       # Logic
        | lambda                                                  # lam
        | expression'[' index=expression ']'                      # index
        ;

listInitializer: 
        '[' (expression ',')* expression ']'            # valuesListInitializer
        |'['expression op=('..'|'..=') expression  ']'  # rangeListInitializer
        ;

expressionList: expression (',' expression)*;

// PROCEDURE

lambdaParameter: typeType IDENTIFIER ;
lambdaParameterList: lambdaParameter (',' lambdaParameter)*;
lambda: '|' lambdaParameterList '|' '->' typeType block;


procedureCall: IDENTIFIER '(' expressionList ? ')';

procedureDeclaration:
	PROC IDENTIFIER '(' parameterList ')' ARROW typeType procedureBody;

procedureBody: block; // '=' expression ?

parameter: typeType IDENTIFIER ('=' expression)?;

parameterList: parameter (',' parameter)*;
