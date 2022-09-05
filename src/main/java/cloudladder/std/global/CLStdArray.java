//package cloudladder.std.global;
//
//import cloudladder.core.runtime.data.*;
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import cloudladder.core.runtime.env.CLRtScope;
//import cloudladder.std.CLBuiltinFuncAnnotation;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//
//public class CLStdArray {
//    @CLBuiltinFuncAnnotation(value={"self"}, name="empty")
//    public static void __empty__(CLRtEnvironment env) {
//        CLArray self = (CLArray) env.getVariable("self").getReferee();
//
//        env.ret(self.getNumberRefers().size() == 0);
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self"}, name="length")
//    public static void __length__(CLRtEnvironment env) {
//        CLArray self = (CLArray) env.getVariable("self").getReferee();
//        int length = self.getNumberRefers().size();
//
//        CLNumber ret = env.newNumber(length);
//        env.ret(ret.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "item"}, name="push")
//    public static void __push__(CLRtEnvironment env) {
//        CLArray self = (CLArray) env.getVariable("self").getReferee();
//        CLData data = env.getVariable("item").getReferee();
//
//        self.addNumberRefer(data.wrap());
//        env.ret(self.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self"}, name="pop")
//    public static void __pop__(CLRtEnvironment env) {
//        CLArray self = (CLArray) env.getVariable("self").getReferee();
//
//        if (self.getNumberRefers().size() == 0) {
//            env.error("array size = 0");
//            return;
//        }
//
//        CLReference last = self.pop();
//
//        env.ret(last);
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "do"}, name="forEach")
//    public static void __forEach__(CLRtEnvironment env) {
//        CLArray self = (CLArray) env.getVariable("self").getReferee();
//        CLData func = env.getVariable("do").getReferee();
//
//        if (!(func instanceof CLFunction)) {
//            // todo
//            return;
//        }
//
//        CLFunction f = (CLFunction) func;
//
//        ArrayList<CLReference> items = self.getNumberRefers();
//        for (int i = 0; i < items.size(); i++) {
//            CLReference item = items.get(i);
//            CLNumber index = new CLNumber(i);
//            index.setExts(env.getVariable("Number"));
//
//            ArrayList<CLReference> params = new ArrayList<>();
////            params.add(null); // add "this"
//
//            params.add(item);
//            params.add(index.wrap());
//
//            env.call(f, params);
//        }
//
//        env.ret(self.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "value"}, name="fill")
//    public static void __fill__(CLRtEnvironment env) {
//        CLReference selfRef = env.getOwnVariable("self");
//        if (selfRef == null) {
//            env.error("self is none");
//            return;
//        }
//
//        CLReference value = env.getOwnVariable("value");
//        if (value == null) {
//            env.error("value is none");
//            return;
//        }
//
//        CLArray self = (CLArray) selfRef.getReferee();
//        CLData v = value.getReferee();
//
//        for (int i = 0; i < self.getNumberRefers().size(); i++) {
//            self.getNumberRefers().set(i, v.wrap());
//        }
//
//        env.ret(self.wrap());
//    }
//
//    @CLBuiltinFuncAnnotation(value={"self", "do"}, name="map")
//    public static void __map__(CLRtEnvironment env) {
//        CLData self = env.getVariable("self").getReferee();
//        CLData func = env.getVariable("do").getReferee();
//
//        if (!(self instanceof CLArray) || !(func instanceof CLFunction)) {
//            // todo
//            return;
//        }
//
//        ArrayList<CLReference> items = self.getNumberRefers();
//        CLArray ret = env.newArray();
//        for (int i = 0; i < items.size(); i++) {
//            CLReference item = items.get(i);
//            CLNumber index = env.newNumber(i);
//
//            ArrayList<CLReference> params = new ArrayList<>();
////            params.add(null);
//
//            params.add(item);
//            params.add(index.wrap());
//
//            env.call((CLFunction) func, params);
//            CLData data = env.getVariable("$r0").getReferee();
//            ret.addNumberRefer(data.wrap());
//        }
//
//        env.ret(ret.wrap());
//    }
//
//    public static void run(CLRtEnvironment env) throws Exception {
//        Class clazz = CLStdArray.class;
//        CLRtScope scope = env.getCurrentScope();
//        CLObject obj = new CLObject();
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
//        env.addVariable("Array", obj.wrap());
//    }
//}
