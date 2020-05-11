import ast.ASTParser;
import ast.ASTWalker;
import ast.Node;
import ast.Program;
import grammar.CLParserLexer;
import grammar.CLParserParser;
import interpreter.Interpreter;
import ir.IR;
import ir.NoOperationIR;
import check.SymbolCheck;
import check.TypeCheck;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASTParserTester {

  @Test
  public void IterTestASTBuild() throws IOException {
    File file = new File("examples");
    File[] fs = file.listFiles();
    assert fs != null;
    for (File f : fs) {
      if (f.isFile()) {
        tryToBuildAST(f.getAbsolutePath());
      }
    }
  }

  public static Program tryToBuildAST(String inputFile) throws IOException {

    System.out.println("test file: " + inputFile);
    InputStream is = System.in;
    if (inputFile != null) is = new FileInputStream(inputFile);
    CharStream input = CharStreams.fromStream(is);
    CLParserLexer lexer = new CLParserLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CLParserParser parser = new CLParserParser(tokens);
    ParseTree tree = parser.program();

    ASTParser trans = new ASTParser();
    return (Program) trans.visit(tree);
  }

  public static IR tryToBuildIR(String inputFile) throws IOException {
    Program p = tryToBuildAST(inputFile);
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
    return Node.ir;
  }

  public static void tryToInterprete(String inputFile) throws IOException {
    IR ir = tryToBuildIR(inputFile);
    Interpreter i = new Interpreter();
    i.execute(ir);
  }

  public static void main(String[] args) throws Exception {
//    tryToBuildIR("examples/expr.cl");
    tryToInterprete("examples/proc.cl");
  }
}
