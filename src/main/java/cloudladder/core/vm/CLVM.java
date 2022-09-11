package cloudladder.core.vm;

import cloudladder.core.compiler.CLCompileContext;
import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.ir.CLIRCallFile;
import cloudladder.core.object.CLCodeObject;
import cloudladder.core.object.CLDict;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLUnit;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.core.runtime.CLRtGlobalScope;
import cloudladder.core.runtime.CLRtScope;
import cloudladder.core.runtime.CLRtStack;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CLVM {
    public final CLRtGlobalScope globalScope;
    public final CLRtStack stack;
    public final CLUnit unitObject;
    public final HashMap<String, CLDict> moduleCache;
    public final Path cwd;

    public CLVM(Path cwd) throws CLRuntimeError {
        this.stack = new CLRtStack();
        this.globalScope = new CLRtGlobalScope();
        this.unitObject = new CLUnit();
        this.moduleCache = new HashMap<>();

        this.cwd = cwd;
    }

    public CLRtFrame runFile(String filename) throws CLRuntimeError {
        CLCompileContext context = new CLCompileContext();
        CLCompiler compiler = new CLCompiler();

        try {
            FileInputStream fileInputStream = new FileInputStream(this.cwd.resolve(filename).toFile());
            String content = new String(fileInputStream.readAllBytes());
            compiler.compileFile(content, context);
            CLCodeObject codeObject = context.getCodeObject();
            System.out.println(codeObject.beautify());
            CLRtFrame frame = new CLRtFrame(this, null, codeObject, "starter file");
            frame.execute();
            return frame;
        } catch (IOException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.FileNotFound, "file not found");
        }
    }

    public CLRtFrame runCodeObject(CLCodeObject codeObject) throws CLRuntimeError {
        CLRtFrame frame = new CLRtFrame(this, null, codeObject, "Run code object");
        frame.execute();
        return frame;
    }
//    private CLRtEnvironment env;
//
//    private void runInitScript(String name) {
//        String packageName = "cloudladder.std.global.CLStd" + StringUtils.capitalize(name);
//
//        try {
//            Class clazz = Class.forName(packageName);
//            Method method = clazz.getMethod("run", CLRtEnvironment.class);
//            method.invoke(null, this.env);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public CLVM(Path wd) {
//        this.env = new CLRtEnvironment(wd);
//
//        this.runInitScript("array");
//        this.runInitScript("globalFunction");
//        this.runInitScript("image");
//        this.runInitScript("audio");
//        this.runInitScript("number");
//        this.runInitScript("object");
//        this.runInitScript("string");
//        this.runInitScript("discreteProbability");
//    }
//
//    public void execute(String filename) {
//        this.env.fileCall(filename);
//    }
}
