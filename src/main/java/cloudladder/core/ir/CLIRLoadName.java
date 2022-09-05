package cloudladder.core.ir;

import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRLoadName extends CLIR {
    public int nameIndex;

    @Override
    public void execute(CLRtFrame frame) {
        String name = frame.codeObject.getName(this.nameIndex);

        CLObject object = frame.scope.getVariable(name);

        if (object == null) {
            // todo error
        } else {
            frame.vm.stack.push(object);
        }
    }

    @Override
    public String toString() {
        return "load_name " + this.nameIndex;
    }
}
