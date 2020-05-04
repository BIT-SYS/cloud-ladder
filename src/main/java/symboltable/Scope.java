package symboltable;

public interface Scope {
    String getScopeName();

    /**
     * Where to look next for symbols
     */
    Scope getEnclosingScope();

    /**
     * Define a symbol in the current scope
     */
    void define(Symbol sym);

    /**
     * Look up name in this scope or in enclosing scope if not here
     */
    Symbol resolve(String name);

    /**
     * 只看当前scope有没有（用于检查重定义）
     */
    Symbol resolveWithin(String name);
}
