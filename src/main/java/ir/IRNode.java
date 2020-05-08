package ir;

import java.util.List;

public abstract class IRNode implements IRNodeInterface {
  public List<Label> labels;
  IRNodeInterface next;

  IRNode(){

  }

  @Override
  public void register() {
  }


  @Override
  public IRNodeInterface getNext() {
    return next;
  }

  public void setNext(IRNode next) {
    this.next = next;
  }

}
