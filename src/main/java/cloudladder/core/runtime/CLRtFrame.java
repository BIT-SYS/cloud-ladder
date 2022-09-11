package cloudladder.core.runtime;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.ir.CLIR;
import cloudladder.core.ir.CLIRReturn;
import cloudladder.core.object.CLCodeObject;
import cloudladder.core.object.CLDict;
import cloudladder.core.object.CLObject;
import cloudladder.core.vm.CLVM;

import java.util.HashMap;

public class CLRtFrame {
    public int pc;
    public CLCodeObject codeObject;
    public CLRtScope scope;
    public CLRtFrame parent;
    public CLVM vm;
    public int previousStackLength;
    public CLDict exportObjects;
    public HashMap<String, CLObject> caughtVariables;

    private String description;

    public CLRtFrame(CLVM vm, CLRtFrame parent, CLCodeObject code, String description) {
        this.parent = parent;
        this.codeObject = code;
        this.description = description;
        this.vm = vm;
        this.previousStackLength = vm.stack.size();
        this.exportObjects = new CLDict();

        this.scope = new CLRtScope();
        if (parent != null) {
            this.scope.parent = parent.scope;
        }
    }

    public void execute() throws CLRuntimeError {
        while (this.pc < this.codeObject.instructions.size()) {
            CLIR instruction = this.codeObject.getInstruction(this.pc);
            instruction.execute(this);
            this.pc++;

            if (instruction instanceof CLIRReturn) {
                return;
            }
        }
    }

    public CLObject loadName(String name) throws CLRuntimeError {
        if (this.caughtVariables != null && this.caughtVariables.containsKey(name)) {
            return this.caughtVariables.get(name);
        }
        CLObject result = this.scope.getVariable(name);
        if (result != null) {
            return result;
        }
        result = this.vm.globalScope.getOwnVariable(name);
        if (result != null) {
            return result;
        }

        throw new CLRuntimeError(CLRuntimeErrorType.VariableNotFound, "variable `" + name + "` not found");
    }
}
