package cloudladder.core.ir;

import cloudladder.core.runtime.env.CLRtEnvironment;

public class CLIRNop extends CLIR {
    @Override
    public void execute(CLRtEnvironment environment) {
        environment.incPC();
    }

    @Override
    public String toSelfString() {
        return "nop         ";
    }
}
