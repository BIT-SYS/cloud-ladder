package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scope {

  int depth = 0;
  Scope prev_scope;

  // Name: value
  HashMap<String, Value> scope;
  HashMap<String, List<Value>> procScope;

  public Scope() {
    scope = new HashMap<>();
    procScope = new HashMap<>();
  }

  public Scope(Scope prev_scope) {
    scope = new HashMap<>();
    procScope = new HashMap<>();
    this.prev_scope = prev_scope;
    depth = prev_scope.depth + 1;
  }

  Value _resolve(String name) {
    Value result = scope.get(name);
    if (result == null && prev_scope != null) {
      result = prev_scope._resolve(name);
    }
    return result;
  }

  // recursively find Symbol's value
  public Value resolve(String name) {
    Value result = _resolve(name);
    if (result == null) {
      System.err.println(String.format("Variable not found: %s", name));
    }
    // TODO: return a copy?
    return result;
  }

  List<Value> _resolveProc(String name) {
    List<Value> result = procScope.get(name);
    if (result == null && prev_scope != null) {
      result = prev_scope._resolveProc(name);
    }
    return result;
  }

  // recursively find Symbol's value, procedures only.
  public Value resolve(ProcSignature proc) {
    // might be `null`
    String proc_name = proc.proc_name.value;
    List<Value> results = _resolveProc(proc_name);
    if (results == null) {
      System.err.println(String.format("Procedure not found: %s", proc));
      return null;
    } else {
      // compare each method
      for (Value n : results) {
        ProcSignature v = (ProcSignature) n.value;
        if (v.args.size() == proc.args.size()) {
          boolean allMatch = true;
          for (int i = 0; i < v.args.size(); i++) {
            ir.Value arg1 = v.args.get(i);
            ir.Value arg2 = proc.args.get(i);
            // 注意顺序
            if (!util.Type.sameParameterType(arg2.type, arg1.type)) {
              allMatch = false;
              break;
            }
          }
          if (allMatch) {
            // fond a valid method.
            return n;
          }
        }
      }
    }
    System.err.println(String.format("Procedure not found: %s", proc));
    return null;
  }

  public void insert(ProcSignature proc, Value value) {
    String procName = proc.proc_name.value;
    if (procScope.containsKey(procName)) {
      List<Value> values = procScope.get(procName);
      values.add(value);
    } else {
      ArrayList<Value> values = new ArrayList<Value>() {{
        add(value);
      }};
      procScope.put(procName, values);
    }
  }

  public void insert(String name, Value value) {
    scope.put(name, value);
  }

  // return: if update success.
  // TODO: update must be successful?
  public Boolean update(String name, Value value) {
    if (scope.containsKey(name)) {
      scope.put(name, value);
    } else {
      prev_scope.update(name, value);
    }
    return true;
  }

  @Override
  public String toString() {
    return String.format("%s %s\n%s %s", depth, scope,depth, procScope );
  }


  public void printAllScope() {
    if (!Interpreter.debug) return;
    if(prev_scope == null) {
      System.out.println(String.format("%d V:%s P:%s", depth, scope, procScope));
    }
    else {
      prev_scope.printAllScope();
      System.out.println(String.format("%d V:%s P:%s", depth, scope, procScope));
    }
  }
}
