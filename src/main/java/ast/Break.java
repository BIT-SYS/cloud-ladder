package ast;

import ir.BreakIR;
import ir.StackOperationIR;

import java.util.ArrayList;
import java.util.List;

public class Break extends Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    // 先弹出栈信息，再跳转XD
    StackOperationIR stackOperationIR = StackOperationIR.PopStack("special");
    Utils.setDebugInfo(stackOperationIR,this);
    ir.emit(stackOperationIR);

    BreakIR breakIR = new BreakIR(ir.getLabel(after));
    breakIR.setDebugInfo(getLineNumber(),getSourceCode());
    ir.emit(breakIR);
    return null;
  }
}
