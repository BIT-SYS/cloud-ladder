parser grammar CLParser;

options {
    tokenVocab=CLLexer;
}


// expression
expression
    : primaryExpression                             # expPrimaryExpression
    | Function LParen paramList? RParen compoundStatement   # expFunctionExpression
    | expression LBrack expression RBrack           # expArrayAccess
    | expression LParen argumentList? RParen        # expFunctionCall
    | expression Dot Identifier                     # expFieldAccess
    | op=(Sub | Exclamation) expression             # expUnaryExpression
    | expression op=(Mul | Div | Mod) expression    # expBinaryExpression
    | expression op=(Add | Sub) expression          # expBinaryExpression
    | expression op=(LT | GT | LE | GE) expression  # expBinaryExpression
    | expression op=(Equal | NotEqual) expression   # expBinaryExpression
    | expression op=And expression                  # expBinaryExpression
    | expression op=Or expression                   # expBinaryExpression
    | expression op=(Pipe | Arrow) expression                    # expPipeAndArrow
//    | expression Arrow expression                   # expArrow
    ;

argumentList
    : exp+=expression (Comma exp+=expression)*
    ;

primaryExpression
    : literal                                       # peLiteral
    | arrayLiteral                                  # peArrayLiteral
    | objLiteral                                    # peObjLiteral
    | LParen expression RParen                      # peParenExpression
    | Identifier                                    # peIdentifier
    ;

literal
    : NumberLiteral                                 # peNumberLiteral
    | BoolLiteral                                   # peBoolLiteral
    | StringLiteral                                 # peStringLiteral
    ;

arrayLiteral
    : LBrack exp+=expression? (Comma exp+=expression)* Comma? RBrack
    ;

objLiteral
    : LBrace item+=objItem? (Comma item+=objItem)* Comma? RBrace
    ;

objItem
    : Identifier (Colon expression)?                # staticObjItem
    | StringLiteral (Colon expression)              # stringObjItem
    | LBrack expression RBrack Colon expression     # dynamicObjItem
    ;

// statements
statement
    : selectionStatement
    | assignmentStatement Semi
    | dataStatement Semi
    | compoundStatement
    | breakStatement Semi
    | continueStatement Semi
    | iterationStatement
    | returnStatement
    | expressionStatement Semi
    ;

importStatement
    : Import path=StringLiteral (As as=Identifier)? Semi
    ;

exportStatement
    : Export Identifier Assign expression Semi
    ;

usingStatement
    : Using (scope=Identifier Dot)? name=Identifier From path=StringLiteral (As as=Identifier)? Semi
    ;

expressionStatement
    : expression
    ;

returnStatement
    : Return expression Semi
    ;

breakStatement
    : Break Identifier?
    ;

continueStatement
    : Continue Identifier?
    ;

dataStatement
    : Let dataStatementItemList
    ;

dataStatementItemList
    : item+=dataStatementItem (Comma item+=dataStatementItem)*
    ;

dataStatementItem
    : Identifier Assign expression
    ;

compoundStatement
    : LBrace (item+=statement)* RBrace
    ;

selectionStatement
    : If LParen expression RParen statement (Else statement)?
    ;

assignmentStatement
    : leftHandSideItem assignmentOperator expression
    ;

leftHandSideItem
    : Identifier                                    # assIdentifier
    | primaryExpression LBrack expression RBrack    # assignArray1
    | leftHandSideItem LBrack expression RBrack     # assignArray2
    | primaryExpression Dot Identifier              # assignField1
    | leftHandSideItem Dot Identifier               # assignField2
    ;

assignmentOperator
    : op=(Assign | AddAssign | SubAssign | MulAssign | DivAssign | ModAssign)
    ;

//arrayAccess
//    : primaryExpression LBrack expression RBrack
//    | leftHandSideItem LBrack expression RBrack
//    ;
//
//fieldAccess
//    : primaryExpression Dot Identifier
//    ;

iterationStatement
    : (Identifier Colon)? While LParen expression RParen statement  # whileStatement
    | (Identifier Colon)? For LParen assignmentStatement Semi expression Semi assignmentStatement RParen statement    # forStatement
    | (Identifier Colon)? For LParen dataStatement Semi expression Semi assignmentStatement RParen statement # forDeclStatement
    ;

functionDefinition
    : Function Identifier LParen paramList? RParen compoundStatement
    ;

paramList
    : item+=Identifier (Comma item+=Identifier)*
    ;

program
    : item+=programItem*
    ;

programItem
    : functionDefinition
    | statement
    | importStatement
    | exportStatement
    | usingStatement
    ;