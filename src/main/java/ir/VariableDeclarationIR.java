package ir;

public class VariableDeclarationIR extends TripleNode {

  public VariableDeclarationIR(Value result, Value value) {
    super(value, result);
  }

  public Value getResult() {
    return this.arg2;
  }

  public Value getValue() {
    return this.arg1;
  }

  @Override
  public IROperator getOp() {
    return IROperator.VariableDeclaration;
  }

  @Override
  public String toString() {
    return String.format("%s %s = %s", labels,arg2, arg1);
  }
}
