package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;

public class CLIRIndex extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.vm.stack.pop();
        CLObject index = frame.vm.stack.pop();

        CLObject result = object.indexing(index);
        frame.vm.stack.push(result);
    }
}
