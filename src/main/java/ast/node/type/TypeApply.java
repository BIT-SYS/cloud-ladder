package ast.node.type;

import grammar.CLParserParser;

public class TypeApply extends Type {
    public TypeApply(CLParserParser.CompositeTypeContext ctx) {
        super(ctx);
        name = ctx.children.get(0).getText();
    }

    @Override
    public String toString() {
        return name;
    }
}
