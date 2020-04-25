package IR;

public abstract class QuadrupleNode extends IRNode {
  public String arg1;
  public String arg2;
  public String result;

  QuadrupleNode() {
  }

  QuadrupleNode(Object arg1, Object arg2, Object result) {
    this.arg1 = arg1.toString();
    this.arg2 = arg2.toString();
    this.result = result.toString();
  }

  @Override
  public String toString() {
    return String.format("%s %s = %s %s\n",labels, result, arg1, arg2);
  }
}
