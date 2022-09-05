//package cloudladder.std.global;
//
//import cloudladder.core.runtime.data.*;
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import cloudladder.core.runtime.env.CLRtScope;
//import cloudladder.std.CLBuiltinFuncAnnotation;
//import cloudladder.utils.CLDataUtils;
//
//import java.lang.reflect.Method;
//
//public class CLStdObject {
//    @CLBuiltinFuncAnnotation(value={"key", "value"}, name="set")
//    public static void __set__(CLRtEnvironment env) {
//        CLData self = env.getVariable("this").getReferee();
//        CLData key = env.getVariable("key").getReferee();
//        CLData value = env.getVariable("value").getReferee();
//
//        if (key instanceof CLString) {
//            self.addStringRefer(((CLString) key).getValue(), value.wrap());
//        } else if (key instanceof CLNumber) {
//            int index = ((CLNumber) key).getIntegralValue();
//            self.set(index, value.wrap());
//        }
//
//        env.ret();
//    }
//
//    @CLBuiltinFuncAnnotation(name="ownKeys")
//    public static void __ownKeys__(CLRtEnvironment env) {
//        CLData self = env.getVariable("this").getReferee();
//        CLArray ret = env.newArray();
//
//        for (String key : self.getStringRefers().keySet()) {
//            CLString str = new CLString(key);
//            ret.addNumberRefer(str.wrap());
//        }
//
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(name="json")
//    public static void __json__(CLRtEnvironment env) {
//        CLData self = env.getVariable("this").getReferee();
//
//        if (!(self instanceof CLObject)) {
//            // todo
//            return;
//        }
//
//        String json = CLDataUtils.toJson(self);
//        CLString ret = env.newString(json);
//
//        env.ret(ret.wrap());
//    }
//
//    public static void run(CLRtEnvironment env) throws Exception {
//        Class clazz = CLStdObject.class;
//        CLObject obj = new CLObject();
//        CLRtScope scope = env.getCurrentScope();
//
//        for (Method method : clazz.getMethods()) {
//            if (method.getName().startsWith("__")) {
//                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
//                String[] params = annotation.value();
//                String name = annotation.name();
//
//                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
//                obj.addStringRefer(name, func.wrap());
//            }
//        }
//
//        env.addVariable("Object", obj.wrap());
//    }
//}
