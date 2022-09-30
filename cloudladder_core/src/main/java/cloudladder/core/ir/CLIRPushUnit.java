package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLUnit;
import cloudladder.core.runtime.CLRtFrame;

public class CLIRPushUnit extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLUnit unit = new CLUnit();
        frame.vm.stack.push(unit);
    }

    @Override
    public String toString() {
        return "push_unit";
    }
}
