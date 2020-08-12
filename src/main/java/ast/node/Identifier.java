package ast.node;


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
}
