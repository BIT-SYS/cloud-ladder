package ir;

import ast.ExpressionNode;
import symboltable.Type;

import java.util.List;

public class LazyExecutionStartIR extends IRNode {
  public Value getResult() {
    return result;
  }

  public List<Value> getParameters() {
    return parameters;
  }

  @Override
  public IRNodeInterface getNext() {
    return super.getNext();
  }

  Value result;
  List<Value> parameters;
  Type retType;

  // Procedure
//  public LazyExecutionStartIR(String name, Type ret, List<Value> parameters) {
//    this.parameters = parameters;
//    this.retType = ret;
//  }

  // lambda
  public LazyExecutionStartIR(ExpressionNode result, Type ret, List<Value> parameters) {
    this.result = new Value(result);
    this.parameters = parameters;
    this.retType = ret;
  }

  @Override
  public IROperator getOp() {
    return IROperator.LazyExecutionStart;
  }

  @Override
  public String toString() {
    return String.format("Lazy Execution Start: %s %s -> %s", result, parameters, retType);
  }
}
