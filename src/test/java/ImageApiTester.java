import ast.ASTParser;
import ast.ASTWalker;
import ast.Node;
import ast.Program;
import check.SymbolCheck;
import check.TypeCheck;
import grammar.CLParserLexer;
import grammar.CLParserParser;
import ir.IR;
import ir.NoOperationIR;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ImageApiTester {
    public static void main(String[] args) throws IOException {
        // 单独测试某个文件
        testImageApi("examples/5-image.cl");
    }

    @Test
    public void iterCheck() throws IOException {
        File dir = new File("examples");
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getName().startsWith("5-image")) {
                testImageApi(file.getAbsolutePath());
            }
        }
    }

    public static void testImageApi(String inputFile) throws IOException {
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

        int before = p.newLabel();
        int after = p.newLabel();
        Node.ir.emitLabel(before);
        p.gen(before, after);
        Node.ir.emitLabel(after);
        Node.ir.emit(new NoOperationIR());
        System.out.println(Node.ir);

        IR ir = Node.ir;
        interpreter.Interpreter i = new interpreter.Interpreter();
        i.debug = false;
        i.execute(ir);
    }
}
