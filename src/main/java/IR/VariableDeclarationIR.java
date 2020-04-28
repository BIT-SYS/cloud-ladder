package IR;

public class VariableDeclarationIR extends TripleNode {

  public VariableDeclarationIR(Object arg1, Object arg2) {
    super(arg1, arg2);
  }

  @Override
  public IROperator getOp() {
    return IROperator.VariableDeclaration;
  }
}
