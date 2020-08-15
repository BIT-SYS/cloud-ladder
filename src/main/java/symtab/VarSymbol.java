package symtab;

import ast.node.type.Type;

public class VarSymbol extends Symbol {

    public VarSymbol(String name, Type type, Scope scope) {
        super(name, type, scope);
    }
}
