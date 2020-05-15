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
    ContinueIR continueIR = new ContinueIR(ir.getLabel(before));
    continueIR.setDebugInfo(getLineNumber(),getSourceCode());
    ir.emit(continueIR);
    return null;
  }
}
