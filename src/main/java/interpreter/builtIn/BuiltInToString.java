package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import util.Type;

import java.util.ArrayList;

public class BuiltInToString extends ExternalProcedureTemplate {
  public BuiltInToString() {
    super("toString", new ArrayList<Value>() {{
      add(Value.Symbol("v", Type.getType("Number")));
    }});
  }

  public BuiltInToString(String type_str) {
    super("toString", new ArrayList<Value>() {{
      add(Value.Symbol("v", Type.getType(type_str)));
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
  interpreter.Value v = context.current_scope.resolve("v");
  return interpreter.Value.valueOf(v.getValueString());
  }
}
