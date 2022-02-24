package cloudladder.core.ir;

import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;

public class CLIRNewBlock extends CLIR {
    @Override
    public void execute(CLRtEnvironment environment) {
        CLRtScope newScope = new CLRtScope(environment.getCurrentScope());

        environment.setCurrentScope(newScope);
        environment.incPC();
    }

    @Override
    public String toSelfString() {
        return "new_block";
    }
}
