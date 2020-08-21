package ast.node;


import ast.AstVisitor;

public class Identifier extends Expression {
    String name;

    public Identifier(String text) {
        super(null);
        name = text;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
