package cloudladder.std.global;

import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;

import java.lang.reflect.Method;

public class CLStdNumber {
    private static CLNumber getSelf(CLRtEnvironment env) {
        return (CLNumber) env.getVariable("self").getReferee();
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="toString")
    public static void __toString__(CLRtEnvironment env) {
        CLNumber self = (CLNumber) env.getVariable("self").getReferee();
        double number = self.getValue();
        String str = Double.toString(number);
        CLString clString = new CLString(str);

        clString.setExts(env.getVariable("String"));

        env.ret(clString.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self", "root"}, name="root")
    public static void __root__(CLRtEnvironment env) {
        CLNumber self = getSelf(env);

        if (!env.hasOwnVariable("root")) {
            double ans = Math.sqrt(self.getValue());
            CLNumber ret = new CLNumber(ans);
            ret.setExts(env.getVariable("Number"));
            env.ret(ret.wrap());
            return;
        }

        CLData root = env.getVariable("root").getReferee();

        if (!(root instanceof CLNumber)) {
            // todo
            env.ret();
            return;
        }

        double ans = Math.pow(self.getValue(), 1.0 / ((CLNumber) root).getValue());
        CLNumber ret = new CLNumber(ans);
        ret.setExts(env.getVariable("Number"));
        env.ret(ret.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self", "pow"}, name="pow")
    public static void __pow__(CLRtEnvironment env) {
        CLNumber number = (CLNumber) env.getVariable("self").getReferee();
        CLNumber pow = (CLNumber) env.getVariable("pow").getReferee();

        double value = Math.pow(number.getValue(), pow.getValue());

        CLNumber ret = new CLNumber(value);
        env.ret(ret.wrap());
    }

    public static void run(CLRtEnvironment env) throws Exception {
        Class clazz = CLStdNumber.class;
        CLRtScope scope = env.getCurrentScope();
        CLObject obj = env.newObject();

        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("__")) {
                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String[] params = annotation.value();
                String name = annotation.name();

                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
                obj.addStringRefer(name, func.wrap());
            }
        }

        env.addVariable("Number", obj.wrap());
    }
}
