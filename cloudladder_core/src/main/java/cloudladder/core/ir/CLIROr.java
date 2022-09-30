package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLBoolean;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;

public class CLIROr extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject a = frame.vm.stack.pop();
        CLObject b = frame.vm.stack.pop();

        if (!(a instanceof CLBoolean) || (!(b instanceof CLBoolean))) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting boolean");
        }

        if (((CLBoolean) a).value || ((CLBoolean) b).value) {
            frame.vm.stack.push(CLBoolean.get(true));
        } else {
            frame.vm.stack.push(CLBoolean.get(false));
        }
    }

    @Override
    public String toString() {
        return "or";
    }
}
