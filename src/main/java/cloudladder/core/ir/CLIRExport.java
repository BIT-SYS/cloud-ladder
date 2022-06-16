package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLObject;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRExport extends CLIR {
    private final String name;
    private final String expo;

    @Override
    public void execute(CLRtEnvironment environment) {
        environment.incPC();

        if (!environment.hasOwnVariable("$exports")) {
            environment.addVariable("$exports", environment.newObject().wrap());
        }

        CLObject exportObj = (CLObject) environment.getCurrentScope().getVariable("$exports").getReferee();
        CLReference data = environment.getVariable(this.expo);

        exportObj.addStringRefer(this.name, data.shallowClone());
    }

    @Override
    public String toSelfString() {
        StringBuilder sb = new StringBuilder();
        sb.append("export      ")
                .append(name)
                .append(", ")
                .append(expo)
                ;
        return new String(sb);
    }
}
