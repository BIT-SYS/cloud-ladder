package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLFunctionDefinition;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLUserFunction;
import cloudladder.core.runtime.CLRtFrame;

import java.util.HashMap;

public class CLIRBuildFunction extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.vm.stack.pop();
        if (!(object instanceof CLFunctionDefinition fd)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting function");
        }

        HashMap<String, CLObject> caughtVariables = new HashMap<>();
        for (String nonLocalName : fd.codeObject.nonLocalNames) {
            CLObject variable = frame.loadName(nonLocalName);
            caughtVariables.put(nonLocalName, variable);
        }

        CLUserFunction function = new CLUserFunction(fd.codeObject, fd.params, fd.catchAll, caughtVariables);
        frame.vm.stack.push(function);
    }

    @Override
    public String toString() {
        return "build_func";
    }
}
