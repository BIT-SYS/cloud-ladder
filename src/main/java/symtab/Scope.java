package symtab;

public interface Scope {
    Scope getEnclosingScope();

    void define(Symbol symbol);

    Symbol resolve(String name);
}
