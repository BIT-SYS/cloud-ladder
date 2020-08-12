package ast.node;

import org.antlr.v4.runtime.ParserRuleContext;

public class IfElse extends Node {
    public IfElse(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "if-else";
    }
}
