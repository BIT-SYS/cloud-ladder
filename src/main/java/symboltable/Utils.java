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
        String parString = parType.toString();
        String argString = argType.toString();

//        // 两者之间可能有一个是Proc
//        if (parType.toString().equals("Proc")) {
//            return true; //todo 目前没法判断传进来的是返回argType的函数还是就是argType的变量
//        } else
        if (containsGeneric(parType)) {
            // 形参是泛型
            // 目前复合类型只有一个空位（List），等字典实现了再用别的方法
            int l = parString.indexOf("Type");
            int r = parString.length() - l - "Type".length() - 1; //必不可能有两个字母以上的TypeX，这是规定

            return argString.substring(0, l).equals(parString.substring(0, l))
                    && argString.substring(argString.length() - r).equals(parString.substring(parString.length() - r));
        } else {
            return argString.equals(parString);
        }
    }

    public static Type matchGenericType(Type argType, Type parType) {
        // 给List<List<Number>>和List<TypeA>得到List<Number>（对应TypeA的类型）
        if (parType instanceof GenericType) {
            return argType;
        }
        if (parType instanceof CompositeType && argType instanceof CompositeType) {
            return matchGenericType(((CompositeType) argType).element, ((CompositeType) parType).element);
        }
        return null;
    }

    public static Type replceGenericType(Type typeWithGenetic, Type coreType) {
        assert containsGeneric(typeWithGenetic) && !containsGeneric(coreType);
        String typeStr = typeWithGenetic.toString();
        int l = typeStr.indexOf("Type");
        int r = typeStr.length() - l - "Type".length() - 1; //必不可能有两个字母以上的TypeX，这是规定
        return getType(typeStr.substring(0, l) + coreType.toString() + typeStr.substring(typeStr.length() - r));
    }

    public static Type getElementType(Type type) {
        assert type instanceof CompositeType;
        return ((CompositeType) type).element;
    }

    public static Type getInnermostElementType(Type type) {
        if (type instanceof CompositeType) {
            return getInnermostElementType(((CompositeType) type).element);
        } else return type; // SimpleType or GenericType;
    }

    public static boolean containsGeneric(Type type) {
        return type.toString().contains("Type");
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
