package cloudladder.core.runtime.data;

import cloudladder.core.runtime.data.CLData;

public class CLBoolean extends CLData {
    private final boolean value;

    public CLBoolean(boolean value) {
        super();

        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
}
