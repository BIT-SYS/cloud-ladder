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
    int afterAll = newLabel();
    ExpressionNode expr = for_expr.gen(0, 0);
    Identifier list = new Identifier("@for");
    Identifier i = new Identifier("@i");
    Temp len = new Temp();
    // @for = List[...]
    ir.emit(new VariableDeclarationIR(new Value(list), new Value(expr)));

    // push stack
    ir.emit(StackOperationIR.PushStack("special"));

    VariableDeclarationIR number = new VariableDeclarationIR(new Value(i), Value.Literal("0", new SimpleType("Number")));
    number.setDebugInfo(ctx.start.getLine(), this.getSourceCodeFirstLine());
    // @i = 0
    ir.emit(number);
    ir.emitLabel(before);

    CallExprIR callExprIR = new CallExprIR(new FunctionIdentifier("size"), list, len, new ArrayList<>());
    callExprIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    // temp_len = @for.size()
    ir.emit(callExprIR);

    LogicExpression condition = new LogicExpression(i, len, "<");
    condition.ctx = this.ctx;
    ExpressionNode condition_reduced = condition.gen(0, 0);
    JumpIfNotTrueIR jumpIfNotTrueIR = new JumpIfNotTrueIR(new Value(condition_reduced), ir.getLabel(after));
    jumpIfNotTrueIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    // if i < temp_len exit loop
    ir.emit(jumpIfNotTrueIR);

    // if false then jump
    CallExprIR callExprIR1 = new CallExprIR(new FunctionIdentifier("get"), list, for_id, new ArrayList<ExpressionNode>() {{
      add(i);
    }});
    callExprIR1.setDebugInfo(for_expr.getLineNumber(),getSourceCode());
    // x = @for.get(@i)
    ir.emit(callExprIR1);

    // push stack
    ir.emit(StackOperationIR.PushStack());
    // execuete
    statements.gen(before, afterAll);

    // pop stack
    ir.emit(StackOperationIR.PopStack());

    ArithmeticExpression b = new ArithmeticExpression(i, new Literal("1", new SimpleType("Number")), "+");
    b.ctx = this.ctx;
    ExpressionNode b_reduced = b.gen(0, 0);

    AssignIR assignIR = new AssignIR(i, b_reduced);
    assignIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    // @i = @i + 1
    ir.emit(assignIR);

    JumpIR jumpIR = new JumpIR(ir.getLabel(before));
    jumpIR.setDebugInfo(for_expr.getLineNumber(),for_expr.getSourceCode());
    // jump back
    ir.emit(jumpIR);

    ir.emitLabel(after);
    // pop stack
    ir.emit(StackOperationIR.PopStack());
    ir.emitLabel(afterAll);
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


