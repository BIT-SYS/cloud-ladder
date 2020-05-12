package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;

import java.util.ArrayList;
import java.util.List;

public class BuiltInPrint extends ExternalProcedureTemplate {
  public BuiltInPrint() {
    super("print", new ArrayList<Value>() {{
      add(Value.Symbol("v", new SimpleType("String")));
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
    interpreter.Value v = context.current_scope.resolve("v");
    System.out.println(v.getString());
    return interpreter.Value.valueOf(v.getString());
  }
}
