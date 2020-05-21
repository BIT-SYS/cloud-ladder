package interpreter;

import ir.ReturnIR;
import ir.Value;

import java.util.List;

public class ExternalProcedureTemplate extends ProcSignature {
  public ExternalProcedureTemplate(String name, List<Value> args) {
    super(Value.Procedure(name), args, new ReturnIR(), true);
  }
}
