package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRStoreName extends CLIR {
    public int nameIndex;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        String name = frame.codeObject.getName(this.nameIndex);
        CLObject value = frame.vm.stack.pop();

        frame.scope.addVariable(name, value);
    }

    @Override
    public String toString() {
        return "store_name " + this.nameIndex;
    }
}
