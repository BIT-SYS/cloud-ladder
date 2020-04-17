package symboltable;

public class Utils {
    public static Type getType(String string) {
        if (string.contains("<")) {
            return new CompositeType(string.replace(" ", ""));
        } else {
            return new SimpleType(string.replace(" ", ""));
        }
    }
}
