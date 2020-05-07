package AST;

import IR.ContinueIR;

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
    emit("goto L" + before);
    return null;
  }
}
