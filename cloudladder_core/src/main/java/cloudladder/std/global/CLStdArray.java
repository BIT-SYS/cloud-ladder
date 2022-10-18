package cloudladder.std.global;
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

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.ir.CLIR;
import cloudladder.core.ir.CLIRCall;
import cloudladder.core.object.*;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.core.runtime.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

@CLStdLibAnnotation(name = "Array")
public class CLStdArray {
    @CLBuiltinFuncAnnotation(params = {"a1", "a2"}, name = "interleave")
    public static CLArray __interleave__(CLRtFrame frame) throws CLRuntimeError {
        CLObject a1 = frame.scope.getOwnVariable("a1");
        CLObject a2 = frame.scope.getOwnVariable("a2");
        if (!(a1 instanceof CLArray arr1) || !(a2 instanceof CLArray arr2)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array");
        }

        CLArray ret = new CLArray();
        if (arr1.size() > arr2.size()) {
            CLArray temp = arr1;
            arr1 = arr2;
            arr2 = temp;
        }

        for (int i = 0; i < arr1.size(); i++) {
            CLObject item1 = arr1.items.get(i);
            CLObject item2 = arr2.items.get(i);
            ret.addItem(item1);
            ret.addItem(item2);
        }
        for (int i = arr1.size(); i < arr2.size(); i++) {
            CLObject item = arr2.items.get(i);
            ret.addItem(item);
        }

        return ret;
    }

    @CLBuiltinFuncAnnotation(params = {"a1", "a2"}, name = "append")
    public static CLArray __append__(CLRtFrame frame) throws CLRuntimeError {
        CLObject a1 = frame.scope.getOwnVariable("a1");
        CLObject a2 = frame.scope.getOwnVariable("a2");
        if (!(a1 instanceof CLArray arr1) || !(a2 instanceof CLArray arr2)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array");
        }

        CLArray ret = new CLArray();
        for (int i = 0; i < arr1.size(); i++) {
            ret.addItem(arr1.items.get(i));
        }
        for (int i = 0; i < arr2.size(); i++) {
            ret.addItem(arr2.items.get(i));
        }

        return ret;
    }

    @CLBuiltinFuncAnnotation(params = {"array"}, name = "length")
    public static CLObject __length__(CLRtFrame frame) throws CLRuntimeError {
        CLObject array = frame.scope.getOwnVariable("array");
        if (!(array instanceof CLArray arr)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array");
        }
        return CLNumber.getNumberObject(arr.items.size());
    }

    @CLBuiltinFuncAnnotation(params = {"array", "function"}, name = "each")
    public static CLObject __forEach__(CLRtFrame frame) throws CLRuntimeError {
        CLIR call = new CLIRCall(2);

        CLObject array = frame.scope.getOwnVariable("array");
        CLObject function = frame.scope.getOwnVariable("function");

        if (!(array instanceof CLArray arr)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array");
        }
        if (!(function instanceof CLFunction)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting function");
        }

        for (int i = 0; i < arr.items.size(); i++) {
            CLObject item = arr.items.get(i);
            CLNumber index = CLNumber.getNumberObject(i);

            frame.vm.stack.push(index);
            frame.vm.stack.push(item);
            frame.vm.stack.push(function);

            call.execute(frame);

            frame.vm.stack.pop();
        }

        return frame.vm.unitObject;
    }

    @CLBuiltinFuncAnnotation(params = {"array", "filter"}, name = "filter")
    public static CLObject __filter__(CLRtFrame frame) throws CLRuntimeError {
        CLIR call = new CLIRCall(2);

        CLObject filter = frame.scope.getOwnVariable("filter");
        CLObject array = frame.scope.getOwnVariable("array");

        if (!(filter instanceof CLFunction)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting function");
        }
        if (!(array instanceof CLArray arr)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array");
        }

        CLArray result = new CLArray();

        for (int i = 0; i < arr.items.size(); i++) {
            CLObject item = arr.items.get(i);
            CLNumber index = CLNumber.getNumberObject(i);

            frame.vm.stack.push(index);
            frame.vm.stack.push(item);
            frame.vm.stack.push(filter);

            call.execute(frame);

            CLObject value = frame.vm.stack.pop();
            if (!(value instanceof CLBoolean)) {
                throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "return value of predicate must be a boolean");
            }
            if (((CLBoolean) value).value) {
                result.addItem(item);
            }
        }

        return result;
    }

    @CLBuiltinFuncAnnotation(params = {"array", "function"}, name = "map")
    public static CLObject __map__(CLRtFrame frame) throws CLRuntimeError {
        CLIR call = new CLIRCall(2);

        CLObject function = frame.scope.getOwnVariable("function");
        CLObject array = frame.scope.getOwnVariable("array");

        if (!(function instanceof CLFunction f)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting function");
        }
        if (!(array instanceof CLArray arr)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting array");
        }

        CLArray result = new CLArray();

        for (int i = 0; i < arr.items.size(); i++) {
            CLObject item = arr.items.get(i);
            CLNumber index = CLNumber.getNumberObject(i);

            frame.vm.stack.push(index);
            frame.vm.stack.push(item);
            frame.vm.stack.push(function);

            call.execute(frame);
            result.addItem(frame.vm.stack.pop());
        }

        return result;
    }

    @CLBuiltinFuncAnnotation(params={"builder", "count"}, catchAll = false, name="build")
    public static CLObject __build__(CLRtFrame frame) throws CLRuntimeError {
        CLIR call = new CLIRCall(1);

        CLObject function = frame.scope.getOwnVariable("builder");
        CLObject count = frame.scope.getOwnVariable("count");

        if (!(function instanceof CLFunction f)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting function");
        }

        if (!(count instanceof CLNumber n)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
        }
        if (!n.isInteger()) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting integer, got float");
        }

        CLArray returnValue = new CLArray();

        for (int i = 0; i < (int) n.value; i++) {
            frame.vm.stack.push(CLNumber.getNumberObject(i));
            frame.vm.stack.push(f);
            try {
                call.execute(frame);
            } catch (cloudladder.core.error.CLRuntimeError clRuntimeError) {
                clRuntimeError.printStackTrace();
            }
            returnValue.addItem(frame.vm.stack.pop());
        }

        return returnValue;
    }

//    public static CLDict getArrayDict() {
//        CLDict dict = new CLDict();
//
//        for (Method method : CLStdArray.class.getMethods()) {
//            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
//                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
//
//                ArrayList<String> params = new ArrayList<>();
//                Collections.addAll(params, annotation.params());
//                boolean catchAll = annotation.catchAll();
//                String name = annotation.name();
//
//                CLBuiltinFunction function = new CLBuiltinFunction(method, params, catchAll);
//                dict.contents.put(name, function);
//            }
//        }
//
//        return dict;
//    }

//    public static void injectScope(CLRtScope scope) {
//        CLDict arr = CLStdArray.getArrayDict();
//        scope.addVariable("Array", arr);
//    }
}
