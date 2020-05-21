package symboltable;

import java.util.*;

import static util.Error.die;

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
            List<Type> signature = ((ProcedureSymbol) sym).signature;
            if (hasProcedure(sym.name)) {
                for (ProcedureSymbol proc : resolveProcedures(sym.name)) {
                    if (Arrays.equals(
                            Arrays.copyOfRange(signature.toArray(), 0, signature.size() - 1), // 去掉返回类型
                            Arrays.copyOfRange(proc.signature.toArray(), 0, proc.signature.size() - 1))) {
                        die("BaseScope: define", "You can't overload a procedure with the same parameter types!");
                    }
                }
            }
            symbols.put(sym.name + signature, sym);
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
