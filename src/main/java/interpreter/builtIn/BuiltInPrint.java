package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;
import util.Type;

import java.util.ArrayList;

public class BuiltInPrint extends ExternalProcedureTemplate {
  public BuiltInPrint() {
    super("print", new ArrayList<Value>() {{
      add(Value.Symbol("v", new SimpleType("String")));
    }});
  }

  public BuiltInPrint(String type_str) {
    super("print", new ArrayList<Value>() {{
      add(Value.Symbol("v", Type.getType(type_str)));
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
    interpreter.Value v = context.current_scope.resolve("v");
    System.out.println(v.getValueString());
    return interpreter.Value.valueOf(v.getValueString());
  }
}
