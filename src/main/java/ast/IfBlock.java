package ast;

import ir.JumpIR;
import ir.JumpIfNotTrueIR;
import ir.Value;

import java.util.ArrayList;
import java.util.List;

public class IfBlock extends Node implements HaveConditionAndBlock {
  public ExpressionNode condition;
  // may be null
  public Block statements;

  IfBlock(Block bl) {
    this.statements = bl;
  }


  @Override
  public ExpressionNode gen(int before, int after) {
    int label = newLabel();
    ExpressionNode reduced_condition = condition.gen(before, label);
    ir.emitLabel(label);
    int blockAfter = newLabel();

    JumpIfNotTrueIR jumpIfNotTrueIR = new JumpIfNotTrueIR(new Value(reduced_condition), ir.getLabel(blockAfter));
    jumpIfNotTrueIR.setDebugInfo(reduced_condition.getLineNumber(),reduced_condition.getSourceCode());
    ir.emit(jumpIfNotTrueIR);

    statements.gen(label, after);

    JumpIR jumpIR = new JumpIR(ir.getLabel(after));
    jumpIR.setDebugInfo(reduced_condition.getLineNumber(),reduced_condition.getSourceCode());
    ir.emit(jumpIR);
    ir.emitLabel(blockAfter);
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(condition);
      add(statements);
    }};
  }

  @Override
  public ExpressionNode getCondition() {
    return condition;
  }

  @Override
  public Block getBlock() {
    return statements;
  }
}
