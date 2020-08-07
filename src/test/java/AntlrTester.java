import grammar.CLParserLexer;
import grammar.CLParserParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class AntlrTester extends Tester {
    @Override
    public void start(String file_name) throws IOException {
        super.start(file_name);
        System.out.println("===Antlr Test begin");

        CharStream input = CharStreams.fromFileName(file_name);
        CLParserLexer lexer = new CLParserLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParserParser parser = new CLParserParser(tokens);
        tree = parser.program();
        System.out.println(tree.toStringTree(parser));
        System.out.println("===Antlr Test end");
    }
}
