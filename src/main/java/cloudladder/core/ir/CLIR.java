package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.runtime.CLRtFrame;

public abstract class CLIR {
    public abstract void execute(CLRtFrame frame) throws CLRuntimeError;
}
