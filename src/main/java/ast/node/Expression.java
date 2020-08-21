package ast.node;

import ast.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Expression extends Node {
    public Expression(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "expr";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
