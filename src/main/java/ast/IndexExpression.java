package ast;

import ir.CallExprIR;

import java.util.ArrayList;

public class IndexExpression extends BinaryExpression {

  IndexExpression(ExpressionNode left, ExpressionNode right, String op) {
    super(left, right, op);
  }

  @Override
  public ExpressionNode gen() {
    ExpressionNode l = left.reduce();
    ExpressionNode r = right.reduce();
    Temp t = new Temp();
    ir.emit(new CallExprIR("get",l,t,new ArrayList<ExpressionNode>(){{add(r);}}));
    return t;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return gen();
  }
}
