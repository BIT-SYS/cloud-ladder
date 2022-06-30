package cloudladder.core.runtime.env;

public class CLRtFunctionStackFrame extends CLRtStackFrame {
    String funcName;

    public CLRtFunctionStackFrame(CLRtScope scope, String funcName) {
        super(scope);

        this.funcName = funcName;
    }

    @Override
    public String getFrameDescription() {
        return "function(" + this.funcName + ")";
    }
}
