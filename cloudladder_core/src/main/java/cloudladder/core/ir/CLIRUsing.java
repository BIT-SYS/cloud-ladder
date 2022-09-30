package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.knowledge.implementation.MethodImplementation;
import cloudladder.core.knowledge.implementation.MethodImplementationUtil;
import cloudladder.core.object.CLFunction;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRUsing extends CLIR {
    public int aliasIndex;
    public int scopeIndex;
    public int nameIndex;
    public int pathIndex;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        String name = frame.codeObject.getName(this.nameIndex);
        String scope = null;
        if (this.scopeIndex != -1) {
            scope = frame.codeObject.getName(this.scopeIndex);
        }
        String path = ((CLString) frame.codeObject.getConstant(this.pathIndex)).value;
        String alias = null;
        if (this.aliasIndex != -1) {
            alias = frame.codeObject.getName(this.aliasIndex);
        }

        MethodImplementation implementation = MethodImplementationUtil.getImplementation(frame.vm.serverBase);
        CLFunction function = implementation.buildFunction();

        String finalName = name;
        if (alias != null) {
            finalName = alias;
        }
        frame.scope.addVariable(finalName, function);
    }

    @Override
    public String toString() {
        return "using";
    }
}
