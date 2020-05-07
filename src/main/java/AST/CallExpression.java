package AST;

import IR.CallExprIR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallExpression extends ExpressionNode {
  // identifier or AST.MemberExpression
  public FunctionIdentifier callee;
  public List<ExpressionNode> arguments;


  @Override
  public ExpressionNode reduce() {
    return reduce(null);
  }

  public ExpressionNode reduce(ExpressionNode caller) {
    List<ExpressionNode> args = arguments.stream().map(ExpressionNode::reduce).collect(Collectors.toCollection(ArrayList::new));
    Temp t = new Temp();
    CallExprIR ceir = new CallExprIR(callee.toString(), caller, t, args);
    ir.emit(ceir);
    return t;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return gen();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(callee);
      addAll(arguments);
    }};
  }
}
