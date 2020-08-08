package ast;

import grammar.CLParserParser;

public class VarDecl extends Node {
    String type;

    public VarDecl(CLParserParser.VariableDeclContext ctx) {
        super(ctx);
        type = ctx.typeType().getText();
    }

    @Override
    public String printNode() {
        return "VarDecl"; //我知道getClass().getName()，但那不起作用
    }
}
