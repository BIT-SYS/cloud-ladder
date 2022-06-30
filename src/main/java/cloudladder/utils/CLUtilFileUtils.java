package cloudladder.utils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

public class CLUtilFileUtils {
    public static String loadFile(Path path) {
        try {
            File file = new File(path.toUri());
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] b = inputStream.readAllBytes();
                return new String(b,"utf8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
