package ast.node;

import org.antlr.v4.runtime.ParserRuleContext;

public class IfElse extends Node {
    public IfElse(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "if-else";
    }

    @Override
    public String toStringTree() {
        if (children == null || children.size() == 0) return toString();
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(toString());
        children.forEach(
                node -> {
                    if (node == null) {
                        sb.append(' ').append("pass");
                    } else // TODO 我是应该单独加一个pass节点，还是直接用null？
                        sb.append(' ').append(node.toStringTree());
                }
        );
        sb.append(')');
        return sb.toString();
    }
}
