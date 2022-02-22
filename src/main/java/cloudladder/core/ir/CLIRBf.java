package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLBoolean;
import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class CLIRBf extends CLIR {
    private final String target;
    private final String label;
    @Setter
    private int offset;

    @Override
    public void execute(CLRtEnvironment environment) {
        CLReference val = environment.getVariable(target);
        CLData data = val.getReferee();
        if (data instanceof CLBoolean) {
            if (!((CLBoolean) data).getValue()) {
                environment.setPCBy(offset);
            } else {
                environment.incPC();
            }
        } else {
            environment.incPC();
        }
    }

    @Override
    public String toSelfString() {
        return "bf          " + this.target + " " + this.label + "(" + offset + ")";
    }
}
