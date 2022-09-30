//package cloudladder.std.encode.url;
//
//import cloudladder.core.runtime.data.CLBuiltinFunction;
//import cloudladder.core.runtime.data.CLObject;
//import cloudladder.core.runtime.data.CLString;
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import cloudladder.core.runtime.env.CLRtScope;
//import cloudladder.std.CLBuiltinFuncAnnotation;
//import cloudladder.std.encode.JSON.CLStdJSON;
//import org.apache.commons.lang3.StringUtils;
//
//import java.lang.reflect.Method;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//public class CLStdUrl {
//    @CLBuiltinFuncAnnotation(value={"str"}, name="encode")
//    public static void __urlEncode__(CLRtEnvironment env) {
//        CLString str = (CLString) env.getVariable("str").getReferee();
//
//        try {
//            String encoded = URLEncoder.encode(str.getValue(), StandardCharsets.UTF_8.toString());
//            CLString ret = new CLString(encoded);
//            env.ret(ret.wrap());
//        } catch (Exception e) {
//            e.printStackTrace();
//            env.error("unsupported encoding");
//        }
//    }
//
//    @CLBuiltinFuncAnnotation(value={"str"}, name="decode")
//    public static void __urlDecode__(CLRtEnvironment env) {
//        CLString str = (CLString) env.getVariable("str").getReferee();
//
//        try {
//            String decoded = URLDecoder.decode(str.getValue(), StandardCharsets.UTF_8.toString());
//            CLString ret = new CLString(decoded);
//            env.ret(ret.wrap());
//        } catch (Exception e) {
//            e.printStackTrace();
//            env.error("unsupported encoding");
//        }
//    }
//
//    public static void run(CLRtEnvironment env) {
//        CLObject obj = env.newObject();
//        Class clazz = CLStdUrl.class;
//        CLRtScope scope = env.getCurrentScope();
//
//        for (Method method: clazz.getMethods()) {
//            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
//                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
//                String[] params = annotation.value();
//                String name = annotation.name();
//
//                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
//                obj.addStringRefer(name, func.wrap());
//            }
//        }
//
//        env.addVariable("$exports", obj.wrap());
//    }
//}
