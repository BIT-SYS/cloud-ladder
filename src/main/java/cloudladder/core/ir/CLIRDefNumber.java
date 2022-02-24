package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLNumber;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CLIRDefNumber extends CLIR {
    private final String dataName;
    private final double value;

    @Override
    public void execute(CLRtEnvironment environment) {
        CLNumber num = new CLNumber(value);
        num.setExts(environment.getVariable("Number"));

        CLReference ref = new CLReference(num);
        environment.addVariable(dataName, ref);
        environment.incPC();
    }

    @Override
    public String toSelfString() {
        return "defnumber   " + this.dataName + ", " + this.value;
    }
}
