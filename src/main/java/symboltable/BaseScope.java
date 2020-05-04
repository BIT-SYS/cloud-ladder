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
        Symbol sameName = symbols.get(sym.name);
        symbols.put(sym.name, sym);

        if (null != sameName) {
            // 同一个作用域里能定义多遍的，就是函数重载了
            // 不知道这样实现会不会有问题……
            assert sameName instanceof ProcedureSymbol;
            assert sym instanceof ProcedureSymbol;
            ((ProcedureSymbol) sym).next = (ProcedureSymbol) sameName;
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
    public Symbol resolveWithin(String name) {
        return symbols.get(name);
    }

    public String toString() {
        return symbols.values().toString();
    }
}
