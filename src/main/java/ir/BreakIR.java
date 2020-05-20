package ir;

public class BreakIR extends JumpIR{
  public BreakIR(Label to) {
    super(to);
  }

  @Override
  public IROperator getOp() {
    return IROperator.Jump;
  }
}
