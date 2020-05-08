package ir;

public class Label {
  public int i;
  public IRNodeInterface iRNode;

  Label(int i) {
    this.i = i;
  }

  @Override
  public String toString() {
    return "L" + Integer.toString(i);
  }
}
