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
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(statements);
    }};
  }

  @Override
  public ExpressionNode getCondition() {
    Literal literal = new Literal("true", new SimpleType("Boolean"));
    literal.ctx = this.ctx;
    return literal;
  }

  @Override
  public Block getBlock() {
    return statements;
  }
}
