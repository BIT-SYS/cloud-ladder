package IR;

public class JumpIR extends JumpIfNotTrueIR {

  public JumpIR(Label to) {
    this.to = to;
    this.condition = "false";
  }

  @Override
  public IROperator getOp() {
    return IROperator.Jump;
  }

  @Override
  public String toString() {
    return String.format("%s goto %s\n", labels, to);
  }
}
