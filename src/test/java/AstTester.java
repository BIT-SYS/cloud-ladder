import ast.ASTParser;

import java.io.IOException;

public class AstTester extends AntlrTester {
    @Override
    public void start(String file_name) throws IOException {
        super.start(file_name);
        System.out.println("===AST Test begin");
        ASTParser astParser = new ASTParser();
        nodeRoot = astParser.visit(tree);
        System.out.println(nodeRoot.toStringTree());
        System.out.println("===AST Test end");
    }
}
