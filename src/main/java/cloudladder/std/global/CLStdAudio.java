package cloudladder.std.global;

import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
public class CLStdAudio {
    @CLBuiltinFuncAnnotation(value = {"path"}, name = "new")
    public static void __new__(CLRtEnvironment env) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
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
    @CLBuiltinFuncAnnotation(value={"self"}, name="binary")
    public static void __binary__(CLRtEnvironment env) {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        String s = self.binary();

        CLString str = new CLString(s);
        str.setExts(env.getVariable("String"));
        env.ret(str.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self"}, name="play")
    public static void __play__(CLRtEnvironment env) throws IOException, InterruptedException {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        self.play();
        env.ret(self.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self"}, name="stop")
    public static void __stop__(CLRtEnvironment env)
    {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        self.stop();
        env.ret(self.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self"}, name="continues")
    public static void __continues__(CLRtEnvironment env)
    {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        self.continues();
        env.ret(self.wrap());
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

