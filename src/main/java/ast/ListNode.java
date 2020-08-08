package ast;

import org.antlr.v4.runtime.ParserRuleContext;

public class ListNode extends Expression {
    public ListNode(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String printNode() {
        return "ListTODO";
    }
}
