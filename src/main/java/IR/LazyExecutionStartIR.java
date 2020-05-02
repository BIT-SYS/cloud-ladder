package IR;

import java.util.List;
import java.util.stream.Collectors;

public class LazyExecutionStartIR extends IRNode {
  String name;
  List<String> parameters;
  String retType;

  public LazyExecutionStartIR(String name, String ret,List<Object> parameters){
    this.name = name;
    this.parameters = parameters.stream().map(Object::toString).collect(Collectors.toList());
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
