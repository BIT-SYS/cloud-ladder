package ast.node.flow;

import ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class For extends Node {
    public For(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "for";
    }
}
