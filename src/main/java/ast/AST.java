package ast;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class AST {
    Token token;
    List<AST> children;

    public AST(Token t) {
        token = t;
    }

    public void addChild(AST a) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(a);
    }
}
