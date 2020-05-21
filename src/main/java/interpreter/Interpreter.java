package interpreter;

import interpreter.builtIn.*;
import interpreter.builtIn.image.*;
import ir.*;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {

  public static boolean debug = true;

  boolean lazy_execution = false;

  public Scope current_scope;
  Stack current_stack;
  CallStack call_stack;

  void prepare() {
    current_scope = new Scope();
    current_stack = new Stack();
    call_stack = new CallStack();
  }

  // resolve args.
  // turn symbols into value.
  Value resolve(ir.Value v) {
    if (v.is_symbol && !v.is_temp) {
      // e.g. num
      return current_scope.resolve(v.value);
    } else if (v.is_temp) {
      // e.g. @t1
      // should be read from stack
      return current_stack.pop();
    } else {
      return v.toInterpreterValue();
    }
  }

  void resolveResult(ir.Value v, Value result) {
    if (v.is_temp) {
      // temp value, save to stack
      current_stack.push(result);

    } else if (v.is_symbol) {
      // update value
      current_scope.update(v.value, result);
    } else {
      // error
      throw new RuntimeException();
    }
  }

  // resolve type, won't change context.
  symboltable.Type resolveType(ir.Value v) {
    if (v.is_symbol && v.is_temp) {
      return current_stack.peek().type;
    } else if (v.is_symbol) {
      return current_scope.resolve(v.value).type;
    } else {
      // should already have type information
      return v.type;
    }
  }

  // save frame, create new frame then jump.
  IRNodeInterface call(ProcSignature dest_proc, ProcSignature src_proc, ir.Value lvalue, IRNodeInterface next_ir) {
    // TODO
    current_scope = new Scope(current_scope);

    for (int i = dest_proc.args.size() - 1; 0 <= i; i--) {
      current_scope.insert(dest_proc.args.get(i).value.toString(), resolve(src_proc.args.get(i)));
    }
    call_stack.push(next_ir, lvalue);
    if (dest_proc.external) {
      // this is where *external* call actually happen
      Value ret = dest_proc.external(this);
      if (ret != null) {
        current_stack.push(ret);
      }
    }
    return dest_proc.next_ir;
  }

  IRNodeInterface ret() {
    CallStack.CallFrame cf = call_stack.pop();

    if (cf.result != null) {
      Value ret_val = current_stack.pop();
      current_scope = current_scope.prev_scope;
      resolveResult(cf.result, ret_val);
    } else {
      current_scope = current_scope.prev_scope;
    }
    return cf.next_ir;
  }


  public Interpreter() {
    prepare();
    injection_external();
    current_scope = new Scope(current_scope);
    current_scope.printAllScope();
  }

  void injection_external() {
    List<ProcSignature> ps = new ArrayList<ProcSignature>() {{
      add(new BuiltInToString());
      add(new BuiltInPrint());
      add(new BuiltInInput());
      add(new BuiltInImRead());
      add(new BuiltInGetString()); //todo 需要区分么？
      add(new BuiltInSize());
      add(new BuiltInGet());
      add(new BuiltInGetAnime());
      add(new BuiltInSave());
      add(new BuiltInUseBaiduForImage());
    }};
    ps.forEach(p -> current_scope.insert(p, new Value(p)));
  }

  public Object execute(IR ir_list) {
    if (debug)
      System.out.println("+++++++++ start interpret +++++++++");
    IRNodeInterface current_ir = ir_list.getFirst();

    while (current_ir != null) {
      if (debug) {
        System.out.println("=========================");
        System.out.print(current_ir);
        System.out.println(current_ir.toStringAfterHook());
      }
      if (lazy_execution) {
        if (current_ir.getOp() == IROperator.LazyExecutionEnd)
          lazy_execution = false;
        else if (debug)
          System.out.println("Because of lazy execution, we skip.");
      } else {
        switch (current_ir.getOp()) {
          case Assign:
            AssignIR assignIR = (AssignIR) current_ir;
            resolveResult(assignIR.getLValue(), resolve(assignIR.getRValue()));
            break;
          case VariableDeclaration:
            VariableDeclarationIR variableDeclarationIR = (VariableDeclarationIR) current_ir;
            Value v_vdir = resolve(variableDeclarationIR.getValue());
            current_scope.insert(variableDeclarationIR.getResult().value, v_vdir);
            break;
          case LazyExecutionStart:
            LazyExecutionStartIR lazyExecutionStartIR = (LazyExecutionStartIR) current_ir;
            ProcSignature procSignature = new ProcSignature(lazyExecutionStartIR.getResult(), lazyExecutionStartIR.getParameters(), lazyExecutionStartIR.getNext());
            Value v_les = new Value(procSignature);
            if (lazyExecutionStartIR.getResult().is_temp) {
//              current_stack.push(v_les);
              resolveResult(lazyExecutionStartIR.getResult(), v_les);
            } else {
              current_scope.insert(procSignature, v_les);
            }

            lazy_execution = true;
            break;
          case LazyExecutionEnd:
            // see code above
            break;
          case AddExpr:
          case SubExpr:
          case MulExpr:
          case DivExpr:
          case AndExpr:
          case OrExpr:
          case XorExpr:
          case ModExpr:
          case EqualExpr:
          case NotEqualExpr:
          case LessThanExpr:
          case GreaterThanExpr:
          case LessEqualThanExpr:
          case GreaterEqualThanExpr:
            BinaryExprIR binaryExprIR = (BinaryExprIR) current_ir;
            Value left_v = resolve(binaryExprIR.arg1);
            Value right_v = resolve(binaryExprIR.arg2);
            Value result;

            switch (binaryExprIR.getOp()) {
              case AddExpr:
                result = left_v.add(right_v, this);
                resolveResult(binaryExprIR.result, result);
                break;
              case SubExpr:
                result = left_v.sub(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case MulExpr:
                result = left_v.mul(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case DivExpr:
                result = left_v.div(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case AndExpr:
                break;
              case OrExpr:
                break;
              case XorExpr:
                break;
              case ModExpr:
                result = left_v.mod(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case EqualExpr:
                result = left_v.equal(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case NotEqualExpr:
                break;
              case LessThanExpr:
                result = left_v.lessThan(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case GreaterThanExpr:
                result = left_v.greaterThan(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case LessEqualThanExpr:
                result = left_v.lessEqualThan(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
              case GreaterEqualThanExpr:
                result = left_v.greaterEqualThan(right_v);
                resolveResult(binaryExprIR.result, result);
                break;
            }

            break;
          case NoOperation:
            break;
          case CallExpr:
            current_scope.printAllScope();
            CallExprIR callExprIR = (CallExprIR) current_ir;
            // copy list to avoid overwrite
            List<ir.Value> args = new ArrayList<>(callExprIR.args);
            if (callExprIR.caller != null) {
              args.add(0, callExprIR.caller);
            }
            // TODO 多个临时值会有冲突
            args.forEach(v -> {
              if (v.type == null) {
                v.type = resolveType(v);
              }
            });
            ProcSignature procSignature_index = new ProcSignature(callExprIR.callee, args, null);
            ProcSignature dest_proc = (ProcSignature) current_scope.resolve(procSignature_index).value;

            current_ir = call(dest_proc, procSignature_index, callExprIR.result, current_ir.getNext());
            continue;
          case Return:
            current_ir = ret();
            printDebugInfo();
            continue;
          case BuildList:
            BuildListIR buildListIR = (BuildListIR) current_ir;
            // little bit tricky, we just using `type` field, omitting `value`
            Value a = Value.newArray(buildListIR.type);
            if (buildListIR.has_range()) {
              // need to insert from start to end
              Integer start = Math.round(resolve(buildListIR.start).getFloat());
              Integer end = Math.round(resolve(buildListIR.end).getFloat());
              List<Value> ar = (List<Value>) a.value;
              for (int i = start; i < end; i++) {
                ar.add(Value.valueOf(i));
              }
            } else if (buildListIR.has_values()) {
              List<Value> ar = (List<Value>) a.value;
              for (int i = buildListIR.values.size()-1;i>=0;i--) {
                ir.Value v = buildListIR.values.get(i);
                ar.add(0, resolve(v));
              }
            } else {
              System.err.println("BuildListIR has error");
            }
            resolveResult(buildListIR.result, a);
            break;
          case JumpIfNotTrue:
            JumpIfNotTrueIR jumpIfNotTrueIR = (JumpIfNotTrueIR) current_ir;
            Value condition = resolve(jumpIfNotTrueIR.condition);
            if (condition.getBoolean()) {
              // execute sequentially
            } else {
              // jump
              current_ir = jumpIfNotTrueIR.to.iRNode;
              printDebugInfo();
              continue;
            }
            break;
          case PushStack:
            current_scope = new Scope(current_scope);
            break;
          case PopStack:
            StackOperationIR stackOperationIR = (StackOperationIR) current_ir;
            int depth = stackOperationIR.depth;
            while (depth > 0) {
              current_scope = current_scope.prev_scope;
              depth -=1;
            }
            break;
          case Jump:
            System.out.println("==Jump==");
            JumpIR jumpIR = (JumpIR) current_ir;
            System.out.println(jumpIR.to.iRNode.toStringAfterHook());
            System.out.println("==Jump==");

            current_ir = jumpIR.to.iRNode;
            printDebugInfo();
            continue;
          case Break:
            break;
          case Continue:
            break;
        }
      }
      printDebugInfo();
      current_ir = current_ir.getNext();
    }
    if (current_stack.stack.size() > 0) {
      return current_stack.pop();
    } else {
      return null;
    }

  }

  void printDebugInfo() {
    if (!debug)
      return;
    System.out.printf(current_scope.toString() + "\n");
    System.out.printf(current_stack.toString() + "\n");
  }

}
