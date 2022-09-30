package cloudladder.converter;

import cloudladder.core.object.CLObject;

public interface Converter {
    public abstract CLObject convert(byte[] bytes);

    public abstract byte[] convertBack(CLObject object);
}
