package ast;

import ir.BreakIR;

import java.util.ArrayList;
import java.util.List;

public class Break extends Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    BreakIR breakIR = new BreakIR(ir.getLabel(after));
    breakIR.setDebugInfo(getLineNumber(),getSourceCode());
    ir.emit(breakIR);
    return null;
  }
}
