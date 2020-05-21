package ir;


import java.util.List;

public abstract class IRNode implements IRNodeInterface {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_BLUE = "\u001B[34m";

  public List<Label> labels;
  IRNodeInterface next;
  public Integer lineNumber;
  public String sourceCode;

  public String toStringBeforeHook() {
    return String.format("%s%s%s", ANSI_BLUE, labels, ANSI_RESET);
  }

  public String toStringAfterHook() {
    if (lineNumber == null && sourceCode == null) {
      return "";
    } else {
      return String.format("\t%s\\\\%d %s%s", ANSI_CYAN, lineNumber, sourceCode, ANSI_RESET);
    }
  }

  IRNode() {

  }

  IRNode(Integer lineNumber, String sourceCode) {
    setDebugInfo(lineNumber, sourceCode);
  }

  public void setDebugInfo(Integer lineNumber, String sourceCode) {
    this.lineNumber = lineNumber;
    this.sourceCode = sourceCode;
  }

  @Override
  public void register() {
  }

  @Override
  public IRNodeInterface getNext() {
    return next;
  }

  public void setNext(IRNode next) {
    this.next = next;
  }

}
