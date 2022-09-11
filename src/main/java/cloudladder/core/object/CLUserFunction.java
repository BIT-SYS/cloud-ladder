package cloudladder.core.object;

import cloudladder.core.runtime.CLRtFrame;

import java.util.ArrayList;
import java.util.HashMap;

public class CLUserFunction extends CLFunction {
    public CLCodeObject codeObject;
    public HashMap<String, CLObject> caughtObjects;

    public CLUserFunction(CLCodeObject codeObject, ArrayList<String> params, boolean catchAll, HashMap<String, CLObject> caughtObjects) {
        super(params, catchAll);
        this.codeObject = codeObject;
        this.caughtObjects = caughtObjects;
    }
}
