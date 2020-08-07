package symboltable;

public class Symbol {
    public String name;
    public Type type;
    Scope scope;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, Type type) {
        this(name);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName() + ": " + type;
    }
}