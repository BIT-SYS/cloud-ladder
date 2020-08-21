package ast.node;

import ast.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class ParamList extends Node {
    public ParamList(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "params";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
