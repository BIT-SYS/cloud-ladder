package ast;

import grammar.CLParserParser;

import java.util.ArrayList;
import java.util.List;

public class Identifier extends ExpressionNode {
  public String name;
  public boolean isProc = false;

  Identifier(CLParserParser.IdContext ctx) {
    name = ctx.getText();
  }

  Identifier(String name) {
    this.name = name;
  }

  @Override
  public ExpressionNode gen() {
    return this;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return gen();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}
