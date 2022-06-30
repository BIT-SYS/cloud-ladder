package cloudladder.std.global;

import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;

import java.lang.reflect.Method;
import java.nio.file.Path;

public class CLStdAudio {
    @CLBuiltinFuncAnnotation(value={"self"}, name="play")
    public static void __play__(CLRtEnvironment env) {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        self.play();
        env.ret(self.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"path"}, name="new")
    public static void __new__(CLRtEnvironment env) {
        CLData path = env.getVariable("path").getReferee();
        if (!(path instanceof CLString)) {
            // todo err
        }

        String str = ((CLString) path).getValue();
        Path p = env.getWd().resolve(str);
        CLAudio audio = new CLAudio(p);

        audio.setExts(env.getVariable("Audio"));
        env.ret(audio.wrap());
    }

    public static void run(CLRtEnvironment env) throws Exception {
        Class clazz = CLStdAudio.class;
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

        env.addVariable("Audio", obj.wrap());
    }
}
