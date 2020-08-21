package ast.node;

import ast.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Range extends Expression {
    public Boolean inclusive;

    public Range(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return inclusive ? "inclusive-range" : "exclusive-range";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
