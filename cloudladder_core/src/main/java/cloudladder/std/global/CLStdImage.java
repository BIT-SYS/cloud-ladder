package cloudladder.std.global;
//
//import cloudladder.core.runtime.data.*;
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import cloudladder.core.runtime.env.CLRtScope;
//import cloudladder.std.CLBuiltinFuncAnnotation;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.nio.file.Path;
//import java.util.Base64;
//import java.util.UUID;
//
//public class CLStdImage {
//    @CLBuiltinFuncAnnotation(value={"path"}, name="new")
//    public static void __new__(CLRtEnvironment env) {
//        CLData path = env.getVariable("path").getReferee();
//        if (!(path instanceof CLString)) {
//            // todo err
//        }
//
//        String str = ((CLString) path).getValue();
//        Path p = env.getWd().resolve(str);
//        CLImage image = new CLImage(p);
//
//        image.setExts(env.getVariable("Image"));
//        env.ret(image.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self"}, name="toCaptionText")
//    public static void __toCaptionText__(CLRtEnvironment env) throws IOException {
//        CLImage img = (CLImage) env.getVariable("self").getReferee();
//        File file = null;
//        if (img.isFromFile()) {
//            Path path = img.getPath();
//            file = new File(path.toString());
//        }
//        else {
//            file = File.createTempFile(UUID.randomUUID().toString(), ".png");
//            img.save(Path.of(file.getPath()));
//        }
////        CLString str = new CLString(img.postToUrl("http://127.0.0.1:5000/inference", file));
//        CLString str = new CLString(img.postToUrl("http://39.103.135.92:5000/inference", file));
//        env.ret(str.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self"}, name="base64")
//    public static void __base64__(CLRtEnvironment env) {
//        CLImage self = (CLImage) env.getVariable("self").getReferee();
//        String s = self.base64();
//
//        CLString str = new CLString(s);
//        str.setExts(env.getVariable("String"));
//        env.ret(str.wrap());
//    }
//
//
//    @CLBuiltinFuncAnnotation(value={"base64"}, name="fromBase64")
//    public static void __fromBase64__(CLRtEnvironment env) {
//        CLData p1 = env.getOwnVariable("base64").getReferee();
//
//        String base64 = ((CLString) p1).getValue();
//
//        CLImage image = CLImage.fromBase64(base64);
//        image.setExts(env.getVariable("Image"));
//        env.ret(image.wrap());
//    }
//
//}

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLImage;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;

@CLStdLibAnnotation(name = "Image")
public class CLStdImage {
    @CLBuiltinFuncAnnotation(params = {"img"}, name = "show")
    public static CLObject __show__(CLRtFrame frame) throws CLRuntimeError {
        CLObject img = frame.scope.getOwnVariable("img");
        if (!(img instanceof CLImage image)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting image");
        }

        image.image.show();
        return frame.vm.unitObject;
    }

    @CLBuiltinFuncAnnotation(params = {"img"}, name = "base64")
    public static CLObject __base64__(CLRtFrame frame) throws CLRuntimeError {
        CLObject img = frame.scope.getOwnVariable("img");
        if (!(img instanceof CLImage image)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting image");
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image.image.getBufferedImage(), "png", stream);

            String temp = Base64.getEncoder().encodeToString(stream.toByteArray());
            String result = URLEncoder.encode(temp, "GBK");
            return new CLString(result);
        } catch (IOException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "convert to base64 error");
        }
    }
}
