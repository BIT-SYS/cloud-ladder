package IR;

public class JumpIfNotTrueIR extends IRNode {
  public Label to;
  public String condition;
  @Override
  public IROperator getOp() {
    return IROperator.JumpIfNotTrue;
  }
  JumpIfNotTrueIR(){

  }
  public JumpIfNotTrueIR(Object condition, Label to) {
    this.condition = condition.toString();
    this.to = to;
  }

  @Override
  public String toString() {
    return String.format("%s if true != %s goto %s\n",labels,condition, to);
  }
}
