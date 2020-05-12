package ast;

import ir.LazyExecutionEndIR;
import ir.LazyExecutionStartIR;
import ir.ReturnIR;
import ir.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProcedureDefinition extends Node {
  public ParameterList parameters;
  public String returnType;
  // function name
  public Identifier id;
  public Block body;

  @Override
  public ExpressionNode gen(int before, int after) {
    ir.emit(new LazyExecutionStartIR(id.toString(), this.evalType, parameters.parameters.stream().map(Value::new).collect(Collectors.toList())));

    body.gen(0, 0);

    ir.emit(new ReturnIR());
    ir.emit(new LazyExecutionEndIR());
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(parameters);
      add(body);
    }};
  }
}
