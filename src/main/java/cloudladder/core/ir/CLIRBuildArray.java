package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLArray;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRBuildArray extends CLIR {
    public int count;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLArray result = new CLArray();
        for (int i = 0; i < this.count; i++) {
            CLObject item = frame.vm.stack.pop();
            result.addItem(item);
        }
        frame.vm.stack.push(result);
    }

    @Override
    public String toString() {
        return "build_array " + this.count;
    }
}
