package cloudladder.std.encode.JSON;

import cloudladder.core.runtime.data.CLBuiltinFunction;
import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLObject;
import cloudladder.core.runtime.data.CLString;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.utils.CLDataUtils;

import java.lang.reflect.Method;

public class CLStdJSON {
    @CLBuiltinFuncAnnotation(value={"json"}, name="parse")
    public static void __parse__(CLRtEnvironment env) {
        CLData obj = env.getOwnVariable("json").getReferee();

        String json = ((CLString) obj).getValue();

        CLData parsed = CLDataUtils.fromJson(json, env);

        env.ret(parsed.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"data"}, name="stringify")
    public static void __stringify__(CLRtEnvironment env) {
        CLData obj = env.getOwnVariable("data").getReferee();

        String json = CLDataUtils.toJson(obj);
        CLString str = env.newString(json);

        env.ret(str.wrap());
    }

    public static void run(CLRtEnvironment env) {
        CLObject obj = env.newObject();
        Class clazz = CLStdJSON.class;
        CLRtScope scope = env.getCurrentScope();

        for (Method method: clazz.getMethods()) {
            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String[] params = annotation.value();
                String name = annotation.name();

                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
                obj.addStringRefer(name, func.wrap());
            }
        }

        env.addVariable("$exports", obj.wrap());
    }
}
