package cloudladder.core.ir;

import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.misc.CLUtilIRList;
import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLObject;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.core.runtime.env.CLRtStack;
import cloudladder.core.runtime.env.CLRtStackFrame;
import cloudladder.utils.CLMiscUtils;
import cloudladder.utils.CLUtilFileUtils;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CLIRImport extends CLIR {
    private final String source;
    private final String as;

    private void executeBuiltinPackage(String className, CLRtEnvironment env) {
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getMethod("run", CLRtEnvironment.class);
            method.invoke(null, env);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void execute(CLRtEnvironment environment) {
        environment.fileCall(source);
        if (environment.hasExports()) {
            CLData exports = environment.getVariable("$exports").getReferee();

            String name = as;
            if (name == null || name.isEmpty()) {
                name = CLMiscUtils.getDefaultPackageName(source);
            }

            environment.addVariable(name, exports.wrap());
        }

        environment.incPC();
    }

    @Override
    public String toSelfString() {
        StringBuilder sb = new StringBuilder();
        sb.append("import      ").append(source);
        if (this.as != null) {
            sb.append("(as ").append(as).append(")");
        }
        return new String(sb);
    }
}
