package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;

import java.util.ArrayList;
import java.util.HashMap;

public class CLArrayMap extends CLObject {
    private ArrayList<CLDict> data;

    public CLArrayMap() {
        this.data = new ArrayList<>();
    }

    @Override
    public String getTypeIdentifier() {
        return "array_map";
    }

    @Override
    public String defaultStringify() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (CLDict item: this.data) {
            sb.append(item.defaultStringify()).append(", ");
        }
        if (sb.length() != 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return new String(sb);
    }

    @Override
    public CLObject indexing(CLObject index) throws CLRuntimeError {
        if (!(index instanceof CLNumber n)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "MapArray can only be indexed using number, got `" + index.getTypeIdentifier() + "`");
        }
        if (!n.isInteger()) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting integer");
        }

        return this.data.get(n.getAsInt());
    }

    @Override
    public void setValue(CLObject index, CLObject value) throws CLRuntimeError {
        if (!(index instanceof CLNumber n)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "MapArray can only be indexed using number, got `" + index.getTypeIdentifier() + "`");
        }
        if (!n.isInteger()) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting integer");
        }

        if (!(value instanceof CLDict)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting dict, found `" + value.getTypeIdentifier() + "`");
        }

        int i = n.getAsInt();

        if (this.data.size() <= i) {
            throw new CLRuntimeError(CLRuntimeErrorType.IndexError, "indexing using " + i + "/" + this.data.size());
        }
        this.data.set(i, (CLDict) value);
    }
}
