package ast.node.flow;

import ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class While extends Node {
    public While(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "while";
    }
}
