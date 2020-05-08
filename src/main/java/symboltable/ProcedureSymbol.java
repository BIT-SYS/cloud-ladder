package symboltable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProcedureSymbol extends Symbol implements Scope {
    LinkedHashMap<String, Symbol> parameters = new LinkedHashMap<>();
    Scope enclosingScope;
//    public ProcedureSymbol next = null;
    public List<Type> signature = new ArrayList<>();

    public ProcedureSymbol(String name, Type retType, Scope enclosingScope) {
        super(name, retType);
        this.enclosingScope = enclosingScope;
        signature.add(type); // 这个是函数签名末尾，函数返回值的类型
    }

    @Override
    public String getScopeName() {
        return name;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol sym) {
        parameters.put(sym.name, sym);
        sym.scope = this;
        signature.add(signature.size()-1, sym.type);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = parameters.get(name);
        if (s != null) return s;
        // if not here, check any enclosing scope
        if (getEnclosingScope() != null) {
            return getEnclosingScope().resolve(name);
        }
        return null; // not found
    }

    // 👇 会用到么？
    @Override
    public Symbol resolveWithin(String name) {
        return parameters.get(name);
    }

    public boolean isMethod() {
        return null != parameters.get("self"); // todo 检查是不是只有第一个参数叫self
    }

    public String toString() {
        String[] temp = super.toString().split(":");
        return "proc " + temp[0] + " " + parameters.values() + ":" + temp[1];
    }
}
