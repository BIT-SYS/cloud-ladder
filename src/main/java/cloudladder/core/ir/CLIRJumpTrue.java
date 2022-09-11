package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLBoolean;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRJumpTrue extends CLIR {
    public int step;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.vm.stack.pop();

        if (object instanceof CLBoolean b) {
            if (b.value) {
                frame.pc += this.step;
            }
        }
    }

    @Override
    public String toString() {
        return "jump_true " + this.step;
    }
}
