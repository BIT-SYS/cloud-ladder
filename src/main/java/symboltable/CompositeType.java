package symboltable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static util.Type.getType;

public class CompositeType implements Type {
    private final static List<String> containerTypes = Arrays.asList("List", "Set", "HashMap", "Stack");

    String container;
    public Type element;

    // todo HashMap 有两个 element
    public CompositeType(String string) {
        int lt_pos = string.indexOf('<');
        String container = string.substring(0, lt_pos);
        if (!containerTypes.contains(container)) {
            //todo 报错
            System.err.println(container + " is not a composite container!");
        }
        this.container = container;

        String remain = string.substring(lt_pos + 1, string.length() - 1);
        element = getType(remain);
    }

    @Override
    public String toString() {
        return container + "<" + element + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeType that = (CompositeType) o;
        return container.equals(that.container) &&
                element.equals(that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(container, element);
    }
}
