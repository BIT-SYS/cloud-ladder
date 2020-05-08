package AST;

import IR.IR;

import java.util.ArrayList;
import java.util.List;

public class Program extends Node {
  public Block statements;

  Program(Block bl) {
    statements = bl;
    ir = new IR();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    statements.gen(0, 0);
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(statements);
    }};
  }
}
