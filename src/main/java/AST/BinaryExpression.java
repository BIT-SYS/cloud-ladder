package AST;

import IR.BinaryExprIR;

import java.util.ArrayList;
import java.util.List;

public class BinaryExpression extends ExpressionNode {
  public ExpressionNode left;

  public String op;
  public ExpressionNode right;


  @Override
  public ExpressionNode reduce() {
    ExpressionNode reducedExpr = gen();
    Temp t = new Temp();
    if (reducedExpr instanceof BinaryExpression) {
      BinaryExpression be = (BinaryExpression) reducedExpr;
      BinaryExprIR beir = new BinaryExprIR(be.op, be.left, be.right, t);
      ir.emit(beir);
    }
    return t;
  }

  BinaryExpression(ExpressionNode left, ExpressionNode right, String op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(left);
      add(right);
    }};
  }
}
