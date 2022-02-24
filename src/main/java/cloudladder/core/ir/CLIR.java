package cloudladder.core.ir;

import cloudladder.core.runtime.env.CLRtEnvironment;

public abstract class CLIR {
    protected String label;

    public abstract void execute(CLRtEnvironment environment);

    public abstract String toSelfString();

    @Override
    public String toString() {
        if (label != null && !label.isEmpty()) {
            return label + ":\n" + toSelfString();
        }
        return toSelfString();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
