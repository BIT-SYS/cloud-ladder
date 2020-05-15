package ast;

import ir.*;
import symboltable.SimpleType;

import java.util.ArrayList;
import java.util.List;


public class ForBlock extends Node {
  public Identifier for_id;
  public ExpressionNode for_expr;
  public String iter_type;
  public Block statements;

  ForBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    before = newLabel();
    after = newLabel();
    ExpressionNode expr = for_expr.gen(0, 0);
    Temp t = new Temp();
    Temp len = new Temp();
    ir.emit(StackOperationIR.PushStack());
    // get expr len
    CallExprIR callExprIR = new CallExprIR("size", expr, len, new ArrayList<>());
    callExprIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    ir.emit(callExprIR);

    VariableDeclarationIR number = new VariableDeclarationIR(new Value(t), Value.Literal("0", new SimpleType("Number")));
    number.setDebugInfo(for_id.getLineNumber(),for_id.getSourceCode());
    ir.emit(number);
    ir.emitLabel(before);
    // if false then jump
    CallExprIR callExprIR1 = new CallExprIR("get", expr, for_id, new ArrayList<ExpressionNode>() {{
      add(t);
    }});
    callExprIR1.setDebugInfo(for_expr.getLineNumber(),getSourceCode());
    ir.emit(callExprIR1);

    LogicExpression condition = new LogicExpression(t, len, "<");
    ExpressionNode condition_reduced = condition.gen(0, 0);
    JumpIfNotTrueIR jumpIfNotTrueIR = new JumpIfNotTrueIR(new Value(condition_reduced), ir.getLabel(after));
    jumpIfNotTrueIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    ir.emit(jumpIfNotTrueIR);

    ir.emit(StackOperationIR.PushStack());
    // execuete
    statements.gen(before, after);

    ir.emitLabel(after);
    ir.emit(StackOperationIR.PopStack());

    // +1 and jump
    ArithmeticExpression b = new ArithmeticExpression(t, new Literal("1", new SimpleType("Number")), "+");
    ExpressionNode b_reduced = b.gen(0, 0);

    AssignIR assignIR = new AssignIR(t, b_reduced);
    assignIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    ir.emit(assignIR);

    JumpIR jumpIR = new JumpIR(ir.getLabel(before));
    jumpIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    ir.emit(jumpIR);

    ir.emit(StackOperationIR.PopStack());
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(for_id);
      add(for_expr);
      add(statements);
    }};
  }
}


