public class ASTListenerTester extends ASTBaseListener {
  @Override
  public void enterProgram(Program node) {
    System.out.println("Enter program !!!!");
    super.enterProgram(node);
  }

  @Override
  public void exitProgram(Program node) {
    System.out.println("Exit program !!!");
    super.exitProgram(node);
  }

  @Override
  public void enterIfBlock(IfBlock node) {
    System.out.println("enter If block");
  }

  @Override
  public void enterVariableDeclaration(VariableDeclaration node) {
    System.out.println("enter V D");
    super.enterVariableDeclaration(node);
  }
}
