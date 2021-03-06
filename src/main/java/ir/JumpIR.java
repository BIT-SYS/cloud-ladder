package ir;

public class JumpIR extends JumpIfNotTrueIR {

  public JumpIR(Label to) {
    this.to = to;
    this.condition = Value.LiteralBoolean("false");
  }

  @Override
  public IROperator getOp() {
    return IROperator.Jump;
  }

  @Override
  public String toString() {
    return String.format("goto %s", to);
  }
}
