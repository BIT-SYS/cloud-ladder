package IR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallExprIR extends IRNode {

  public String caller;
  public String callee;
  public String result;
  public List<String> args;

  @Override
  public IROperator getOp() {
    return IROperator.CallExpr;
  }

  public CallExprIR(String callee, Object caller, Object result, List<Object> args) {
    this.callee = callee;
    this.caller = caller != null ? caller.toString() : null;
    this.result = result != null ? result.toString() : null;
    args = (args == null) ? new ArrayList<>() : args;
    this.args = args.stream().map(Object::toString).collect(Collectors.toList());
  }

  @Override
  public String toString() {
    if (caller != null && result != null) {
      return String.format("%s %s = %s.%s(%s)\n", labels,result, caller, callee, args);
    }
    if (caller != null) {
      return String.format("%s %s.%s(%s)\n", labels, caller, callee, args);
    } else {
      return String.format("%s %s(%s)\n", labels, callee, args);
    }
  }
}
