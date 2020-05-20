package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;

import java.util.ArrayList;
import java.util.List;

public class BuiltInSize extends ExternalProcedureTemplate {

  public BuiltInSize() {
    super("size", new ArrayList
            <Value>(){{
      add(Value.Symbol("l", util.Type.getType("List<TypeA>")));
    }});
  }

  @Override
  public interpreter.Value external(Interpreter context) {
    interpreter.Value l = context.current_scope.resolve("l");
    List<interpreter.Value> _l= l.getList();
    return interpreter.Value.valueOf(_l.size());
  }
}
