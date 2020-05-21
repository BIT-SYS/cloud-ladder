package ir;

import ast.ExpressionNode;
import org.antlr.v4.runtime.ParserRuleContext;

public class AssignIR extends TripleNode {

  public AssignIR(ExpressionNode lvalue, ExpressionNode rvalue) {
    this(lvalue, rvalue,0, null);
  }
  public AssignIR(ExpressionNode lvalue, ExpressionNode rvalue, int lineNumber, String sourceCode) {
    super(new Value(lvalue), new Value(rvalue), lineNumber, sourceCode);
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
