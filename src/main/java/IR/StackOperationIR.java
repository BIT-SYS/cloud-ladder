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

  static StackOperationIR PushStack() {
    return new StackOperationIR(IROperator.PushStack);
  }
  static StackOperationIR popStack() {
    return new StackOperationIR(IROperator.PopStack);
  }
}
