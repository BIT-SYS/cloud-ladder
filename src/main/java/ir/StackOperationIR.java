package ir;

import java.util.Stack;

public class StackOperationIR extends IRNode {
  public static Stack<String> labelStack = new Stack<>();
  public int depth = 1;
  IROperator op;

  @Override
  public IROperator getOp() {
    return op;
  }
  StackOperationIR(IROperator op) {
    this.op = op;
  }
  StackOperationIR(IROperator op, int depth) {
    this.op = op;
    this.depth = depth;
  }

  public static StackOperationIR PushStack() {
    labelStack.push("");
    return new StackOperationIR(IROperator.PushStack);
  }
  public static StackOperationIR PushStack(String label) {
    labelStack.push(label);
    return new StackOperationIR(IROperator.PushStack);
  }
  public static StackOperationIR PopStack() {
    labelStack.pop();
    return new StackOperationIR(IROperator.PopStack);
  }
  public static StackOperationIR PopStack(String label) {
    return new StackOperationIR(IROperator.PopStack, labelStack.search(label));
  }

  @Override
  public String toString() {
    if (op == IROperator.PopStack) {

      return String.format("POP @STACK %d", depth);
    } else {
      return String.format("PUSH @STACK %d", depth);
    }
  }
}
