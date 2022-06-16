package cloudladder.core.runtime.data;

import cloudladder.core.runtime.data.CLData;
import lombok.Getter;

@Getter
public class CLNumber extends CLData {
    private final double value;

    public CLNumber(double value) {
        super();

        this.typeName = "number";
        this.isBuildIn = true;

        this.value = value;
    }

    public int getIntegralValue() {
        return (int)Math.round(this.value);
    }

    public boolean isInteger() {
        return Math.abs(value - (int) value) < 0.000001;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
