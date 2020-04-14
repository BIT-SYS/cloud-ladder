package symboltable;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope {
    Scope enclosingScope; // null if global (outermost) scope
    Map<String, Symbol> symbols = new LinkedHashMap<>();

    public BaseScope(Scope enclosingScope) {
        this.enclosingScope = enclosingScope;
    }

    @Override
    public String getScopeName() {
        return null;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol sym) {
        symbols.put(sym.name, sym);
        sym.scope = this; // track the scope in each symbol
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if (s != null) return s;
        // if not here, check any enclosing scope
        if (enclosingScope != null) return enclosingScope.resolve(name);
        return null; // not found
    }

    public String toString() {
        return symbols.values().toString();
    }
}
