package cloudladder.core.runtime;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLObject;

import java.util.HashMap;

public class CLRtScope {
    public CLRtScope parent = null;
    private final HashMap<String, CLObject> objects = new HashMap<>();

    public CLObject getOwnVariable(String name) {
        //        if (temp == null) {
//            throw new CLRuntimeError(CLRuntimeErrorType.VariableNotFound, "name `" + name + "` not found");
//        }
        return this.objects.get(name);
    }

    public boolean hasOwnVariable(String name) {
        return this.objects.containsKey(name);
    }

    public CLObject getVariable(String name) {
        CLObject temp = this.getOwnVariable(name);
        if (temp != null) {
            return temp;
        }
        if (this.parent != null) {
            return this.parent.getVariable(name);
        }
        return null;
    }

    public void addVariable(String name, CLObject variable) {
        this.objects.put(name, variable);
    }

    public HashMap<String, CLObject> getNameMapping() {
        return this.objects;
    }
}
