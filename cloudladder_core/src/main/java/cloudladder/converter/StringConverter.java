package cloudladder.converter;

import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;

public class StringConverter implements Converter {
    @Override
    public CLObject convert(byte[] bytes) {
        String result = new String(bytes);
        return new CLString(result);
    }

    @Override
    public byte[] convertBack(CLObject object) {
        String result = object.toString();
        return result.getBytes();
    }
}
