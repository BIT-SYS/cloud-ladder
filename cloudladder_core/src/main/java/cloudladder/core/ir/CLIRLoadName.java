package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRLoadName extends CLIR {
    public int nameIndex;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        String name = frame.codeObject.getName(this.nameIndex);

        CLObject object = frame.loadName(name);

        frame.vm.stack.push(object);
    }

    @Override
    public String toString() {
        return "load_name " + this.nameIndex;
    }
}
