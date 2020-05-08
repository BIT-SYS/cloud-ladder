package symboltable;

public class Utils {
    public static Type getType(String string) {
        // 可以维护一个“类型池”，不知道有没有必要
        if (string.contains("<")) {
            return new CompositeType(string.replace(" ", ""));
        } else {
            return new SimpleType(string.replace(" ", ""));
        }
    }

    public static boolean typeEquals(Type a, Type b) {
        return a.toString().equals(b.toString());
    }

    public static Type getElementType(String typeStr) {
        return getType(typeStr.substring(typeStr.indexOf('<') + 1, typeStr.length() - 1));
    }

    public static ProcedureSymbol mkproc(Scope scope, String name, String retType, Symbol... args) {
        ProcedureSymbol proc = new ProcedureSymbol(name, getType(retType), scope);
        for (Symbol arg : args) {
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
