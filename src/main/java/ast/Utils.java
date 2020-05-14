package ast;

import ir.IRNode;

public class Utils {
  static void setDebugInfo(IRNode i, Node n) {
    i.setDebugInfo(n.getLineNumber(),n.getSourceCode());
  }
}
