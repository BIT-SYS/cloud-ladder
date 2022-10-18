package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;

import java.util.ArrayList;

@CLObjectAnnotation(typeIdentifier = "array")
public class CLArray extends CLObject {
    public ArrayList<CLObject> items;

    public CLArray() {
        this.items = new ArrayList<>();
    }

    public void addItem(CLObject item) {
        this.items.add(item);
    }

    public int size() {
        return this.items.size();
    }

    @Override
    public String getTypeIdentifier() {
        return "array";
    }

    @Override
    public String defaultStringify() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (CLObject o: this.items) {
            sb.append(o.defaultStringify()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return new String(sb);
    }

    @Override
    public CLObject add(CLObject other) throws CLRuntimeError {
        if (other instanceof CLArray arr) {
            CLArray newArray = new CLArray();
            for (CLObject o: this.items) {
                newArray.addItem(o);
            }
            for (CLObject o: arr.items) {
                newArray.addItem(o);
            }
            return newArray;
        } else {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array, got `" + other.getTypeIdentifier() + "`");
        }
    }
}
