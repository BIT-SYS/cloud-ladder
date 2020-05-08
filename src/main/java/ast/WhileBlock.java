package ast;

import ir.JumpIR;
import ir.JumpIfNotTrueIR;
import ir.StackOperationIR;
import ir.Value;

import java.util.ArrayList;
import java.util.List;

public class WhileBlock extends Node {
  public ExpressionNode condition;
  public Block statements;

  WhileBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    before = newLabel();
    after = newLabel();

    ir.emitLabel(before);
    ExpressionNode reduced_condition = condition.gen(0, 0);
    JumpIfNotTrueIR jintir = new JumpIfNotTrueIR(new Value(reduced_condition), ir.getLabel(after));
    ir.emit(jintir);
    ir.emit(StackOperationIR.PushStack());
    statements.gen(before, after);
    ir.emit(StackOperationIR.PopStack());
    ir.emit(new JumpIR(ir.getLabel(before)));
    ir.emitLabel(after);
    return null;

  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(condition);
      add(statements);
    }};
  }
}
