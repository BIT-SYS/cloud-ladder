package ast;

import org.antlr.v4.runtime.ParserRuleContext;

public class Literal extends Expression {
    String text;

    public Literal(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "Lit:" + text;
    }
}
