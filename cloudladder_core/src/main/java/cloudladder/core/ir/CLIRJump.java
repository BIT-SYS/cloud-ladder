package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRJump extends CLIR {
    public int step;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        frame.pc += this.step;
    }

    @Override
    public String toString() {
        return "jump " + this.step;
    }
}
