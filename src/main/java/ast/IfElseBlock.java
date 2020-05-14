package ast;

import ir.JumpIR;
import ir.JumpIfNotTrueIR;
import ir.StackOperationIR;
import ir.Value;

import java.util.ArrayList;
import java.util.List;

public class IfElseBlock extends Node {
  public List<Node> ifelses = new ArrayList<>();

  @Override
  public ExpressionNode gen(int before, int after) {
    // TODO 作用域 和 标签的冲突
    int last = newLabel();
    for (Node n : ifelses) {
      int middle = newLabel();
      if (n instanceof HaveConditionAndBlock) {
        HaveConditionAndBlock n1 = (HaveConditionAndBlock) n;
        ExpressionNode condition = n1.getCondition().gen(0, 0);
        // fix ctx
        condition.ctx = n1.getCondition().ctx;

        JumpIfNotTrueIR j = new JumpIfNotTrueIR(new Value(condition), ir.getLabel(middle));
        j.setDebugInfo(condition.getLineNumber(),condition.getSourceCode());
        ir.emit(j);
        ir.emit(StackOperationIR.PushStack());
        n1.getBlock().gen(before, after);
        ir.emit(StackOperationIR.PopStack());

        JumpIR jumpIR = new JumpIR(ir.getLabel(last));
        jumpIR.setDebugInfo(condition.getLineNumber(), condition.getSourceCode());
        ir.emit(jumpIR);
      } else {
        System.err.println("ERROR AST");
      }
      ir.emitLabel(middle);
    }
    ir.emitLabel(last);
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(ifelses);
  }
}
