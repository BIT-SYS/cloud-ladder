package ast.node;

import org.antlr.v4.runtime.ParserRuleContext;

public class Range extends Expression {
    public Boolean inclusive;

    public Range(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return inclusive ? "InclusiveRange" : "ExclusiveRange";
    }
}
