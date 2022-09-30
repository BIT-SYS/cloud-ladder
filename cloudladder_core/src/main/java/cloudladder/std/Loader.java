package cloudladder.std;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLBuiltinFunction;
import cloudladder.core.object.CLDict;
import cloudladder.core.object.CLObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Loader {
    public Set<Class> classes;
    public static HashMap<String, Loader> instances = new HashMap<>();

    private Loader(String pkg) {
        this.classes = this.findAllClasses(pkg);
    }

    public Set<Class> findAllClasses(String pkg) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(pkg.replaceAll("\\.", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, pkg))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Class getClass(String className, String pkg) {
        try {
            Class cl = Class.forName(pkg + "." + className.substring(0, className.lastIndexOf(".")));
            if (cl.isAnnotationPresent(CLStdLibAnnotation.class)) {
                return cl;
            } else {
                return null;
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Loader getLoader(String pkg) {
        if (Loader.instances.containsKey(pkg)) {
            return Loader.instances.get(pkg);
        } else {
            Loader loader = new Loader(pkg);
            Loader.instances.put(pkg, loader);
            return loader;
        }
    }

    public static CLDict loadClass(Class cl) throws CLRuntimeError {
        CLDict exports = new CLDict();

        for (Method method : cl.getMethods()) {
            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
                CLBuiltinFuncAnnotation funcAnnotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);

                String methodName = funcAnnotation.name();
                String[] params = funcAnnotation.params();
                boolean catchAll = funcAnnotation.catchAll();

                ArrayList<String> paramNames = new ArrayList<>();
                Collections.addAll(paramNames, params);

                CLBuiltinFunction function = new CLBuiltinFunction(method, paramNames, catchAll);

                exports.put(methodName, function);
            } else if (method.isAnnotationPresent(CLBuiltinConstantAnnotation.class)) {
                CLBuiltinConstantAnnotation constantAnnotation = method.getAnnotation(CLBuiltinConstantAnnotation.class);

                String constName = constantAnnotation.name();

                try {
                    CLObject value = (CLObject) method.invoke(null);
                    exports.put(constName, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "illegal while executing const invoke");
                }

            }
        }

        return exports;
    }

    public CLDict loadName(String name) throws CLRuntimeError {
        for (Class cl : this.classes) {
            if (cl.isAnnotationPresent(CLStdLibAnnotation.class)) {
                CLStdLibAnnotation annotation = (CLStdLibAnnotation) cl.getAnnotation(CLStdLibAnnotation.class);

                String libName = annotation.name();
                if (libName.equals(name)) {
                    return Loader.loadClass(cl);
                }
            }
        }

        throw new CLRuntimeError(CLRuntimeErrorType.LibNotFound, "builtin lib `" + name + "` not found");
    }

    public static CLDict loadLib(String name) throws CLRuntimeError {
        Loader loader = Loader.getLoader("cloudladder.std.lib");

        return loader.loadName(name);
    }
}
