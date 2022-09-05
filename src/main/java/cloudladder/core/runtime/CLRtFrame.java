package cloudladder.core.runtime;

import cloudladder.core.ir.CLIR;
import cloudladder.core.object.CLCodeObject;
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
    public HashMap<String, CLObject> exportObjects;

    private String description;

    public CLRtFrame(CLVM vm, CLRtFrame parent, CLCodeObject code, String description) {
        this.parent = parent;
        this.codeObject = code;
        this.description = description;
        this.vm = vm;
        this.previousStackLength = vm.stack.size();
        this.exportObjects = new HashMap<>();

        this.scope = new CLRtScope();
    }

    public void execute() {
        while (this.pc < this.codeObject.instructions.size()) {
            CLIR instruction = this.codeObject.getInstruction(this.pc);
            instruction.execute(this);
            this.pc++;
        }
    }
}
