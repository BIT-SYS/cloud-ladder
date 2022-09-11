package cloudladder.std.global;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLDict;
import cloudladder.core.runtime.CLRtScope;
import cloudladder.std.CLStdLibAnnotation;
import cloudladder.std.Loader;

public class GlobalInjector {
    public static void inject(CLRtScope scope) throws CLRuntimeError {
        // inject global function
        CLStdGlobalFunction.injectScope(scope);

        // inject global objects
        Loader loader = Loader.getLoader("cloudladder.std.global");
        for (Class cl : loader.classes) {
            CLStdLibAnnotation libAnnotation = (CLStdLibAnnotation) cl.getAnnotation(CLStdLibAnnotation.class);
            String globalName = libAnnotation.name();

            CLDict item = Loader.loadClass(cl);
            scope.addVariable(globalName, item);
        }
    }
}
