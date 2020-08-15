package symtab;

import ast.node.type.Type;

import java.util.LinkedHashMap;

public class ProcSymbol extends Symbol implements Scope {
    LinkedHashMap<String, Symbol> params = new LinkedHashMap<>();

    public ProcSymbol(String name, Type type, Scope scope) {
        super(name, type, scope);
    }

    @Override
    public Scope getEnclosingScope() {
        return scope; // TODO
    }

    @Override
    public void define(Symbol symbol) {
        params.put(symbol.name, symbol);
        symbol.scope = this;
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = params.get(name);
        if (s != null) return s;
        // if not here, check any enclosing scope
        if (getEnclosingScope() != null) {
            return getEnclosingScope().resolve(name);
        }
        return null; // not found
    }
}
