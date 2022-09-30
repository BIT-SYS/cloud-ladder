package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.core.runtime.CLRtScope;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRStoreNameExist extends CLIR {
    private int nameIndex;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        String name = frame.codeObject.getName(this.nameIndex);
        CLObject value = frame.vm.stack.pop();

        CLRtScope scope = frame.scope;
        while (scope != null && !scope.hasOwnVariable(name)) {
            scope = scope.parent;
        }

        if (scope == null) {
            // todo error
        } else {
            scope.addVariable(name, value);
        }
    }

    @Override
    public String toString() {
        return "store_name_exist " + this.nameIndex;
    }
}
