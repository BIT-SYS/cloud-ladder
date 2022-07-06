package cloudladder.core.vm;

import cloudladder.core.runtime.env.CLRtEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.nio.file.Path;

public class CLVM {
    private CLRtEnvironment env;

    private void runInitScript(String name) {
        String packageName = "cloudladder.std.global.CLStd" + StringUtils.capitalize(name);

        try {
            Class clazz = Class.forName(packageName);
            Method method = clazz.getMethod("run", CLRtEnvironment.class);
            method.invoke(null, this.env);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CLVM(Path wd) {
        this.env = new CLRtEnvironment(wd);

        this.runInitScript("array");
        this.runInitScript("globalFunction");
        this.runInitScript("image");
        this.runInitScript("audio");
        this.runInitScript("number");
        this.runInitScript("object");
        this.runInitScript("string");
        this.runInitScript("discreteProbability");
    }

    public void execute(String filename) {
        this.env.fileCall(filename);
    }
}
