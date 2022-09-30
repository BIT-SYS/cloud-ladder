package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;

public class CLIRSet extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.vm.stack.pop();
        CLObject index = frame.vm.stack.pop();
        CLObject value = frame.vm.stack.pop();

        object.setValue(index, value);
    }
}
