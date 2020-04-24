package IR;

import java.util.List;

public class IRNode implements IRNodeInterface {
  public IROperator op;
  public List<Label> labels;

  IRNode(){

  }
  IRNode(IROperator op) {
    this.op = op;
  }

  @Override
  public void register() {


  }

  @Override
  public IRNodeInterface getNext() {
    return null;
  }
}
