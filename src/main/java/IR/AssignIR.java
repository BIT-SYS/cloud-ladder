package IR;

public class AssignIR extends TripleNode {

  public AssignIR(Object lvalue, Object rvalue) {
    super(lvalue, rvalue);

  }

  @Override
  public IROperator getOp() {
    return IROperator.Assign;
  }
}
