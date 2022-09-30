//package cloudladder.core.runtime.data;
//
//import ij.IJ;
//import ij.ImagePlus;
//import lombok.Getter;
//import okhttp3.*;
//
//import javax.imageio.ImageIO;
//import javax.print.attribute.standard.Media;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.net.MulticastSocket;
//import java.net.URLEncoder;
//import java.nio.file.Path;
//import java.util.Base64;
//
//@Getter
//public class CLImage extends CLData {
//    private final ImagePlus image;
//    private final Path path;
//    private final boolean fromFile;
//
//    private CLImage(ImagePlus img){
//        this.image = img;
//        this.path = null;
//        this.fromFile = false;
//    }
//
//    public CLImage(Path path) {
//        this.typeName = "image";
//        this.path = path;
//        this.fromFile = true;
//        image = IJ.openImage(path.toString());
//    }
//
//    public static CLImage fromBase64(String code) {
//        byte[] data = Base64.getDecoder().decode(code);
//
//        try {
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
//            ImagePlus ip = new ImagePlus("title", image);
//            return new CLImage(ip);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public void show() {
//        image.show();
//    }
//
//    public void save(Path path) {
//        File file = new File(path.toUri());
//
//        try {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            ImageIO.write(image.getBufferedImage(), "png", stream);
//
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            stream.writeTo(fileOutputStream);
//            fileOutputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String postToUrl(String url, File file) throws IOException {
//        final MediaType MEDIA_TYPE = MediaType.parse("image/png");
//        final OkHttpClient client = new OkHttpClient();
//
//        RequestBody fileBody = RequestBody.create(MEDIA_TYPE, file);
//        RequestBody reqBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("image", "image.png", fileBody)
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(reqBody)
//                .build();
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
//
//    public String base64() {
//        try {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            ImageIO.write(image.getBufferedImage(), "png", stream);
//
//            String temp = Base64.getEncoder().encodeToString(stream.toByteArray());
//            return URLEncoder.encode(temp, "GBK");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }
//
//    @Override
//    public String toString() {
//        if (this.fromFile) {
//            return "Image[" + this.path.toString() + "]";
//        } else {
//            return "Image[" + this.image.toString() + "]";
//        }
//    }
//}
