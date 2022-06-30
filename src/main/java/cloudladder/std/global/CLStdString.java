package cloudladder.std.global;

import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class CLStdString {
    @CLBuiltinFuncAnnotation(value={"self"}, name="length")
    public static void __length__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        int length = self.getValue().length();

        CLNumber ret = env.newNumber(length);
        env.ret(ret.wrap());
    }

//    @CLBuiltinFuncAnnotation(value={"str"}, name="trim")
//    public static void __trim__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("this").getReferee();
//        String str = self.getValue();
//        String result;
//
//        if (env.hasOwnVariable("str")) {
//            CLString p1 = (CLString) env.getVariable("str").getReferee();
//            String trim = p1.getValue();
//            result = "";
//        } else {
//            result = str.trim();
//        }
//
//        CLString ret = new CLString(result);
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"str"}, name="trimStart")
//    public static void __trimStart__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("this").getReferee();
//        String str = self.getValue();
//
//        String result;
//        if (!env.checkParamType("str", CLString.class)) {
//            result = str.replaceFirst("\\s+", "");
//        } else {
//            CLString p1 = (CLString) env.getVariable("str").getReferee();
//            String s = p1.getValue();
//            result = str;
//            while (result.startsWith(s)) {
//                result = result.replaceFirst(s, "");
//            }
//        }
//
//        CLString ret = env.newString(result);
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"str"}, name="trimEnd")
//    public static void __trimEnd__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("this").getReferee();
//        String str = self.getValue();
//
//        String result;
//        if (!env.checkParamType("str", CLString.class)) {
//            result = str.replaceFirst("\\s+$", "");
//        } else {
//            CLString p1 = (CLString) env.getVariable("str").getReferee();
//            String s = p1.getValue();
//            result = str;
//            while (result.endsWith(s)) {
//                result = result.replaceFirst(s + "$", "");
//            }
//        }
//
//        CLString ret = env.newString(result);
//        env.ret(ret.wrap());
//    }

    @CLBuiltinFuncAnnotation(value={"self", "sep"}, name="split")
    public static void __split__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        if (!env.hasOwnVariable("sep")) {
            String[] ans = str.trim().split("\\s+");
            CLArray ret = env.newArray();
            for (String s : ans) {
                CLString item = env.newString(s);
                ret.addNumberRefer(item.wrap());
            }
            env.ret(ret.wrap());
        } else {
            // split by "sep"
        }
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="lines")
    public static void __lines__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();
        List<String> lines = str.lines().collect(Collectors.toList());

        CLArray ret = env.newArray();
        for (String line : lines) {
            CLString item = env.newString(line);
            ret.addNumberRefer(item.wrap());
        }

        env.ret(ret.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="chars")
    public static void __chars__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        CLArray arr = env.newArray();
        for (int i = 0, l = str.length(); i < l; i++) {
            char c = str.charAt(i);
            CLString item = env.newString(Character.toString(c));
            arr.addNumberRefer(item.wrap());
        }

        env.ret(arr.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self"}, name="bytes")
    public static void __bytes__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        CLArray arr = env.newArray();
        for (byte b : str.getBytes()) {
            CLNumber item = env.newNumber(b);
            arr.addNumberRefer(item.wrap());
        }

        env.ret(arr.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self", "value"}, name="startsWith")
    public static void __startsWith__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        if (!env.checkParamType("value", CLString.class)) {
            env.ret();
        }

        CLString p1 = (CLString) env.getOwnVariable("value").getReferee();
        CLBoolean ret = env.getBool(str.startsWith(p1.getValue()));
        env.ret(ret.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self", "value"}, name="endsWith")
    public static void __endsWith__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        if (!env.checkParamType("value", CLString.class)) {
            env.ret();
        }

        CLString p1 = (CLString) env.getOwnVariable("value").getReferee();
        CLBoolean ret = env.getBool(str.endsWith(p1.getValue()));
        env.ret(ret.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self", "times"}, name="repeat")
    public static void __repeat__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        if (!env.checkParamType("times", CLNumber.class)) {
            env.ret();
        }

        CLNumber times = (CLNumber) env.getOwnVariable("times").getReferee();
        CLString ret = env.newString(str.repeat(times.getIntegralValue()));
        env.ret(ret.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self", "str"}, name="cat")
    public static void __cat__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        if (!env.checkParamType("str", CLString.class)) {
            // todo
            env.ret();
        }

        CLString p1 = (CLString) env.getVariable("str").getReferee();
        CLString ret = env.newString(str + p1.getValue());
        env.ret(ret.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"self", "str1", "str2"}, name="replaceAll")
    public static void __replaceAll__(CLRtEnvironment env) {
        CLString self = (CLString) env.getVariable("self").getReferee();
        String str = self.getValue();

        if (!env.checkParamType("str1", CLString.class)) {
            // todo
            env.ret();
        }
        if (!env.checkParamType("str2", CLString.class)) {
            // todo
            env.ret();
        }

        CLString p1 = (CLString) env.getVariable("str1").getReferee();
        CLString p2 = (CLString) env.getVariable("str2").getReferee();
        CLString ret = env.newString(str.replaceAll(p1.getValue(), p2.getValue()));
        env.ret(ret.wrap());
    }

    public static void run(CLRtEnvironment env) throws Exception {
        Class clazz = CLStdString.class;
        CLObject obj = new CLObject();
        CLRtScope scope = env.getCurrentScope();

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String[] params = annotation.value();
                String name = annotation.name();

                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
                obj.addStringRefer(name, func.wrap());
            }
        }

        env.addVariable("String", obj.wrap());
    }
}
