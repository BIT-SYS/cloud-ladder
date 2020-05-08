package ir;

public class JumpIfNotTrueIR extends IRNode {
  public Label to;
  public Value condition;
  @Override
  public IROperator getOp() {
    return IROperator.JumpIfNotTrue;
  }
  JumpIfNotTrueIR(){

  }
  public JumpIfNotTrueIR(Value condition, Label to) {
    this.condition = condition;
    this.to = to;
  }

  @Override
  public String toString() {
    return String.format("%s if true != %s goto %s",labels,condition, to);
  }
}
