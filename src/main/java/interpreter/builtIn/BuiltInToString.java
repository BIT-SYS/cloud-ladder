package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;

import java.util.ArrayList;

public class BuiltInToString extends ExternalProcedureTemplate {
  public BuiltInToString() {
    super("toString", new ArrayList<Value>() {{
      add(Value.Symbol("v", new SimpleType("Number")));
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
  interpreter.Value v = context.current_scope.resolve("v");
  return interpreter.Value.valueOf(v.getFloat().toString());
  }
}
