package cloudladder.std.lib;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLDict;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;
import cloudladder.std.global.CLStdGlobalFunction;

@CLStdLibAnnotation(name = "json")
public class CLStdLibJson {
    @CLBuiltinFuncAnnotation(name = "stringify", params = {"s"})
    public static CLObject jsonEncode(CLRtFrame frame) throws CLRuntimeError {
        CLObject s = frame.scope.getOwnVariable("s");
        if (!(s instanceof CLDict dict)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting dict");
        }

        String ans = CLStdGlobalFunction.toString(s);
        return new CLString(ans);
    }
}
