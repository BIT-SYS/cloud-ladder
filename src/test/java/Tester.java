import ast.AST;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

abstract public class Tester {
    ParseTree tree;
    AST astRoot;

    public void start(String file_name) throws IOException {
    }
}
