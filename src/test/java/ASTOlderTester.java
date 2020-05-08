import AST.*;
import check.SymbolCheck;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class ASTOlderTester {
    public static void main(String[] args) throws Exception {
        // 单独测试某个文件
        InputStream is = new FileInputStream("examples/leap-year.cl");

        ANTLRInputStream input = new ANTLRInputStream(is);
        CLParserLexer lexer = new CLParserLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParserParser parser = new CLParserParser(tokens);
        ParseTree tree = parser.program();

        ASTParser trans = new ASTParser();
        Program p = (Program) trans.visit(tree);
        ASTWalker walker = new ASTWalker();

        SymbolCheck symbolChecker = new SymbolCheck();
        walker.walk(symbolChecker, p);

        ASTOlderRunner typeChecker = new ASTOlderRunner();
        walker.walk(typeChecker, p);
    }

    public static class ASTOlderRunner extends ASTBaseListener {
        @Override
        public void exitCallExpression(CallExpression ctx) {
            System.out.println("exit call" + ctx.symbol);
        }

        @Override
        public void exitMemberExpression(MemberExpression ctx) {
            System.out.println("exit method" + ctx.property.symbol + "\n\n");
        }
    }

}
