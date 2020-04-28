package IR;


public class BinaryExprIR extends QuadrupleNode {
  private IROperator op;

  @Override
  public IROperator getOp() {
    return null;
  }

  public BinaryExprIR(String op, Object arg1, Object arg2, Object result) {
    super(arg1, arg2, result);
    if (op.equals("+"))
      this.op = IROperator.AddExpr;
    else if (op.equals("-"))
      this.op = IROperator.SubExpr;
    else if (op.equals("*"))
      this.op = IROperator.MulExpr;
    else if (op.equals("/"))
      this.op = IROperator.DivExpr;
    else if (op.equals("and"))
      this.op = IROperator.AndExpr;
    else if (op.equals("or"))
      this.op = IROperator.OrExpr;
  }

  @Override
  public String toString() {
    return String.format("%s %s = %s %s %s\n",labels, result, arg1, op, arg2);
  }
}
