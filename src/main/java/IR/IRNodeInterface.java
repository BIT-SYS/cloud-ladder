package IR;

public interface IRNodeInterface {

  public void register();
  public IROperator getOp();
  public IRNodeInterface getNext();
}
