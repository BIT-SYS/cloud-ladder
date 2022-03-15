package cloudladder.core.compiler;

import cloudladder.core.compiler.ast.AST;
import grammar.CLLexer;
import grammar.CLParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class CLCompilerCodeToAST implements CompilerCodeToAST {
    @Override
    public AST compileCodeToAST(String code) {
        CLLexer lexer = new CLLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParser parser = new CLParser(tokens);
        CLParser.ProgramContext tree = parser.program(); // generate CST

        AntlrVisitor visitor = new AntlrVisitor();
        AST ast = visitor.visit(tree);

        return ast;
    }
}
