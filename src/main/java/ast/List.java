package ast;

import org.antlr.v4.runtime.ParserRuleContext;

public class List extends Expression {
    public List(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String printNode() {
        return "ListTODO";
    }
}
