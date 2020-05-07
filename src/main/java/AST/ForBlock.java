package AST;

import IR.*;
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
    // get expr len
    ir.emit(new CallExprIR("size", expr , len, new ArrayList<>()));

    ir.emit(new VariableDeclarationIR(new Value(t), Value.Literal("0", new SimpleType("Number"))));
    ir.emitLabel(before);
    // if false then jump
    ir.emit(new CallExprIR("get", expr, for_id, new ArrayList<ExpressionNode>() {{
      add(t);
    }}));
    LogicExpression condition = new LogicExpression(t, len, "<");
    ExpressionNode condition_reduced = condition.gen(0, 0);
    ir.emit(new JumpIfNotTrueIR(new Value(condition_reduced), ir.getLabel(after)));

    // execuete
    statements.gen(before, after);


    // +1 and jump
    ArithmeticExpression b = new ArithmeticExpression(t, new Literal("1", new SimpleType("Number")), "+");
    ExpressionNode b_reduced = b.gen(0, 0);
    ir.emit(new AssignIR(t, b_reduced));
    ir.emit(new JumpIR(ir.getLabel(before)));
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


