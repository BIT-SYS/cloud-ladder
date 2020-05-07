package AST;

import IR.JumpIR;
import IR.JumpIfNotTrueIR;
import IR.Value;

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
        JumpIfNotTrueIR j = new JumpIfNotTrueIR(new Value(condition), ir.getLabel(middle));
        ir.emit(j);
        n1.getBlock().gen(before, after);
        ir.emit(new JumpIR(ir.getLabel(last)));
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
