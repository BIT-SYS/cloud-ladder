package ast.node;

import org.antlr.v4.runtime.ParserRuleContext;

public class WhileLoop extends Node {
    public WhileLoop(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "while";
    }
}
