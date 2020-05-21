package ir;

import ast.ExpressionNode;
import symboltable.Type;

import java.util.List;
import java.util.stream.Collectors;

public class BuildListIR extends IRNode {
  public Value result;
  // [x .. y]
  public Value start;
  public Value end;
  // [1, 2, 3]
  public List<Value> values;

  public Type type;

  /**
   * If initializer has range.
   *
   * @return
   */
  public Boolean has_range() {
    return start != null && end != null;
  }

  public Boolean has_values() {
    return values != null;
  }

  @Override
  public IROperator getOp() {
    return IROperator.BuildList;
  }

  public BuildListIR(ExpressionNode result, Type type) {
    this(result, type, null, null);
  }

  public BuildListIR(ExpressionNode result, Type type, ExpressionNode start, ExpressionNode end) {
    this.result = new Value(result);
    this.type = type;
    if (start != null) {
      this.start = new Value(start);
      this.end = new Value(end);
    }
  }

  public BuildListIR(ExpressionNode result, Type type, List<ExpressionNode> values) {
    this(result, type);
    this.values = values.stream().map(Value::new).collect(Collectors.toList());
  }

  @Override
  public String toString() {
    if (start == null) {
      return String.format("%s = %s:[]", result, type);
    } else {
      return String.format("%s = %s:[%s ... %s]", result, type, start, end);
    }
  }
}
