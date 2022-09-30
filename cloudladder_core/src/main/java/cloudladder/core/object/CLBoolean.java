package cloudladder.core.object;

@CLObjectAnnotation(typeIdentifier = "boolean")
public class CLBoolean extends CLObject {
    public boolean value;

    public static final CLBoolean boolTrue = new CLBoolean(true);
    public static final CLBoolean boolFalse = new CLBoolean(false);

    public static CLBoolean get(boolean v) {
        if (v) {
            return CLBoolean.boolTrue;
        } else {
            return CLBoolean.boolFalse;
        }
    }

    private CLBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public CLObject not() {
        return CLBoolean.get(!this.value);
    }
}
