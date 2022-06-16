package cloudladder.core.runtime.data;

import cloudladder.core.runtime.data.CLData;

public class CLString extends CLData {
    private final String value;

    public CLString(String value) {
        super();

        this.typeName = "string";
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
