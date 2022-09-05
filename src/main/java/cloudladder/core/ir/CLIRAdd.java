package cloudladder.core.ir;

import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.core.vm.CLVM;

// add the first and second object of the stack
public class CLIRAdd extends CLIR {
    @Override
    public void execute(CLRtFrame frame) {
        CLObject a = frame.vm.stack.pop();
        CLObject b = frame.vm.stack.pop();

        CLObject result = a.add(b);
        frame.vm.stack.push(result);
    }

    @Override
    public String toString() {
        return "add";
    }
}
