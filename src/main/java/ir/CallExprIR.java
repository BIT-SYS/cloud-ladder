package ir;

import ast.ExpressionNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallExprIR extends IRNode {

  public Value caller;
  public Value callee;
  public Value result;
  public List<Value> args;

  @Override
  public IROperator getOp() {
    return IROperator.CallExpr;
  }

  public CallExprIR(ExpressionNode callee, ExpressionNode caller, ExpressionNode result, List<ExpressionNode> args) {
    this.callee = new Value(callee);
    this.caller = caller != null ? new Value(caller): null;
    this.result = result != null ? new Value(result) : null;
    args = (args == null) ? new ArrayList<>() : args;
    this.args = args.stream().map(e -> new Value(e)).collect(Collectors.toList());
  }

  @Override
  public String toString() {
    if (caller != null && result != null) {
      return String.format("%s = %s.%s(%s)",result, caller, callee, args);
    } else if (result != null) {
      return String.format("%s = %s(%s)", result,callee,args);
    }
    else if (caller != null) {
      return String.format("%s.%s(%s)", caller, callee, args);
    } else {
      return String.format("%s(%s)", callee, args);
    }
  }
}
