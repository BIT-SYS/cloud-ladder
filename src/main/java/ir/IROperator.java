package ir;

public enum IROperator {
  Assign, VariableDeclaration, LazyExecutionStart, LazyExecutionEnd,
  AddExpr, SubExpr,
  MulExpr, DivExpr, AndExpr, OrExpr, XorExpr, NoOperation,
  ModExpr,EqualExpr,NotEqualExpr,
  LessThanExpr, GreaterThanExpr,LessEqualThanExpr,GreaterEqualThanExpr,
  CallExpr, BuildList,
  Return,
  JumpIfNotTrue,
  Jump,
  Break, Continue,
  PushStack, PopStack
}