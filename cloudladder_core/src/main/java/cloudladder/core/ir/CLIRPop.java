package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.runtime.CLRtFrame;

public class CLIRPop extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        frame.vm.stack.pop();
    }

    @Override
    public String toString() {
        return "pop";
    }
}
