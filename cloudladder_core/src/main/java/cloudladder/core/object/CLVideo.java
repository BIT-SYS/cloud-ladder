package cloudladder.core.object;

// todo

public class CLVideo extends CLObject {
    @Override
    public String getTypeIdentifier() {
        return "video";
    }

    @Override
    public String defaultStringify() {
        return "video@" + this.id;
    }
}
