package ast.type;

import ast.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class Type extends Node {
    String name;

    public Type(ParserRuleContext ctx) {
        super(ctx);
    }
}
