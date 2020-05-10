package interpreter;

import ir.*;

public class Interpreter {

  public static boolean debug = true;

  Scope current_scope;
  Stack current_stack;

  void prepare() {
    current_scope = new Scope();
    current_stack = new Stack();
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

  public Interpreter() {
    prepare();
  }

  public Object execute(IR ir_list) {
    if (debug)
      System.out.println("+++++++++ start interpret +++++++++");
    IRNodeInterface current_ir = ir_list.getFirst();

    while (current_ir != null) {
      if (debug) {
        System.out.println(current_ir);
      }
      switch (current_ir.getOp()) {
        case Assign:
          AssignIR assignIR = (AssignIR) current_ir;
          resolveResult(assignIR.getLValue(), resolve(assignIR.getRValue()));
          break;
        case VariableDeclaration:
          VariableDeclarationIR variableDeclarationIR = (VariableDeclarationIR) current_ir;
          Value v = resolve(variableDeclarationIR.getValue());
          current_scope.insert(variableDeclarationIR.getResult().value, v);
          break;
        case LazyExecutionStart:
          break;
        case LazyExecutionEnd:
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
              break;
            case MulExpr:
              break;
            case DivExpr:
              break;
            case AndExpr:
              break;
            case OrExpr:
              break;
            case XorExpr:
              break;
            case ModExpr:
              break;
            case EqualExpr:
              break;
            case NotEqualExpr:
              break;
            case LessThanExpr:
              break;
            case GreaterThanExpr:
              break;
            case LessEqualThanExpr:
              break;
            case GreaterEqualThanExpr:
              break;
          }

          break;
        case NoOperation:
          break;
        case CallExpr:
          break;
        case BuildList:
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
          current_scope = current_scope.prev_scope;
          break;
        case Jump:
          break;
        case Break:
          break;
        case Continue:
          break;
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
    System.out.println("=========================");
  }

}
