package util;

import interpreter.ProcSignature;
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

    public static ProcedureSymbol builtin(Scope scope, ProcSignature ps, symboltable.Type retType) {
        ProcedureSymbol proc = new ProcedureSymbol(ps.getName(), retType, scope);
        //                                                     👇 貌似 F 的 Value 存函数参数时，Value::value是参数名
        ps.getArgs().stream().map(value -> new VariableSymbol(value.value, value.type)).forEachOrdered(proc::define);
        return proc;
    }
}
