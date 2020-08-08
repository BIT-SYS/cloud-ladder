package ast;

import org.antlr.v4.runtime.ParserRuleContext;

public class ParamList extends Node {
    public ParamList(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String printNode() {
        return "Params";
    }
}
