package cloudladder.core.runtime.data;

import ij.IJ;
import ij.ImagePlus;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.Base64;

@Getter
public class CLImage extends CLData {
    private final ImagePlus image;
    private final Path path;
    private final boolean fromFile;

    private CLImage(ImagePlus img){
        this.image = img;
        this.path = null;
        this.fromFile = false;
    }

    public CLImage(Path path) {
        this.typeName = "image";
        this.path = path;
        this.fromFile = true;
        image = IJ.openImage(path.toString());
    }

    public static CLImage fromBase64(String code) {
        byte[] data = Base64.getDecoder().decode(code);

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
            ImagePlus ip = new ImagePlus("title", image);
            return new CLImage(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void show() {
        image.show();
    }

    public void save(Path path) {
        File file = new File(path.toUri());

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image.getBufferedImage(), "png", stream);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            stream.writeTo(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String base64() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image.getBufferedImage(), "png", stream);

            String temp = Base64.getEncoder().encodeToString(stream.toByteArray());
            return URLEncoder.encode(temp, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public String toString() {
        if (this.fromFile) {
            return "Image[" + this.path.toString() + "]";
        } else {
            return "Image[" + this.image.toString() + "]";
        }
    }
}
