package symboltable;

import java.util.LinkedHashMap;
import java.util.LinkedList;
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
        if (sym instanceof ProcedureSymbol) {
            symbols.put(sym.name + ((ProcedureSymbol) sym).signature, sym);
        } else {
            symbols.put(sym.name, sym);
        }
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

    @Override
    public boolean hasProcedure(String name) {
        return symbols.keySet().stream()
                .anyMatch(str -> str.startsWith(name) && str.substring(name.length()).startsWith("["))
                || (null != enclosingScope && enclosingScope.hasProcedure(name));
    }

    public LinkedList<ProcedureSymbol> resolveProcedures(String name) {
        LinkedList<ProcedureSymbol> list = new LinkedList<>();
        symbols.keySet().stream().filter(str -> str.startsWith(name) && str.substring(name.length()).startsWith("["))
                .map(str -> (ProcedureSymbol) symbols.get(str)).forEachOrdered(list::add);
        if (null != enclosingScope)
            list.addAll(enclosingScope.resolveProcedures(name));
        return list;
    }

    @Override
    public Symbol resolveWithin(String name) {
        return symbols.get(name);
    }

    public String toString() {
        return symbols.values().toString();
    }
}
