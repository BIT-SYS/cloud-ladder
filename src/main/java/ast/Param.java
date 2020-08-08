package ast;

import org.antlr.v4.runtime.ParserRuleContext;

// 第一个child是type，第二个是name
public class Param extends Node {
    public Param(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "Param";
    }
}
