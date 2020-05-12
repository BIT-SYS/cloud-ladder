package ast;

import symboltable.SimpleType;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class Literal extends ExpressionNode {

  public String raw;

  Literal(String raw, Type type) {
    if (type.equals(new SimpleType("String"))) {
      raw = raw.substring(1, raw.length() - 1);
    }
    this.raw = raw;
    evalType = type;
  }

  @Override
  public ExpressionNode gen() {
    return this;
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return this;
  }

  @Override
  public String toString() {
    return raw;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}
