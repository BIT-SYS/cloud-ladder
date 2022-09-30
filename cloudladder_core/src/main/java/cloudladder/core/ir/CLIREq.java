package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;

public class CLIREq extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject a = frame.vm.stack.pop();
        CLObject b = frame.vm.stack.pop();

        frame.vm.stack.push(a.eq(b));
    }

    @Override
    public String toString() {
        return "eq";
    }
}
