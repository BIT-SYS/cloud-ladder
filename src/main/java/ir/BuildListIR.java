package ir;

import ast.ExpressionNode;
import symboltable.Type;

public class BuildListIR extends IRNode {
  public Value start;
  public Value end;
  public Value result;

  public Type type;

  /**
   * If initializer has range.
   * @return
   */
  public Boolean has_range() {
    return start != null && end !=null;
  }

  @Override
  public IROperator getOp() {
    return IROperator.BuildList;
  }

  public BuildListIR(ExpressionNode result, Type type){
    this(result, type, null, null);
  }
  public BuildListIR(ExpressionNode result, Type type, ExpressionNode start, ExpressionNode end) {
    this.result = new Value(result);
    this.type = type;
    this.start = new Value(start);
    this.end = new Value(end);
  }

  @Override
  public String toString() {
    if (start == null) {
     return String.format("%s %s = %s:[]", labels, result, type);
    } else {
      return String.format("%s %s = %s:[%s ... %s]",labels,result,type,start,end);
    }
  }
}
