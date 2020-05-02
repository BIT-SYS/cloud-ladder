package IR;

public class BuildListIR extends IRNode {
  String start;
  String end;
  String result;
  @Override
  public IROperator getOp() {
    return IROperator.BuildList;
  }

  public BuildListIR(Object result){
    this.result = result.toString();
  }
  public BuildListIR(Object result, Object start, Object end) {
    this(result);
    this.start = start.toString();
    this.end = end.toString();
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
