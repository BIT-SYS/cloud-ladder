package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLBoolean;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CLIRDefBoolean extends CLIR {
    private final String dataName;
    private final boolean value;


    @Override
    public void execute(CLRtEnvironment environment) {
        CLBoolean data = new CLBoolean(value);

        CLReference ref = new CLReference(data);
        environment.addVariable(dataName, ref);
        environment.incPC();
    }

    @Override
    public String toSelfString() {
        return "defboolean  " + this.dataName + ", " + this.value;
    }
}
