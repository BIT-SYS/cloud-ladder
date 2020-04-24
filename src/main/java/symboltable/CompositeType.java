package symboltable;

import java.util.Arrays;
import java.util.List;

public class CompositeType implements Type {
    private final static List<String> containerTypes = Arrays.asList("List", "Set", "HashMap", "Stack");

    String container;
    Type element;

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
        if (remain.contains("<")) {
            element = new CompositeType(remain);
        } else {
            element = new SimpleType(remain);
        }
    }

    @Override
    public String toString() {
        return container + "<" + element + ">";
    }
}
