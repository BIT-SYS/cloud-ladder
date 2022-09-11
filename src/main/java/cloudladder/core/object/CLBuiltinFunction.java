package cloudladder.core.object;

import cloudladder.core.runtime.CLRtFrame;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class CLBuiltinFunction extends CLFunction {
    private final Method method;

    public CLBuiltinFunction(Method method, ArrayList<String> paramNames, boolean catchAll) {
        super(paramNames, catchAll);
        this.method = method;
    }

    public CLObject execute(CLRtFrame frame) {
        try {
            return (CLObject) this.method.invoke(null, frame);
        } catch (Exception e) {
            System.out.println(e);
        }

        return frame.vm.unitObject;
    }
}
