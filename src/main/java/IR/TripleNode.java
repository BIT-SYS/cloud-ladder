package IR;

public abstract class TripleNode extends IRNode {
  public String arg1;
  public String arg2;


  @Override
  public String toString() {
    return String.format("%s %s %s %s", labels, getOp(), arg1, arg2);
  }

  public TripleNode(Object arg1, Object arg2) {
    this.arg1 = arg1.toString();
    this.arg2 = arg2.toString();
  }
}
