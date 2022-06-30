package cloudladder.core.runtime.env;

import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLReference;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class CLRtScope {
    private final HashMap<String, CLReference> variables;
    @Setter
    @Getter
    private CLRtScope parent;

    public CLRtScope(CLRtScope parent) {
        this.variables = new HashMap<>();
        this.parent = parent;
    }

    public boolean hasOwnVariable(String key) {
        return this.variables.containsKey(key);
    }

    public boolean hasVariable(String key) {
        if (this.hasOwnVariable(key)) {
            return true;
        }

        if (this.parent != null) {
            return this.parent.hasVariable(key);
        }

        return false;
    }

    public void addVariable(String key, CLReference item) {
        this.variables.put(key, item);
    }

    public CLReference getVariable(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        } else {
            if (parent != null) {
                return parent.getVariable(key);
            }
        }

        return null;
    }

    public CLReference getOwnVariable(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        }
        return null;
    }
}
