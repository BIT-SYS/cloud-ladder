package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.runtime.CLRtFrame;

import java.util.ArrayList;
import java.util.HashMap;

@CLObjectAnnotation(typeIdentifier = "function")
public class CLUserFunction extends CLFunction {
    public CLCodeObject codeObject;
    public HashMap<String, CLObject> caughtObjects;

    public CLUserFunction(CLCodeObject codeObject, ArrayList<String> params, boolean catchAll, HashMap<String, CLObject> caughtObjects) {
        super(params, catchAll);
        this.codeObject = codeObject;
        this.caughtObjects = caughtObjects;
    }

    @Override
    public CLObject execute(CLRtFrame frame) throws CLRuntimeError {
        frame.execute();

        CLObject retValue = frame.vm.stack.pop();
        return retValue;
    }

    @Override
    public String getTypeIdentifier() {
        return "function";
    }

    @Override
    public String defaultStringify() {
        return "user_function@" + this.id;
    }
}
