package symtab;

import ast.node.type.Type;

public abstract class Symbol {
    public String name;
    public Type type;
    public Scope scope;

    public Symbol(String name, Type type, Scope scope) {
        this.name = name;
        this.type = type;
        this.scope = scope;
    }
}
