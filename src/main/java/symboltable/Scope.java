package symboltable;

import java.util.LinkedList;

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

    /**
     * 确定有没有某个函数
     */
    boolean hasProcedure(String name);

    /**
     * 返回所有同名函数
     */
    LinkedList<ProcedureSymbol> resolveProcedures(String name);
}
