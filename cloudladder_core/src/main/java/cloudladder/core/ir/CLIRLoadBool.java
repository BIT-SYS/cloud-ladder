package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLBoolean;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRLoadBool extends CLIR {
    public boolean value;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        if (this.value) {
            frame.vm.stack.push(CLBoolean.boolTrue);
        } else {
            frame.vm.stack.push(CLBoolean.boolFalse);
        }
    }

    @Override
    public String toString() {
        return "load_bool " + Boolean.toString(this.value);
    }
}
