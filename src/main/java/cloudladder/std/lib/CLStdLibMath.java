package cloudladder.std.lib;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLDict;
import cloudladder.core.object.CLNumber;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinConstantAnnotation;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;

@CLStdLibAnnotation(name = "math")
public class CLStdLibMath {
    @CLBuiltinFuncAnnotation(params = "x", name = "sin")
    public static CLObject __sin__(CLRtFrame frame) throws CLRuntimeError {
        CLObject x = frame.scope.getOwnVariable("x");
        if (x instanceof CLNumber n) {
            return CLNumber.getNumberObject(Math.sin(n.value));
        }
        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @CLBuiltinConstantAnnotation(name = "PI")
    public static CLNumber __pi__() {
        return CLNumber.getNumberObject(Math.PI);
    }
}
