package ast.node.flow;

import ast.AstVisitor;
import ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class While extends Node {
    public While(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "while";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
