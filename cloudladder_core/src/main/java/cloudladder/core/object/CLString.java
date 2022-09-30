package cloudladder.core.object;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CLObjectAnnotation(typeIdentifier = "string")
public class CLString extends CLObject {
    public String value;
}
