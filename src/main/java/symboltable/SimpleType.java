package symboltable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimpleType implements Type {
    private final static List<String> simpleTypes =
            Arrays.asList("Number", "Image", "String", "Audio", "Video", "Boolean", "Invalid", "Proc");

    public String name;

    public SimpleType(String string) {
        if (simpleTypes.contains(string)) {
            name = string;
        } else {
            //todo 报错
            System.err.println("Sorry I don't know what is " + string + " type");
            name = "Invalid";
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleType that = (SimpleType) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}