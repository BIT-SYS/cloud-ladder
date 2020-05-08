package ast;

import ir.ContinueIR;

import java.util.ArrayList;
import java.util.List;

public class Continue extends Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    ir.emit(new ContinueIR(ir.getLabel(before)));
    return null;
  }
}
