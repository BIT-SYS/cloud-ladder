package cloudladder.std.global;

import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;

import java.lang.reflect.Method;
import java.nio.file.Path;

public class CLStdText {
    @CLBuiltinFuncAnnotation(value={"path"}, name="new")
    public static void __new__(CLRtEnvironment env) {
        CLData path = env.getVariable("path").getReferee();
        if (!(path instanceof CLString)) {
            // todo err
        }

        String str = ((CLString) path).getValue();
        Path p = env.getWd().resolve(str);
        CLText text = new CLText(p.toString());

        text.setExts(env.getVariable("Text"));
        env.ret(text.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="getContent")
    public static void __getContent__(CLRtEnvironment env) {
        CLText self = (CLText) env.getVariable("self").getReferee();

        CLString str = new CLString(self.getContent());

        str.setExts(env.getVariable("String"));
        env.ret(str.wrap());
    }

    public static void run(CLRtEnvironment env) throws Exception {
        Class clazz = CLStdText.class;
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
        env.addVariable("Text", obj.wrap());
    }
}
