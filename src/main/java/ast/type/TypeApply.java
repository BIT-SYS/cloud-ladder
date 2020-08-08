package ast.type;

import grammar.CLParserParser;

public class TypeApply extends Type {
    String name;

    public TypeApply(CLParserParser.CompositeTypeContext ctx) {
        super(ctx);
        name = ctx.children.get(0).getText();
    }

    @Override
    public String printNode() {
        return name;
    }
}
