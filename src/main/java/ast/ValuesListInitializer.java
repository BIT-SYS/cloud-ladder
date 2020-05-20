package ast;

import ir.AssignIR;
import ir.BuildListIR;

import java.util.List;
import java.util.stream.Collectors;

public class ValuesListInitializer extends ExpressionNode {
  public List<ExpressionNode> values;

  @Override
  public ExpressionNode reduce() {
    Temp t = new Temp();
    BuildListIR buildListIR = new BuildListIR(t, evalType, values.stream().map(ExpressionNode::reduce).collect(Collectors.toList()));
    Utils.setDebugInfo(buildListIR, this);
    ir.emit(buildListIR);
    return t;
  }

  @Override
  public ExpressionNode gen() {
    return this.reduce();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return this.gen();
  }

  @Override
  public List<Node> getChildren() {
    return values.stream().map(en -> (Node) en).collect(Collectors.toList());
  }
}
