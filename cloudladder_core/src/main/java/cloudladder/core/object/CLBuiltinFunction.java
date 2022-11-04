package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.runtime.CLRtFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@CLObjectAnnotation(typeIdentifier = "function")
public class CLBuiltinFunction extends CLFunction {
    private final Method method;

    public CLBuiltinFunction(Method method, ArrayList<String> paramNames, boolean catchAll) {
        super(paramNames, catchAll);
        this.method = method;
    }

    @Override
    public CLObject execute(CLRtFrame frame) throws CLRuntimeError {
        try {
            return (CLObject) this.method.invoke(null, frame);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "method invocation error");
        }
    }

    @Override
    public String getTypeIdentifier() {
        return "function";
    }

    @Override
    public String defaultStringify() {
        return "builtin_function@" + this.id;
    }
}
