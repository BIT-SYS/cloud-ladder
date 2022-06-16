lexer grammar CLLexer;

// keywords

//Boolean: 'boolean';
//String: 'string';
//Number: 'number';
//Audio: 'audio';
//Video: 'video';
//Image: 'image';

If: 'if';
Else: 'else';
While: 'while';
For: 'for';
Continue: 'continue';
Break: 'break';

Function: 'function';
Return: 'return';
Let: 'let';
Import: 'import';
Export: 'export';
From: 'from';
As: 'as';

// literals
NumberLiteral
    : IntegerLiteral
    | FloatLiteral
    ;

fragment
IntegerLiteral
    : '0'
    | [1-9] [0-9]*
    ;

fragment
FloatLiteral
    : '0.' [0-9]*
    | [1-9] [0-9]* '.' [0-9]*
    ;


BoolLiteral
    : 'true'
    | 'false'
    ;


StringLiteral
    : '"' StringCharacters? '"'
    ;

fragment
StringCharacters
    : StringCharacter+
    ;

fragment
StringCharacter
    : ~["\\\r\n]
    | EscapeSequence
    ;

fragment
EscapeSequence
    : '\\' [btnfr"'\\]
    ;


// punctuations
LParen: '(';
RParen: ')';
LBrace: '{';
RBrace: '}';
LBrack: '[';
RBrack: ']';
Semi: ';';
Comma: ',';
Dot: '.';
Colon: ':';
Arrow: '->';


// operators
Assign: '=';
AddAssign: '+=';
SubAssign: '-=';
MulAssign: '*=';
DivAssign: '/=';
ModAssign: '%=';
GT: '>';
LT: '<';
Equal: '==';
LE: '<=';
GE: '>=';
NotEqual: '!=';
And: '&&';
Or: '||';
Add: '+';
Sub: '-';
Mul: '*';
Div: '/';
Mod: '%';
Exclamation: '!';
Pipe: '|';




// Whitespace and comments
WS
    : [ \t\r\n\u000C]+ -> skip
    ;

Comment
    : '//' ~[\r\n]* -> skip
    ;


// identifier
Identifier
    : [_a-zA-Z] [_a-zA-Z0-9]*
    ;