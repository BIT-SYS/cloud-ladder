package cloudladder.core.ir;

import cloudladder.core.runtime.CLRtFrame;

public class CLIRPop extends CLIR {
    @Override
    public void execute(CLRtFrame frame) {
        frame.vm.stack.pop();
    }

    @Override
    public String toString() {
        return "pop";
    }
}
