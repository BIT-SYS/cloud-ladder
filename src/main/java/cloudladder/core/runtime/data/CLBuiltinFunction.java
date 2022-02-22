package cloudladder.core.runtime.data;

import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;

import java.lang.reflect.Method;

public class CLBuiltinFunction extends CLFunction {
    private final Method method;

    public CLBuiltinFunction(Method method) {
        this.method = method;
    }

    public CLBuiltinFunction(Method method, CLRtScope parent, String ...params) {
        this.method = method;
        this.setParentScope(parent);
        for (String p : params) {
            this.addParam(p);
        }
    }

    @Override
    public void execute(CLRtEnvironment env) {
        try {
            method.invoke(null, env);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "f[" + this.method.getName() + "]";
    }
}
