package ir;

import symboltable.Type;

import java.util.List;

public class LazyExecutionStartIR extends IRNode {
  public String getName() {
    return name;
  }

  public List<Value> getParameters() {
    return parameters;
  }

  @Override
  public IRNodeInterface getNext() {
    return super.getNext();
  }

  String name;
  List<Value> parameters;
  Type retType;

  public LazyExecutionStartIR(String name, Type ret,List<Value> parameters){
    this.name = name;
    this.parameters = parameters;
    this.retType = ret;
  }
  @Override
  public IROperator getOp() {
    return IROperator.LazyExecutionStart;
  }

  @Override
  public String toString() {
    return String.format("%s Lazy Execution Start: %s %s -> %s", labels,name,parameters, retType);
  }
}
