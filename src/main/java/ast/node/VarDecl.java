package ast.node;

import grammar.CLParserParser;

public class VarDecl extends Node {
    public VarDecl(CLParserParser.VariableDeclContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "VarDecl"; //我知道getClass().getName()，但那不起作用
    }
}
