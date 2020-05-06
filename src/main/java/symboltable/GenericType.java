package symboltable;

public class GenericType implements Type {
    String name;

    public GenericType(String string) {
        name = string;
    }

    @Override
    public String toString() {
        return name;
    }
}
