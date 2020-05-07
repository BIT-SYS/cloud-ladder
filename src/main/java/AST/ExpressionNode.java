package AST;

import java.util.ArrayList;
import java.util.List;

public class ExpressionNode extends Node {

  public ExpressionNode reduce() {
    return this;
  }

  public ExpressionNode gen() {
    System.out.println("unimplemented gen: " + this.getClass().getName());
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

}
