package IR;

import AST.ExpressionNode;

public class BuildListIR extends IRNode {
  Value start;
  Value end;
  Value result;
  @Override
  public IROperator getOp() {
    return IROperator.BuildList;
  }

  public BuildListIR(ExpressionNode result){
    this.result = new Value(result);
  }
  public BuildListIR(ExpressionNode result, ExpressionNode start, ExpressionNode end) {
    this(result);
    this.start = new Value(start);
    this.end = new Value(end);
  }

  @Override
  public String toString() {
    if (start == null) {
     return String.format("%s %s = []", labels,result);
    } else {
      return String.format("%s %s [%s ... %s]",labels,result,start,end );
    }
  }
}
