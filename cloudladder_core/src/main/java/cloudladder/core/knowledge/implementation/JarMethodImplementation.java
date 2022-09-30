package cloudladder.core.knowledge.implementation;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLFunction;
import cloudladder.core.object.CLJarFunction;

import java.net.URLClassLoader;

public class JarMethodImplementation extends MethodImplementation {
    // jar path
    public String className;
    public String methodName;
    public String url;

    public JarMethodImplementation(String alias, boolean catchAll, String url, String className, String methodName) {
        super(alias, catchAll);
        this.url = url;
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public CLFunction buildFunction() throws CLRuntimeError {
//        URLClassLoader classLoader = new CLass

        CLJarFunction function = new CLJarFunction(this.catchAll, this.url, this.className, this.methodName);
        return function;
    }
}
