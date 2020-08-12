package ast.node.type;

import grammar.CLParserParser;

public class TypeName extends Type {
    public TypeName(CLParserParser.BasicTypeContext ctx, String string) {
        super(ctx, string);
    }
}
