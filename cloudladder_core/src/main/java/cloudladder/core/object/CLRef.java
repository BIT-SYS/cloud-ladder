package cloudladder.core.object;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CLObjectAnnotation(typeIdentifier = "ref")
public class CLRef extends CLObject {
    public CLObject value;

    @Override
    public CLObject indexing(CLObject index) {
        if (index instanceof CLString s && s.value.equals("value")) {
            return this.value;
        }

        // todo
        return null;
    }

    @Override
    public void setValue(CLObject index, CLObject value) {
        this.value = value;
    }
}
