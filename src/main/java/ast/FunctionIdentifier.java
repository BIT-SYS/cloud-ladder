package ast;

import grammar.CLParserParser;

import java.util.ArrayList;
import java.util.List;

public class FunctionIdentifier extends ExpressionNode {

  public String name;

  FunctionIdentifier(CLParserParser.IdContext ctx) {
    name = ctx.getText();
  }

  FunctionIdentifier(String name) {
    this.name = name;
  }

  @Override
  public ExpressionNode reduce() {
    return this;
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
