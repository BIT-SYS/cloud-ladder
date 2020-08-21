package ast.node.type;

import ast.AstVisitor;
import grammar.CLParserParser;

public class TypeName extends Type {
    public TypeName(CLParserParser.BasicTypeContext ctx, String string) {
        super(ctx, string);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
