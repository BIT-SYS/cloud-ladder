package AST;

import IR.AssignIR;

import java.util.ArrayList;
import java.util.List;

public class Assign extends ExpressionNode {
  public Identifier lvalue;
  public ExpressionNode rvalue;

  Assign(String lvalue, ExpressionNode rvalue) {
    this.lvalue = new Identifier(lvalue);
    this.rvalue = rvalue;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    ir.emit(new AssignIR(lvalue.gen(), rvalue.gen()));
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(lvalue);
      add(rvalue);
    }};
  }
}
