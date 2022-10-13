package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;

import java.util.HashMap;

@CLObjectAnnotation(typeIdentifier = "dict")
public class CLDict extends CLObject {
    public final HashMap<String, CLObject> contents;

    public CLDict() {
        this.contents = new HashMap<>();
    }

    public void put(String key, CLObject value) {
        this.contents.put(key, value);
    }

    @Override
    public String toString() {
        return "dict";
    }

    @Override
    public CLObject indexing(CLObject index) throws CLRuntimeError {
        if (index instanceof CLString s) {
            if (this.contents.containsKey(s.value)) {
                return this.contents.get(s.value);
            } else {
                throw new CLRuntimeError(CLRuntimeErrorType.MemberNotFound, "member `" + s.value + "` not found");
            }
        } else {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "dict can only be indexed with string");
        }
    }

    @Override
    public String getTypeIdentifier() {
        return "dict";
    }

    @Override
    public String defaultStringify() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String key: this.contents.keySet()) {
            CLObject value = this.contents.get(key);
            sb.append(key).append(": ").append(value.defaultStringify()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return new String(sb);
    }
}
