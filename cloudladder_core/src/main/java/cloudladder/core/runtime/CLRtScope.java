package cloudladder.core.runtime;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public <T extends CLObject> T requireOwnVariableWithType(String name, Class<T> type) throws CLRuntimeError {
        CLObject obj = this.getOwnVariable(name);
        if (!type.isInstance(obj)) {
            try {
                Method method = type.getMethod("getTypeIdentifier");
                Constructor constructor = type.getConstructor();
                Object ins = constructor.newInstance();
                String typeName = (String) method.invoke(ins);
                throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting " + typeName + ", found `" + obj.getTypeIdentifier() + "`");
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "expecting in require own variable with type");
            }
        } else {
            return (T) obj;
        }
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
