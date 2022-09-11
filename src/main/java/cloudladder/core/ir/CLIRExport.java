package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;

public class CLIRExport extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject content = frame.vm.stack.pop();
        CLObject name = frame.vm.stack.pop();

        if (!(name instanceof CLString s)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "export name have to be a string");
        }

        frame.exportObjects.put(s.value, content);
        frame.scope.addVariable(s.value, content);
    }

    @Override
    public String toString() {
        return "export";
    }
}
