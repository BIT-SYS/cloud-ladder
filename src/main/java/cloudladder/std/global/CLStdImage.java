package cloudladder.std.global;

import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

public class CLStdImage {
    @CLBuiltinFuncAnnotation(value={"path"}, name="new")
    public static void __new__(CLRtEnvironment env) {
        CLData path = env.getVariable("path").getReferee();
        if (!(path instanceof CLString)) {
            // todo err
        }

        String str = ((CLString) path).getValue();
        Path p = env.getWd().resolve(str);
        CLImage image = new CLImage(p);

        image.setExts(env.getVariable("Image"));
        env.ret(image.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="toCaptionText")
    public static void __toCaptionText__(CLRtEnvironment env) throws IOException {
        CLImage img = (CLImage) env.getVariable("self").getReferee();
        File file = null;
        if (img.isFromFile()) {
            Path path = img.getPath();
            file = new File(path.toString());
        }
        else {
            file = File.createTempFile(UUID.randomUUID().toString(), ".png");
            img.save(Path.of(file.getPath()));
        }
        CLString str = new CLString(img.postToUrl("http://127.0.0.1:5000/inference", file));
        env.ret(str.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="base64")
    public static void __base64__(CLRtEnvironment env) {
        CLImage self = (CLImage) env.getVariable("self").getReferee();
        String s = self.base64();

        CLString str = new CLString(s);
        str.setExts(env.getVariable("String"));
        env.ret(str.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="show")
    public static void __show__(CLRtEnvironment env) {
        CLImage self = (CLImage) env.getVariable("self").getReferee();
        self.show();

        env.ret(self.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"base64"}, name="fromBase64")
    public static void __fromBase64__(CLRtEnvironment env) {
        CLData p1 = env.getOwnVariable("base64").getReferee();

        String base64 = ((CLString) p1).getValue();

        CLImage image = CLImage.fromBase64(base64);
        image.setExts(env.getVariable("Image"));
        env.ret(image.wrap());
    }

    public static void run(CLRtEnvironment env) throws Exception {
        Class clazz = CLStdImage.class;
        CLObject obj = new CLObject();
        CLRtScope scope = env.getCurrentScope();

        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("__")) {
                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String[] params = annotation.value();
                String name = annotation.name();

                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
                obj.addStringRefer(name, func.wrap());
            }
        }

        env.addVariable("Image", obj.wrap());
    }
}
