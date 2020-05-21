package ir;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class TripleNode extends IRNode {
  public Value arg1;
  public Value arg2;


  @Override
  public String toString() {
    return String.format("%s %s %s", getOp(), arg1, arg2);
  }

  public TripleNode(Value arg1, Value arg2) {
    this.arg1 = arg1;
    this.arg2 = arg2;
  }
  public TripleNode(Value arg1, Value arg2, int lineNumber, String sourceCode) {
    this.arg1 = arg1;
    this.arg2 = arg2;
    setDebugInfo(lineNumber,sourceCode);
  }
}
