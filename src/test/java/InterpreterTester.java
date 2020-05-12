import ast.ASTParser;
import ast.ASTWalker;
import ast.Node;
import ast.Program;
import check.SymbolCheck;
import check.TypeCheck;
import grammar.CLParserLexer;
import grammar.CLParserParser;
import interpreter.Value;
import ir.IR;
import ir.NoOperationIR;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class InterpreterTester {

  public static Program tryToBuildAST(String input) throws IOException {

    InputStream is = new ByteArrayInputStream(input.getBytes());
    CLParserLexer lexer = new CLParserLexer(CharStreams.fromStream(is));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CLParserParser parser = new CLParserParser(tokens);
    ParseTree tree = parser.program();

    ASTParser trans = new ASTParser();
    Program p = (Program) trans.visit(tree);
    return p;
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

  public static Object tryToInterpret(String input) throws IOException {
    IR ir = tryToBuildIR(input);
    interpreter.Interpreter i = new interpreter.Interpreter();
    i.debug = false;
    return i.execute(ir);
  }

  public static void main(String[] args) throws IOException {
    while (true) {
      Scanner sc = new Scanner(System.in);
      String input = sc.nextLine();
      System.out.println(tryToInterpret(input));
    }
  }
}
