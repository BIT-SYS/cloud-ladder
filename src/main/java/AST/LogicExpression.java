package AST;

public class LogicExpression extends BinaryExpression {

  LogicExpression(ExpressionNode left, ExpressionNode right, String op) {
    super(left, right, op);
  }
//
//  @Override
//  public ExpressionNode gen() {
//    return new LogicExpression(left.reduce(), right.reduce(), op);
//  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return reduce();
  }

}
