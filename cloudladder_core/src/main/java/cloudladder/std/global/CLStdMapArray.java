package cloudladder.std.global;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLArray;
import cloudladder.core.object.CLMapArray;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;

@CLStdLibAnnotation(name = "MapArray")
public class CLStdMapArray {
    @CLBuiltinFuncAnnotation(params = {"map_array"}, name = "keys")
    public static CLArray __keys__(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.scope.getOwnVariable("map_array");
        if (!(object instanceof CLMapArray mapArray)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting map_array, got `" + object.getTypeIdentifier() + "`");
        }

        CLArray ret = new CLArray();
        for (String k: mapArray.data.keySet()) {
            CLString s = new CLString(k);
            ret.addItem(s);
        }

        return ret;
    }
}
