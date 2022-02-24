package cloudladder.core.runtime.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;

@AllArgsConstructor
@Getter
public class CLStream extends CLData {
    private final InputStream stream;

    @Override
    public String toString() {
        return "[[stream]]";
    }

    public void save(Path path) {
        File file = new File(path.toUri());

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(stream.readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
