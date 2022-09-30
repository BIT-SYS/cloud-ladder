package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;

import java.util.HashMap;
import java.util.WeakHashMap;

@CLObjectAnnotation(typeIdentifier = "object")
public abstract class CLObject {
    private static int NEXT_ID = 0;

    public int id;

    public CLObject() {
        this.id = CLObject.NEXT_ID;
        CLObject.NEXT_ID += 1;
    }

    public CLObject neg() throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "neg not implemented");
    }

    public CLObject not() throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "`not` not implemented");
    }

    public CLObject add(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "add not implemented");
    }

    public CLObject sub(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "sub not implemented");
    }

    public CLObject mul(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "mul not implemented");
    }

    public CLObject div(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "div not implemented");
    }

    public CLObject mod(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "mod not implemented");
    }

    public CLObject eq(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "eq not implemented");
    }

    public CLObject ne(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "ne not implemented");
    }

    public CLObject gt(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "gt not implemented");
    }

    public CLObject ge(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "ge not implemented");
    }

    public CLObject lt(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "lt not implemented");
    }

    public CLObject le(CLObject other) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "le not implemented");
    }

    public CLObject indexing(CLObject index) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "`indexing` not implemented");
    }

    public void setValue(CLObject index, CLObject value) throws CLRuntimeError {
        throw new CLRuntimeError(CLRuntimeErrorType.Unimplemented, "`setValue` not implemented");
    }
}
