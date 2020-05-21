import ast.ASTParser;
import ast.ASTWalker;
import ast.Program;
import check.SymbolCheck;
import check.TypeCheck;
import grammar.CLParserLexer;
import grammar.CLParserParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TypeCheckTester {
    public static void main(String[] args) throws Exception {
        // 单独测试某个文件
        CharStream input = CharStreams.fromFileName("examples/0-basic-1-test-type-simple.cl");
        CLParserLexer lexer = new CLParserLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParserParser parser = new CLParserParser(tokens);
        ParseTree tree = parser.program();

        ASTParser trans = new ASTParser();
        Program p = (Program) trans.visit(tree);
        ASTWalker walker = new ASTWalker();

        SymbolCheck symbolChecker = new SymbolCheck();
        walker.walk(symbolChecker, p);

        TypeCheck typeChecker = new TypeCheck();
        walker.walk(typeChecker, p);
    }

    @Test
    public void iterTestTypeCheck() throws IOException {
        File dir = new File("examples");
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                testTypeCheck(file.getAbsolutePath());
            }
        }
    }

    public void testTypeCheck(String inputFile) throws IOException {
        System.out.println("testing file: " + inputFile);

        CharStream input = CharStreams.fromFileName(inputFile);
        CLParserLexer lexer = new CLParserLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParserParser parser = new CLParserParser(tokens);
        ParseTree tree = parser.program();

        ASTParser trans = new ASTParser();
        Program p = (Program) trans.visit(tree);
        ASTWalker walker = new ASTWalker();

        SymbolCheck symbolChecker = new SymbolCheck();
        walker.walk(symbolChecker, p);

        TypeCheck typeChecker = new TypeCheck();
        walker.walk(typeChecker, p);
    }
}
