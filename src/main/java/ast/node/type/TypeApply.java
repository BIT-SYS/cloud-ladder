package ast.node.type;

import grammar.CLParserParser;

public class TypeApply extends Type {
    public TypeApply(CLParserParser.CompositeTypeContext ctx, String string) {
        super(ctx, string);
    }
}
