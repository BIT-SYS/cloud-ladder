package ir;


import ast.ExpressionNode;
import org.antlr.v4.runtime.ParserRuleContext;

public class BinaryExprIR extends QuadrupleNode {
  private IROperator op;

  @Override
  public IROperator getOp() {
    return op;
  }

  public BinaryExprIR(String op, ExpressionNode arg1, ExpressionNode arg2, ExpressionNode result, ParserRuleContext ctx) {
    super(new Value(arg1), new Value(arg2), new Value(result), 0,null);
    switch (op) {
      case "+":
        this.op = IROperator.AddExpr;
        break;
      case "-":
        this.op = IROperator.SubExpr;
        break;
      case "*":
        this.op = IROperator.MulExpr;
        break;
      case "/":
        this.op = IROperator.DivExpr;
        break;
      case "and":
        this.op = IROperator.AndExpr;
        break;
      case "or":
        this.op = IROperator.OrExpr;
        break;
      case "%":
        this.op = IROperator.ModExpr;
        break;
      case "==":
        this.op = IROperator.EqualExpr;
        break;
      case "!=":
        this.op = IROperator.NotEqualExpr;
        break;
      case "<":
        this.op = IROperator.LessThanExpr;
        break;
      case ">":
        this.op = IROperator.GreaterThanExpr;
        break;
      case "<=":
        this.op = IROperator.LessEqualThanExpr;
        break;
      case ">=":
        this.op = IROperator.GreaterEqualThanExpr;
        break;
      case "xor":
        this.op = IROperator.XorExpr;
        break;
    }
  }

  public BinaryExprIR(String op, ExpressionNode arg1, ExpressionNode arg2, ExpressionNode result ) {
    this(op,arg1,arg2,result,null);
  }
  @Override
  public String toString() {
    return String.format("%s = %s %s %s",result, arg1, op, arg2);
  }
}
