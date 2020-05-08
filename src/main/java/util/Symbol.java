package util;

import symboltable.ProcedureSymbol;
import symboltable.Scope;
import symboltable.VariableSymbol;

import static util.Type.getType;

public class Symbol {
    public static ProcedureSymbol mkproc(Scope scope, String name, String retType, symboltable.Symbol... args) {
        ProcedureSymbol proc = new ProcedureSymbol(name, getType(retType), scope);
        for (symboltable.Symbol arg : args) {
            proc.define(arg);
        }
        return proc;
    }

    public static VariableSymbol mkprmtr(String typeStr, String name) {
        return new VariableSymbol(name, getType(typeStr));
    }

    public static VariableSymbol mkprmtr(String typeStr) {
        return new VariableSymbol("", getType(typeStr)); // 不需要非得有名字吧？
    }
}
