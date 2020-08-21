package ast.node;

import ast.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class IfElse extends Node {
    public IfElse(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "if-else";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
