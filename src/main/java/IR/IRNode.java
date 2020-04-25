package IR;

import java.util.List;

public abstract class IRNode implements IRNodeInterface {
  public List<Label> labels;

  IRNode(){

  }

  @Override
  public void register() {
  }


  @Override
  public IRNodeInterface getNext() {
    return null;
  }
}
