package cloudladder.core.ir;

import cloudladder.core.runtime.env.CLRtEnvironment;

public class CLIREndBlock extends CLIR {
    @Override
    public void execute(CLRtEnvironment environment) {
        CLRtScope scope = environment.getCurrentScope().getParent();
        environment.setCurrentScope(scope);

        environment.incPC();
    }

    @Override
    public String toSelfString() {
        return "end_block";
    }
}
