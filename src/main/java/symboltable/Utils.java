package symboltable;

public class Utils {
    public static Type getType(String string) {
        // 可以维护一个“类型池”，不知道有没有必要
        if (string.contains("<")) {
            return new CompositeType(string.replace(" ", ""));
        } else if (string.startsWith("Type")) {
            return new GenericType(string);
        } else {
            return new SimpleType(string.replace(" ", "")); //啊？我写replace干嘛？
        }
    }

    public static boolean sameType(Type a, Type b) {
        return a.toString().equals(b.toString());
    }


    public static boolean sameParameterType(Type argType, Type parType) {
        System.out.println("argType " + argType);
        System.out.println("parType " + parType);
//        // 两者之间可能有一个是Proc
//        if (parType.toString().equals("Proc")) {
//            return true; //todo 目前没法判断传进来的是返回argType的函数还是就是argType的变量
//        } else
        if (!parType.toString().contains("Type")) {
            return argType.toString().equals(parType.toString());
        } else {
            // todo 形参是泛型
            return true;
        }
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
