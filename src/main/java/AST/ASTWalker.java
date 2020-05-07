package AST;

import java.lang.reflect.Method;

public class ASTWalker {
  public void walk(ASTBaseListener astListener, Node node) {
//    AST.ASTParser.printMethodInfo(node);
    this.enterRule(astListener, node);
    try {

      node.getChildren().forEach(
              n -> {
                if (n == null) {
                  return;
                }
                walk(astListener, n);
              }
      );
    } catch (NullPointerException e) {

      e.printStackTrace();
      System.out.println(node.getClass().getName());
      System.exit(1);
    }
    this.exitRule(astListener, node);
  }

  protected void enterRule(ASTBaseListener astListener, Node node) {

    Method enterMethod = this.getMethod("enter", astListener, node);
    try {
      enterMethod.invoke(astListener, node);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  protected void exitRule(ASTBaseListener astListener, Node node) {
    Method enterMethod = this.getMethod("exit", astListener, node);
    try {
      enterMethod.invoke(astListener, node);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private Method getMethod(String prefix, ASTBaseListener astListener, Node node) {
    String nodeClassName = node.getClass().getSimpleName();
    String methodName = prefix + nodeClassName;
    Class<?>[] paramTypes = {node.getClass()};
//    astListener.getClass().getDeclaredMethod()
    try {
      return astListener.getClass().getMethod(methodName, paramTypes);
    } catch (Exception e) {
      System.err.println(node.getClass().getName() + " has no such method");
      e.printStackTrace();
      System.exit(1);
    }
    // never touched
    return null;
  }
}
