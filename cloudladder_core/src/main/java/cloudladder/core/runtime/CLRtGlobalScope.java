package cloudladder.core.runtime;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.std.global.CLStdArray;
import cloudladder.std.global.CLStdGlobalFunction;
import cloudladder.std.global.GlobalInjector;

public class CLRtGlobalScope extends CLRtScope {
    public CLRtGlobalScope() throws CLRuntimeError {
        GlobalInjector.inject(this);
    }
}
