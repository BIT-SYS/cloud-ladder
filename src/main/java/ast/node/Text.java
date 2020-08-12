package ast.node;

import org.antlr.v4.runtime.ParserRuleContext;

public class Text extends Node {
    String text;

    public Text(ParserRuleContext ctx, String string) {
        super(ctx);
        text = string;
    }

    @Override
    public String toString() {
        return text;
    }
}
