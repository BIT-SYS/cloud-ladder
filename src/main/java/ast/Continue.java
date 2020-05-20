package ast;

import ir.ContinueIR;
import ir.StackOperationIR;

import java.util.ArrayList;
import java.util.List;

public class Continue extends Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

  @Override
  public ExpressionNode gen(int before, int after) {

    StackOperationIR stackOperationIR = StackOperationIR.PopStack("special");
    Utils.setDebugInfo(stackOperationIR,this);
    ir.emit(stackOperationIR);

    ContinueIR continueIR = new ContinueIR(ir.getLabel(before));
    continueIR.setDebugInfo(getLineNumber(),getSourceCode());
    ir.emit(continueIR);
    return null;
  }
}
