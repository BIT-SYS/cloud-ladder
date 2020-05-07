package AST;

public class LogicExpression extends BinaryExpression {

  LogicExpression(ExpressionNode left, ExpressionNode right, String op) {
    super(left, right, op);
  }

  @Override
  public ExpressionNode gen() {
    return new ArithmeticExpression(left.reduce(), right.reduce(), op);
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return reduce();
  }

}
