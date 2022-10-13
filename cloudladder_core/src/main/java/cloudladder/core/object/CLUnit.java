package cloudladder.core.object;

@CLObjectAnnotation(typeIdentifier = "unit")
public class CLUnit extends CLObject {
    private static final CLUnit ins = new CLUnit();

    public static CLUnit getInstance() {
        return CLUnit.ins;
    }
    // a dummy object


    @Override
    public String getTypeIdentifier() {
        return "unit";
    }

    @Override
    public String defaultStringify() {
        return "unit";
    }
}
