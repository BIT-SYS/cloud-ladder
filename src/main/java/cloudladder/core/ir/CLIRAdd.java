package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;

// add the first and second object of the stack
public class CLIRAdd extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject a = frame.vm.stack.pop();
        CLObject b = frame.vm.stack.pop();

        CLObject result = a.add(b);
        frame.vm.stack.push(result);
    }

    @Override
    public String toString() {
        return "add";
    }
}