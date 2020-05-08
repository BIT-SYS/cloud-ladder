package Interpreter;

import IR.*;

public class Interpreter {

  public void execute(IR ir_list) {
    IRNodeInterface current_ir = ir_list.getFirst();

    while(current_ir != null) {
      switch (current_ir.getOp()){
        case Assign:
          break;
        case VariableDeclaration:
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
