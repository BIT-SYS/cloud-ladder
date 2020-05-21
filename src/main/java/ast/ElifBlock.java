package ast;

import java.util.ArrayList;
import java.util.List;

public class ElifBlock extends Node implements HaveConditionAndBlock {
  public ExpressionNode condition;
  public Block statements;

  ElifBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {
      {
        add(condition);
        add(statements);
      }
    };

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
