package ast;

import symboltable.SimpleType;

import java.util.ArrayList;
import java.util.List;

public class ElseBlock extends Node implements HaveConditionAndBlock {
  public Block statements;

  ElseBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    statements.gen(0, after);
    return null;
  }

  @Override
  List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(statements);
    }};
  }

  @Override
  public ExpressionNode getCondition() {
    return new Literal("true", new SimpleType("Boolean"));
  }

  @Override
  public Block getBlock() {
    return statements;
  }
}
