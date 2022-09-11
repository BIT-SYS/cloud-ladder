package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLBoolean;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRJumpFalse extends CLIR {
    public int step;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.vm.stack.pop();

        if (object instanceof CLBoolean b) {
            if (!b.value) {
                frame.pc += this.step;
            }
        } else {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting boolean");
        }
    }

    @Override
    public String toString() {
        return "jump_false " + this.step;
    }
}
