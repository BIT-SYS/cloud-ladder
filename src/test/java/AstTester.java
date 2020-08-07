import ast.CstVisitor;

import java.io.IOException;

public class AstTester extends AntlrTester {
    @Override
    public void start(String file_name) throws IOException {
        super.start(file_name);
        System.out.println("===AST Test begin");
        CstVisitor cstVisitor = new CstVisitor();
        astRoot = cstVisitor.visit(tree);
        System.out.println("===AST Test end");
    }
}
