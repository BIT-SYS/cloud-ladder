package ast.node;

import ast.AstVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

// 第一个child是type，第二个是name
public class Param extends Node {
    public Param(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "param";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
