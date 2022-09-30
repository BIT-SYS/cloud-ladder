package cloudladder.converter;

public class ConverterFactory {
    public static StringConverter stringConverter = new StringConverter();

    public static Converter getConverter(String name) {
        return ConverterFactory.stringConverter;
    }
}
