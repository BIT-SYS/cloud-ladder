package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLBoolean;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRPushBoolean extends CLIR {
    public boolean value;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        frame.vm.stack.push(CLBoolean.get(this.value));
    }

    @Override
    public String toString() {
        return "push_boolean " + this.value;
    }
}
