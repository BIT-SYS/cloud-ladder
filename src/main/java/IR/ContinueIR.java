package IR;

public class ContinueIR extends JumpIR {

  public ContinueIR(Label to) {
    super(to);
  }

  @Override
  public IROperator getOp() {
    return IROperator.Continue;
  }
}
