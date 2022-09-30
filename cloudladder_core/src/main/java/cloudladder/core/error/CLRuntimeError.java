package cloudladder.core.error;

public class CLRuntimeError extends Exception {
    public CLRuntimeError(CLRuntimeErrorType type, String message) {
        super("CLRuntimeError(" + type.toString() + "): " + message);
    }
}
