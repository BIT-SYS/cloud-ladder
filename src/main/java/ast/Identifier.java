package ast;

import org.antlr.v4.runtime.ParserRuleContext;

public class Identifier extends Expression {
    String name;

    public Identifier(ParserRuleContext ctx) {
        super(ctx);
        name = ctx.getText();
    }

    public Identifier(String text) {
        super(null);
        name = text;
    }

    @Override
    public String printNode() {
        return name;
    }
}
