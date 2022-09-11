package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;

public class CLIRDup extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject top = frame.vm.stack.top();
        frame.vm.stack.push(top);
    }

    @Override
    public String toString() {
        return "dup";
    }
}
