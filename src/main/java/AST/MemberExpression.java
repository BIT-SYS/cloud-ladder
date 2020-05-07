package AST;

import java.util.ArrayList;
import java.util.List;

public class MemberExpression extends ExpressionNode {
  // TODO 求值顺序
  public ExpressionNode object;
  public ExpressionNode property;

  @Override
  public ExpressionNode reduce() {
    ExpressionNode object_reduced = object.reduce();
    Temp temp = new Temp();
    // TODO call
    if (property instanceof CallExpression) {
      CallExpression proc = (CallExpression) property;
      proc.reduce(object_reduced);
//      CallExprIR ceir = new CallExprIR(proc.callee.toString(), object_reduced, temp,
//              proc.arguments.stream().map(e -> (Object) e).collect(Collectors.toList()));
//      ir.emit(ceir);
    }
    return temp;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return gen();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {
      {
        add(object);
        add(property);
      }
    };
  }
}
