package cloudladder.std.global;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLNumber;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;
//import org.apache.commons.lang3.StringUtils;

@CLStdLibAnnotation(name = "String")
public class CLStdString {
    @CLBuiltinFuncAnnotation(value={"s"}, name="length")
    public static CLNumber __length__(CLRtFrame frame) throws CLRuntimeError {
        CLObject maybeString = frame.scope.getOwnVariable("s");
        if (!(maybeString instanceof CLString s)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string, found `" + maybeString.getTypeIdentifier() + "`");
        }
        int length = s.value.length();
        return CLNumber.getNumberObject((double) length);
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

//    @CLBuiltinFuncAnnotation(value={"self", "sep"}, name="split")
//    public static void __split__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//
//        if (!env.hasOwnVariable("sep")) {
//            String[] ans = str.trim().split("\\s+");
//            CLArray ret = env.newArray();
//            for (String s : ans) {
//                CLString item = env.newString(s);
//                ret.addNumberRefer(item.wrap());
//            }
//            env.ret(ret.wrap());
//        } else {
//            // split by "sep"
//        }
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self"}, name="lines")
//    public static void __lines__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//        List<String> lines = str.lines().collect(Collectors.toList());
//
//        CLArray ret = env.newArray();
//        for (String line : lines) {
//            CLString item = env.newString(line);
//            ret.addNumberRefer(item.wrap());
//        }
//
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self"}, name="chars")
//    public static void __chars__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//
//        CLArray arr = env.newArray();
//        for (int i = 0, l = str.length(); i < l; i++) {
//            char c = str.charAt(i);
//            CLString item = env.newString(Character.toString(c));
//            arr.addNumberRefer(item.wrap());
//        }
//
//        env.ret(arr.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self"}, name="bytes")
//    public static void __bytes__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//
//        CLArray arr = env.newArray();
//        for (byte b : str.getBytes()) {
//            CLNumber item = env.newNumber(b);
//            arr.addNumberRefer(item.wrap());
//        }
//
//        env.ret(arr.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "value"}, name="startsWith")
//    public static void __startsWith__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//
//        if (!env.checkParamType("value", CLString.class)) {
//            env.ret();
//        }
//
//        CLString p1 = (CLString) env.getOwnVariable("value").getReferee();
//        CLBoolean ret = env.getBool(str.startsWith(p1.getValue()));
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "value"}, name="endsWith")
//    public static void __endsWith__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//
//        if (!env.checkParamType("value", CLString.class)) {
//            env.ret();
//        }
//
//        CLString p1 = (CLString) env.getOwnVariable("value").getReferee();
//        CLBoolean ret = env.getBool(str.endsWith(p1.getValue()));
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "times"}, name="repeat")
//    public static void __repeat__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//
//        if (!env.checkParamType("times", CLNumber.class)) {
//            env.ret();
//        }
//
//        CLNumber times = (CLNumber) env.getOwnVariable("times").getReferee();
//        CLString ret = env.newString(str.repeat(times.getIntegralValue()));
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "str"}, name="cat")
//    public static void __cat__(CLRtEnvironment env) {
//        CLString self = (CLString) env.getVariable("self").getReferee();
//        String str = self.getValue();
//
//        if (!env.checkParamType("str", CLString.class)) {
//            // todo
//            env.ret();
//        }
//
//        CLString p1 = (CLString) env.getVariable("str").getReferee();
//        CLString ret = env.newString(str + p1.getValue());
//        env.ret(ret.wrap());
//    }
}
