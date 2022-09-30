package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLDict;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRBuildDict extends CLIR {
    public int count;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLDict dict = new CLDict();

        for (int i = 0; i < this.count; i++) {
            CLObject key = frame.vm.stack.pop();
            CLObject value = frame.vm.stack.pop();

            if (!(key instanceof CLString s)) {
                throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "dict key have to be string");
            }

            dict.put(s.value, value);
        }

        frame.vm.stack.push(dict);
    }
}
