package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;

public class CLIRReturn extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject value = frame.vm.stack.pop();
        // stack destructuring
        while (frame.vm.stack.size() > frame.previousStackLength) {
            frame.vm.stack.pop();
        }
        frame.vm.stack.push(value);
    }

    @Override
    public String toString() {
        return "ret";
    }
}
