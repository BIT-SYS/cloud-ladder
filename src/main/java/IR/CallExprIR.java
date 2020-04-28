package IR;

import java.util.List;
import java.util.stream.Collectors;

public class CallExprIR extends IRNode {

  public String caller;
  public String callee;
  public List<String> args;

  @Override
  public IROperator getOp() {
    return IROperator.CallExpr;
  }

  public CallExprIR(String Callee, List<Object> args) {
    this.callee = Callee;
    this.args = args.stream().map(Object::toString).collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return String.format("%s %s %s\n", labels, callee, args);
  }
}
