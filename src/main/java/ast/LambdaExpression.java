package ast;

import ir.LazyExecutionEndIR;
import ir.LazyExecutionStartIR;
import ir.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaExpression extends ExpressionNode {

  public ParameterList parameters;
  public String retType;
  public Block body;

  @Override
  public ExpressionNode reduce() {
    // TODO retType ???
    Temp t = new Temp();
    LazyExecutionStartIR lazyExecutionStartIR = new LazyExecutionStartIR(t, this.evalType, parameters.parameters.stream().map(Value::new).collect(Collectors.toList()));
    Utils.setDebugInfo(lazyExecutionStartIR,this);
    ir.emit(lazyExecutionStartIR);
    int before = newLabel();
    int after = newLabel();
    ir.emitLabel(before);

    body.gen(before, after);

    ir.emitLabel(after);

    LazyExecutionEndIR lazyExecutionEndIR = new LazyExecutionEndIR();
    Utils.setDebugInfo(lazyExecutionEndIR, this);
    ir.emit(lazyExecutionEndIR);
    return t;
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
        add(parameters);
        add(body);
      }
    };
  }
}
