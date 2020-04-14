package symboltable;

import java.util.Arrays;
import java.util.List;

public class SimpleType implements Type {
    private final static List<String> simpleTypes =
            Arrays.asList("Number", "Image", "String", "Audio", "Video", "Boolean", "Invalid");

    String name;

    public SimpleType(String string) {
        if (simpleTypes.contains(string)) {
            name = string;
        } else {
            //todo 报错
            System.err.println("Sorry I don't know what is " + string + "type");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}