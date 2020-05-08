package AST;

import IR.BinaryExprIR;

public class ArithmeticExpression extends BinaryExpression {
  ArithmeticExpression(ExpressionNode left, ExpressionNode right, String op) {
    super(left, right, op);
  }


//  @Override
//  public ExpressionNode gen() {
//    ExpressionNode l = left.reduce();
//    ExpressionNode r = right.reduce();
//    Temp t = new Temp();
//    ir.emit(new BinaryExprIR(op,l,r,t));
//    return t;
//  }


  @Override
  public ExpressionNode gen(int before, int after) {
    return gen();
  }

  @Override
  public String toString() {
    return left.toString() + " " + op + " " + right.toString();
  }
}
