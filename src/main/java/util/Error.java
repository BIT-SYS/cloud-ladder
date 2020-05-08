package util;

public class Error {
    public static void die(String errType, String errMsg) {
        System.err.println(errType + "\n\t" + errMsg);
        System.exit(0);
    }
}
