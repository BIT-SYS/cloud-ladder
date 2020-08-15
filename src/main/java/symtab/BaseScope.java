package symtab;

import java.util.HashMap;
import java.util.Map;

public class BaseScope implements Scope {
    String name;
    Scope enclosingScope;
    Map<String, Symbol> symbols = new HashMap<>();

    public BaseScope(Scope outer, String string) {
        enclosingScope = outer;
        name = string;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol symbol) {
        symbols.put(symbol.name, symbol);
        symbol.scope = this;
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if (null != s) return s;
        if (null != enclosingScope) return enclosingScope.resolve(name);
        return null;
    }
}
