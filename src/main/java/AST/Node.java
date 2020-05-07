package AST;

import IR.IR;
import symboltable.Scope;
import symboltable.Symbol;
import symboltable.Type;

import java.util.List;

/**
 * Base AST.Node.
 */
//interface AST.Node {
//  List<AST.Node> getChildren();
//}

public abstract class Node {
  // AST.ScopePointer
  public Scope scope;
  public Symbol symbol;
  public Type evalType;
  static public IR ir;

  //
  public int newLabel() {
    return ++Label.label;
  }


  void emit(String s) {
    System.out.println("\t" + s);
  }

  public ExpressionNode gen(int before, int after) {
    System.out.println(this.getClass().getName());
    return null;
  }

  abstract List<Node> getChildren();
}
