package cloudladder.core.object;

@CLObjectAnnotation(typeIdentifier = "audio")
public class CLAudio extends CLObject {
    @Override
    public String getTypeIdentifier() {
        return "audio";
    }

    @Override
    public String defaultStringify() {
        return "audio@" + this.id;
    }
}
