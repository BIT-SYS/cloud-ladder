package AST;

import IR.BreakIR;

import java.util.ArrayList;
import java.util.List;

public class Break extends Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    ir.emit(new BreakIR(ir.getLabel(after)));
    return null;
  }
}
