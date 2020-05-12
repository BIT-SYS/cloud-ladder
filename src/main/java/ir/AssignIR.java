package ir;

import ast.ExpressionNode;

public class AssignIR extends TripleNode {

  public AssignIR(ExpressionNode lvalue, ExpressionNode rvalue) {
    super(new Value(lvalue), new Value(rvalue));
  }

  public Value getLValue() {
    return arg1;
  }

  public Value getRValue() {
    return arg2;
  }

  @Override
  public IROperator getOp() {
    return IROperator.Assign;
  }
}
