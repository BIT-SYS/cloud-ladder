package ast;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

public class Node {
    ParserRuleContext actx; // 避免和antlr visitor里new Node(){{addChild(visit(ctx.xxx))}}的ctx搞混
    List<Node> children;

    //    public Node() {};
    public Node(ParserRuleContext ctx) {
        actx = ctx;
    }

    public void addChild(Node a) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(a);
    }

    public String printNode() {
        return "Node";
    }

    // lisp-style AST
    public String toStringTree() {
        if (children == null || children.size() == 0) return printNode();
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(printNode());
        children.forEach(
                node -> {
                    if (node == null) {
                        sb.append(' ').append("null");
                    } else // TODO delete this if
                        sb.append(' ').append(node.toStringTree());
                }
        );
        sb.append(')');
        return sb.toString();
    }
}
