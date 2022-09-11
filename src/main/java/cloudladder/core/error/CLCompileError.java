package cloudladder.core.error;

public class CLCompileError extends Exception {
    public CLCompileError(CLCompileErrorType type, String message) {
        super("CLCompileError(" + type.toString() + "): " + message);
    }
}
