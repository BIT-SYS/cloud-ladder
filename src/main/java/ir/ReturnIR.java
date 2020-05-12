package ir;

public class ReturnIR extends IRNode {
  @Override
  public IROperator getOp() {
    return IROperator.Return;
  }
}
