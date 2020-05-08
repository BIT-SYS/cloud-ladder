package AST;

import java.util.ArrayList;
import java.util.List;

public class ParameterList extends Node {
  public List<Parameter> parameters;

  ParameterList(List<Parameter> ps) {
    this.parameters = ps;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(parameters);
  }
}
