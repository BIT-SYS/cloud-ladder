package cloudladder.core.ir;

import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRLoadConst extends CLIR {
    public int index;

    @Override
    public void execute(CLRtFrame frame) {
        CLObject constant = frame.codeObject.getConstant(this.index);

        frame.vm.stack.push(constant);
    }

    @Override
    public String toString() {
        return "load_const " + this.index;
    }
}
