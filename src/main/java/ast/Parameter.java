package ast;

import java.util.ArrayList;
import java.util.List;

public class Parameter extends ExpressionNode {
  public String type;
  public Identifier id;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(id);
    }};
  }

  @Override
  public String toString() {
    return String.format("%s %s", type, id.toString());
  }
}
