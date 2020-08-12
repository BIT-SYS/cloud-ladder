package ast.node;

import org.antlr.v4.runtime.ParserRuleContext;

public class Block extends Node {
    public Block(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "block";
    }
}
