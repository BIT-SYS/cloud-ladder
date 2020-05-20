package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;

import java.util.ArrayList;

public class BuiltInPrint extends ExternalProcedureTemplate {
  public BuiltInPrint() {
    super("print", new ArrayList<Value>() {{
      add(Value.Symbol("v", util.Type.getType("TypeA")));
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
    interpreter.Value v = context.current_scope.resolve("v");
    System.out.println("\u001b[32m" + v.getValueString()+"\u001b[0m");
    return interpreter.Value.valueOf(v.getValueString());
  }
}
