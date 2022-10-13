package cloudladder.core.object;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CLObjectAnnotation(typeIdentifier = "string")
public class CLString extends CLObject {
    public String value;

    @Override
    public String getTypeIdentifier() {
        return "string";
    }

    @Override
    public String defaultStringify() {
        return this.value;
    }
}
