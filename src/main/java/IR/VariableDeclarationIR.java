package IR;

public class VariableDeclarationIR extends QuadrupleNode {

  public VariableDeclarationIR(Object result, Object type, Object value) {
    super(type, value, result);
  }

  @Override
  public IROperator getOp() {
    return IROperator.VariableDeclaration;
  }

  @Override
  public String toString() {
    return String.format("%s %s %s = %s", labels,arg1, result, arg2);
  }
}
