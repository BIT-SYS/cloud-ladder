package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;

import java.util.ArrayList;
import java.util.List;

public class BuiltInGet extends ExternalProcedureTemplate {
  public BuiltInGet() {
    super("get", new ArrayList
            <Value>(){{
      add(Value.Symbol("self", util.Type.getType("List<TypeA>")));
      add(Value.Symbol("index", util.Type.getType("Number")));
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
    interpreter.Value l = context.current_scope.resolve("self");
    interpreter.Value i = context.current_scope.resolve("index");
    int index = Math.round(i.getFloat());
    List<interpreter.Value> _l= l.getList();
    return _l.get(index);
  }
}
