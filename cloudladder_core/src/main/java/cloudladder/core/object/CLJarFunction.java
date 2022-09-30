package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class CLJarFunction extends CLFunction {
    public String url;
    public String className;
    public String methodName;

    private Method method;
//    private URLClassLoader classLoader;

    public CLJarFunction(boolean catchAll, String url, String className, String methodName) throws CLRuntimeError {
        super(new ArrayList<>(), catchAll);

        this.url = url;
        this.className = className;
        this.methodName = methodName;

        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.MalformedURL, "string `" + this.url + "` is not a valid url");
        }
        URLClassLoader classLoader = new URLClassLoader(
                new URL[] { u },
                this.getClass().getClassLoader()
        );

        try {
            Class c = classLoader.loadClass(this.className);
//            System.out.println(c.getMethods()[0].getName());
            this.method = c.getMethod(this.methodName, CLRtFrame.class);

            if (this.method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
                CLBuiltinFuncAnnotation annotation = this.method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String[] params = annotation.params();
                for (String p: params) {
                    this.addParamName(p);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.ClassNotFound, "class `" + this.className + "` not found");
        } catch (NoSuchMethodException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.MemberNotFound, "method `" + this.methodName + "` of class '" + this.className + "` not found");
        } catch (Exception e) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "illegal access");
        }
    }

    @Override
    public CLObject execute(CLRtFrame frame) throws CLRuntimeError {
        try {
            Object retValue = this.method.invoke(null, frame);
            if (!(retValue instanceof CLObject o)) {
                throw new CLRuntimeError(CLRuntimeErrorType.WrongReturnType, "return value type of jar function have to be CLObject, got `" + retValue.getClass().getTypeName() + "`");
            }
            return o;
        } catch (Exception e) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "illegal access");
        }
    }
}
