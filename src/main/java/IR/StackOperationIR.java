package IR;

public class StackOperationIR extends IRNode {
  IROperator op;

  @Override
  public IROperator getOp() {
    return op;
  }
  StackOperationIR(IROperator op) {
    this.op = op;
  }

  public static StackOperationIR PushStack() {
    return new StackOperationIR(IROperator.PushStack);
  }
  public static StackOperationIR PopStack() {
    return new StackOperationIR(IROperator.PopStack);
  }

  @Override
  public String toString() {
    if (op == IROperator.PopStack) {

      return "POP @STACK";
    } else {
      return "PUSH @STACK";
    }
  }
}
