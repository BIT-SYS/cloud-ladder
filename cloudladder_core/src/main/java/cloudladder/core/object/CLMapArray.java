package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CLMapArray extends CLObject {
    public HashMap<String, CLArray> data;

    public CLMapArray() {
        super();
        this.data = new HashMap<>();
    }

    @Override
    public CLObject indexing(CLObject index) throws CLRuntimeError {
        if (index instanceof CLString s) {
            String str = s.value;
            if (this.data.containsKey(str)) {
                return this.data.get(str);
            } else {
                return CLUnit.getInstance();
            }
        } else {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string");
        }
    }

    @Override
    public void setValue(CLObject index, CLObject value) throws CLRuntimeError {
        if ((index instanceof CLString s) && (value instanceof CLArray arr)) {
            this.data.put(s.value, arr);
        } else {
            if (!(index instanceof CLString)) {
                throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string as key");
            } else {
                throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array as value");
            }
        }
    }

    @Override
    public String getTypeIdentifier() {
        return "map_array";
    }

    @Override
    public String defaultStringify() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String key: this.data.keySet()) {
            CLArray value = this.data.get(key);
            sb.append(key).append(": ").append(value.defaultStringify()).append(", ");
        }
        if (sb.length() != 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return new String(sb);
    }
}
