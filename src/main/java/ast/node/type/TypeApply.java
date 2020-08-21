package ast.node.type;

import ast.AstVisitor;
import grammar.CLParserParser;

public class TypeApply extends Type {
    public TypeApply(CLParserParser.CompositeTypeContext ctx, String string) {
        super(ctx, string);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
