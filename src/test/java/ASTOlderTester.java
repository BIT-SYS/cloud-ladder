import ast.*;
import check.SymbolCheck;
import grammar.CLParserLexer;
import grammar.CLParserParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class ASTOlderTester {
    public static void main(String[] args) throws Exception {
        // 单独测试某个文件
        CharStream input = CharStreams.fromFileName("examples/leap-year.cl");
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
