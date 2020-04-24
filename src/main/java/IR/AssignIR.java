package IR;

public class AssignIR extends TripleNode {

  AssignIR(){
    op = IROperator.Assign;
  }
  public AssignIR(String lvalue, String rvalue) {
    arg1 = lvalue;
    arg2 = rvalue;
  }
}
