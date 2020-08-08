package ast;

import org.antlr.v4.runtime.ParserRuleContext;

public class Expression extends Node {
    public Expression(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String printNode() {
        return "Expr";
    }
}
