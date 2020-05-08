package ir;

public class NoOperationIR extends IRNode {
  @Override
  public IROperator getOp() {
    return IROperator.NoOperation;
  }

  @Override
  public String toString() {
    return String.format("%s NOP", labels);
  }
}
