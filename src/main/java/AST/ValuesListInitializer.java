package AST;

import java.util.List;
import java.util.stream.Collectors;

public class ValuesListInitializer extends ExpressionNode {
  public List<ExpressionNode> values;

  @Override
  public List<Node> getChildren() {
    return values.stream().map(en -> (Node) en).collect(Collectors.toList());
  }
}
