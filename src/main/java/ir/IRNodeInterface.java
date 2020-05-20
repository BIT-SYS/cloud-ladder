package ir;

import org.antlr.v4.runtime.ParserRuleContext;


public interface IRNodeInterface {

  public void register();
  public IROperator getOp();
  public IRNodeInterface getNext();
  public String toStringBeforeHook();
  public String toStringAfterHook();
}
