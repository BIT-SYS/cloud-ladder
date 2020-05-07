package IR;

import AST.ExpressionNode;

public class AssignIR extends TripleNode {

  public AssignIR(ExpressionNode lvalue, ExpressionNode rvalue) {
    super(new Value(lvalue), new Value(rvalue));
  }

  @Override
  public IROperator getOp() {
    return IROperator.Assign;
  }
}
