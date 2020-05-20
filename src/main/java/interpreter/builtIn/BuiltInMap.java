package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import interpreter.ProcSignature;
import ir.Value;

import java.util.ArrayList;
import java.util.List;

public class BuiltInMap extends ExternalProcedureTemplate {
  public BuiltInMap() {

    super("map", new ArrayList<Value>() {{
      add(Value.Symbol("l", "List<Number>"));
      add(
              Value.Procedure("func")
    );
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
    interpreter.Value l = context.current_scope.resolve("l");
    interpreter.Value f = context.current_scope.resolve("func");

    int i = 0;
    return super.external(context);
  }
}
