package cloudladder.std.global;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.ir.CLIR;
import cloudladder.core.object.*;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.core.runtime.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;
import ij.IJ;
import ij.ImagePlus;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class CLStdGlobalFunction {
    public static String toString(CLObject object) throws CLRuntimeError {
        if (object instanceof CLString s) {
            return s.value;
        } else if (object instanceof CLNumber n) {
            if (n.isInteger()) {
                return Integer.toString((int) n.value);
            } else {
                return Double.toString(n.value);
            }
        } else if (object instanceof CLArray arr) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (CLObject item : arr.items) {
                sb.append(CLStdGlobalFunction.toString(item)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            return new String(sb);
        } else if (object instanceof CLRef ref) {
            return "ref(" + CLStdGlobalFunction.toString(ref.value) + ")";
        } else if (object instanceof CLBoolean b) {
            return Boolean.toString(b.value);
        } else if (object instanceof CLDict dict) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (String key : dict.contents.keySet()) {
                CLObject value = dict.contents.get(key);
                sb.append(key).append(": ").append(CLStdGlobalFunction.toString(value)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
            return new String(sb);
        } else if (object instanceof CLImage) {
            return "image";
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "type not supported");
    }

    @CLBuiltinFuncAnnotation(params = {"path"}, name = "image")
    public static CLObject __image__(CLRtFrame frame) throws CLRuntimeError {
        CLObject file = frame.scope.getOwnVariable("path");
        if (!(file instanceof CLString s)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string");
        }

        Path path = frame.vm.cwd.resolve(s.value);
        ImagePlus image = IJ.openImage(path.toString());

        return new CLImage(image);
    }

    @CLBuiltinFuncAnnotation(params={"items"}, catchAll = true, name = "print")
    public static CLObject __print__(CLRtFrame frame) throws CLRuntimeError {
        CLObject items = frame.scope.getOwnVariable("items");
        if (items instanceof CLArray) {
            for (CLObject item : ((CLArray) items).items) {
//                System.out.println(CLStdGlobalFunction.toString(item));
                System.out.println(item.defaultStringify());
            }
        }

        return frame.vm.unitObject;
    }

    @CLBuiltinFuncAnnotation(params={"object"}, catchAll = false, name="type")
    public static CLObject __type__(CLRtFrame frame) throws CLRuntimeError {
        CLObject item = frame.scope.getOwnVariable("object");
        if (item instanceof CLString) {
            return new CLString("string");
        } else if (item instanceof CLNumber) {
            return new CLString("number");
        } else if (item instanceof CLFunction) {
            return new CLString("function");
        } else if (item instanceof CLDict) {
            return new CLString("dict");
        } else if (item instanceof CLImage) {
            return new CLString("image");
        }
        // todo

        return frame.vm.unitObject;
    }

    @CLBuiltinFuncAnnotation(params = {"function"}, name="dis")
    public static CLObject __dis__(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.scope.getOwnVariable("function");
//        CLArray returnValue = new CLArray();
        StringBuilder sb = new StringBuilder();

        if (object instanceof CLUserFunction f) {
            return new CLString(f.codeObject.beautify());
        }

        // todo;
        return null;
    }

    @CLBuiltinFuncAnnotation(params = {"object"}, name = "ref")
    public static CLObject __ref__(CLRtFrame frame) throws CLRuntimeError {
        CLObject object = frame.scope.getOwnVariable("object");

        return new CLRef(object);
    }

    @CLBuiltinFuncAnnotation(name = "map_array")
    public static CLObject __map_array__(CLRtFrame frame) throws CLRuntimeError {
        CLMapArray ma = new CLMapArray();
        return ma;
    }

    public static void injectScope(CLRtScope scope) {
        Class<CLStdGlobalFunction> c = CLStdGlobalFunction.class;
        for (Method method: c.getMethods()) {
            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String name = annotation.name();
                String[] params = annotation.params();
                boolean catchAll = annotation.catchAll();

                ArrayList<String> paramNames = new ArrayList<>();
                Collections.addAll(paramNames, params);

                CLBuiltinFunction function = new CLBuiltinFunction(method, paramNames, catchAll);

                scope.addVariable(name, function);
            }
        }
    }
}