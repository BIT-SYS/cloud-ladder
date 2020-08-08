package ast;

import org.antlr.v4.runtime.ParserRuleContext;

// Assign并不是Expression
public class Assign extends Node {
    public Assign(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "Assign";
    }
}
