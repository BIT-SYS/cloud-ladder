package ast.node;

import ast.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Block extends Node {
    public Block(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "block";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
