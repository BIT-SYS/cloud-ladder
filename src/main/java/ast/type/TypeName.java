package ast.type;

import grammar.CLParserParser;

public class TypeName extends Type {
    public TypeName(CLParserParser.BasicTypeContext ctx) {
        super(ctx);
        name = ctx.getText();
    }

    public TypeName(String type) {
        super(null);
        name = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
