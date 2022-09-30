//package cloudladder.std.global;
//
//import cloudladder.core.runtime.data.*;
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import cloudladder.core.runtime.env.CLRtScope;
//import cloudladder.std.CLBuiltinFuncAnnotation;
//
//import java.lang.reflect.Method;
//import java.nio.file.Path;
//
//public class CLStdDiscreteProbability {
//    @CLBuiltinFuncAnnotation(name="new")
//    public static void __new__(CLRtEnvironment env) {
//        CLDiscreteProbability prob = new CLDiscreteProbability();
//
//        env.ret(prob.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(name="push", value={"self", "prob", "data"})
//    public static void __push__(CLRtEnvironment env) {
//        CLReference selfRef = env.getVariable("self");
//        CLReference probRef = env.getVariable("prob");
//        CLReference dataRef = env.getVariable("data");
//        if (selfRef.isNull() || probRef.isNull() || dataRef.isNull()) {
//            // todo
//        }
//
//        CLDiscreteProbability self = (CLDiscreteProbability) selfRef.getReferee();
//        CLNumber prob = (CLNumber) probRef.getReferee();
//        CLData data = dataRef.getReferee();
//
//        self.addEntry(prob.getValue(), data);
//
//        env.ret(selfRef);
//    }
//
//    public static void run(CLRtEnvironment env) throws Exception {
//        Class clazz = CLStdDiscreteProbability.class;
//        CLObject obj = new CLObject();
//        CLRtScope scope = env.getCurrentScope();
//
//        for (Method method : clazz.getMethods()) {
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
//        env.addVariable("DiscreteProbability", obj.wrap());
//    }
//}
