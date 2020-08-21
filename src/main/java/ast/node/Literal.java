package ast.node;

import ast.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Literal extends Expression {
    public Literal(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "lit";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
