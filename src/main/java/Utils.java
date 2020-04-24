public class Utils {
    public static void err(String errType, String errMsg) {
        System.err.println(errType + "\n\t" + errMsg);
        System.exit(0);
    }
}
