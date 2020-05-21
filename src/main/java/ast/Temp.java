package ast;

public class Temp extends ExpressionNode {
  static int indexOfAll = 0;
  int index;

  Temp() {
    index = indexOfAll++;
  }

  @Override
  public String toString() {
    return "t" + index;
  }
}
