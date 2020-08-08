package ast.type;

import ast.Node;
import grammar.CLParserParser;

public class Type extends Node {
    String name;

    public Type(CLParserParser.TypeTypeContext ctx) {
        super(ctx);
        name = ctx.getText();
    }

    @Override
    public String printNode() {
        return name;
    }
}
