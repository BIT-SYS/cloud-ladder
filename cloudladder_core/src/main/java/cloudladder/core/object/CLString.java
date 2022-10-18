package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@CLObjectAnnotation(typeIdentifier = "string")
public class CLString extends CLObject {
    public String value;

    @Override
    public String getTypeIdentifier() {
        return "string";
    }

    @Override
    public String defaultStringify() {
        return this.value;
    }

    @Override
    public CLObject eq(CLObject other) throws CLRuntimeError {
        if (!(other instanceof CLString s)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string, got `" + other.getTypeIdentifier() + "`");
        }
        return CLBoolean.get(this.value.equals(s.value));
    }
}
