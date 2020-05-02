package IR;

public enum IROperator {
  Assign, VariableDeclaration, AddExpr, SubExpr,
  MulExpr, DivExpr, AndExpr, OrExpr, NoOperation,
  CallExpr,
  JumpIfNotTrue,
  Jump,
  Break, Continue,
}