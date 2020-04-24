package symboltable;

public class Utils {
    public static Type getType(String string) {
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
}
