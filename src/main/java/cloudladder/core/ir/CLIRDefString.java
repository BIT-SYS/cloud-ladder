package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLString;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CLIRDefString extends CLIR {
    private final String dataName;
    private final String value;

    @Override
    public void execute(CLRtEnvironment environment) {
        CLString str = new CLString(value);
        str.setExts(environment.getVariable("String"));

        CLReference ref = new CLReference(str);
        environment.addVariable(dataName, ref);
        environment.incPC();
    }

    @Override
    public String toSelfString() {
        return "defstr      " + this.dataName + ", " + "\"" + this.value + "\"";
    }
}
