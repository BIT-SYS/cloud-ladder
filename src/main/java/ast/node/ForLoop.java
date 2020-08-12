package ast.node;

import org.antlr.v4.runtime.ParserRuleContext;

public class ForLoop extends Node {
    public ForLoop(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "for";
    }
}
