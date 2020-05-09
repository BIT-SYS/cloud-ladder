package interpreter;

import ir.*;

public class Interpreter {

  Scope current_scope;
  Stack current_stack;

  void prepare() {
    current_scope = new Scope();
    current_stack = new Stack();
  }

  public Interpreter() {
    prepare();
  }

  public void execute(IR ir_list) {
    System.out.println("+++++++++ start interpret +++++++++");
    IRNodeInterface current_ir = ir_list.getFirst();

    while(current_ir != null) {
      System.out.println(current_ir);
      switch (current_ir.getOp()){
        case Assign:
          break;
        case VariableDeclaration:
          VariableDeclarationIR ir = (VariableDeclarationIR) current_ir;
          break;
        case LazyExecutionStart:
          break;
        case LazyExecutionEnd:
          break;
        case AddExpr:
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
        case NoOperation:
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
        case CallExpr:
          break;
        case BuildList:
          break;
        case JumpIfNotTrue:
          break;
        case Jump:
          break;
        case Break:
          break;
        case Continue:
          break;
      }
      current_ir = current_ir.getNext();
    }

  }
}
