package ir;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class QuadrupleNode extends IRNode {
  public Value arg1;
  public Value arg2;
  public Value result;

  QuadrupleNode() {
  }

  QuadrupleNode(Value arg1, Value arg2, Value result) {
    this(arg1,arg2,result,0, null);
  }
  QuadrupleNode(Value arg1, Value arg2, Value result, int lineNumber, String sourceCode) {
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.result = result;
    setDebugInfo(lineNumber, sourceCode);
  }

  @Override
  public String toString() {
    return String.format("%s = %s %s",result, arg1, arg2);
  }
}
