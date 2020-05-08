package ast;

import java.util.ArrayList;
import java.util.List;

// so what's the difference between AST.Block and AST.Program
public class Block extends Node {

  public List<Node> statements;

  Block() {
    statements = new ArrayList<>();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    for (Node statement : statements) {
      statement.gen(before, after);
    }
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(statements);
  }
}
