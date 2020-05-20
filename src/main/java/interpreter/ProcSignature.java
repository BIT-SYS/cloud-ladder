package interpreter;

import ir.IRNodeInterface;
import ir.Value;

import java.util.List;
import java.util.stream.Collectors;

public class ProcSignature {

  Value proc_name;
  List<Value> args;
  IRNodeInterface next_ir;
  Boolean external;

  public IRNodeInterface getNextIR() {
    return next_ir;
  }

  public interpreter.Value external(Interpreter context) {
    System.err.println("you should override this function");
    return null;
  }

  public ProcSignature(Value name, List<Value> args, IRNodeInterface next_ir) {
    this(name, args, next_ir, false);
  }

  public ProcSignature(Value name, List<Value> args, IRNodeInterface next_ir, Boolean external) {
    this.proc_name = name;
    this.args = args;
    this.next_ir = next_ir;
    this.external = external;
  }

  public String getSignature() {
    String _v = args.stream().map(v -> v.type.toString()).collect(Collectors.joining(","));
    return String.format("%s[%s]", proc_name, _v);
  }

  public List<Value> getArgs() {
    return args;
  }

  public String getName() {
    return proc_name.value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    ProcSignature that = (ProcSignature) obj;
    if (this.proc_name.equals(that.proc_name))
      return false;
    if (this.args.size() != that.args.size())
      return false;
    for (int i = 0; i < this.args.size(); i++) {
      if (!this.args.get(i).equals(that.args.get(i)))
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return proc_name +
            "(" + args +
            ')';
  }
}
