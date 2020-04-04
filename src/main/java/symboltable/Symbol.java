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
        if (type != Type.INVALID) return '<' + getName() + ":" + type + '>';
        return getName();
    }

    public enum Type {INVALID, Number, Image, String, Audio, Video, Boolean}
}
