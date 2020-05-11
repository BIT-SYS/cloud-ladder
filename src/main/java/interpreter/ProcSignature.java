package interpreter;

import ir.IRNodeInterface;
import ir.Value;

import java.util.List;
import java.util.stream.Collectors;

public class ProcSignature {

  String proc_name;
  List<Value> args;
  IRNodeInterface next_ir;

  public IRNodeInterface getNextIR() {
    return next_ir;
  }

  ProcSignature(String name, List<Value> args, IRNodeInterface next_ir){
    this.proc_name = name;
    this.args = args;
    this.next_ir = next_ir;
  }

  public String getSignature() {
    String _v = args.stream().map(v -> v.type.toString()).collect(Collectors.joining(","));
    return String.format("%s[%s]", proc_name,_v);
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
    for (int i=0; i< this.args.size(); i++) {
      if (!this.args.get(i).equals(that.args.get(i)))
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ProcSignature{" +
            "proc_name='" + proc_name + '\'' +
            ", args=" + args +
            ", next_ir=" + next_ir +
            '}';
  }
}
