package IR;

public class QuadrupleNode extends IRNode {
  public String arg1;
  public String arg2;
  public String result;

  QuadrupleNode() {
  }

  QuadrupleNode(IROperator op, String arg1, String arg2, String result) {
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.result = result;
  }
}
