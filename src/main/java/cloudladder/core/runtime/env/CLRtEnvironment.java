package cloudladder.core.runtime.env;

import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.ir.CLIR;
import cloudladder.core.misc.CLUtilIRList;
import cloudladder.core.runtime.data.*;
import cloudladder.utils.CLMiscUtils;
import cloudladder.utils.CLUtilFileUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class CLRtEnvironment {
    @Setter
    private CLRtScope currentScope;
    @Getter
    @Setter
    private CLRtScope globalScope;
    private CLRtStack stack;

    @Setter
    private int pc;

    private boolean error = false;

    private final HashMap<String, CLReference> specialVariables;

    // working directory
    private Path wd;

    public CLRtEnvironment(Path wd) {
        this.globalScope = new CLRtScope(null);
        this.currentScope = globalScope;
        this.stack = new CLRtStack();
        this.stack.push(new CLRtBottomStackFrame());
        this.pc = 0;
        this.specialVariables = new HashMap<>();

        this.wd = wd;
    }

    public boolean hasExports() {
        return this.specialVariables.containsKey("$exports");
    }

    public boolean checkParams(String ...params) {
        for (String s : params) {
            if (!hasOwnVariable(s)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkParamType(String param, Class type) {
        if (!checkParams(param)) {
            return false;
        }
        CLData data = getOwnVariable(param).getReferee();
        return type.isInstance(data);
    }

    public CLObject newObject() {
        CLObject ret = new CLObject();
        ret.setExts(getVariable("Object"));
        return ret;
    }

    public CLNumber newNumber(double value) {
        CLNumber number = new CLNumber(value);
        number.setExts(getVariable("Number"));

        return number;
    }

    public CLArray newArray() {
        CLArray ret = new CLArray();
        ret.setExts(getVariable("Array"));

        return ret;
    }

    public CLString newString(String value) {
        CLString ret = new CLString(value);
        ret.setExts(getVariable("String"));

        return ret;
    }

    public CLBoolean getBool(boolean value) {
        if (value) {
            return (CLBoolean) globalScope.getVariable("$true").getReferee();
        } else {
            return (CLBoolean) globalScope.getVariable("$false").getReferee();
        }
    }

    public void setPCBy(int offset) {
        this.pc += offset;
    }

    public void incPC() {
        this.pc++;
    }

    public CLReference getVariable(String key) {
        if (specialVariables.containsKey(key)) {
            return specialVariables.get(key);
        }
        CLReference ref = currentScope.getVariable(key);
        return currentScope.getVariable(key);
    }

    public CLReference getOwnVariable(String key) {
        return currentScope.getOwnVariable(key);
    }

    public boolean hasOwnVariable(String key) {
        return currentScope.hasOwnVariable(key);
    }

    public boolean hasVariable(String key) {
        return currentScope.hasVariable(key);
    }

    public void addVariable(String key, CLReference data) {
        currentScope.addVariable(key, data);
    }

    public void pushStack(CLRtStackFrame frame) {
        stack.push(frame);
    }

    public CLRtStackFrame pop() {
        return stack.pop();
    }

    public void ret(String value) {
        if (value == null || value.isEmpty()) {
            ret(CLReference.newNull());
        } else {
            ret(getVariable(value));
        }
    }

    public void ret(CLReference value) {
        stack.pop();
        CLRtScope scope = stack.top().getScope();
//        scope.addVariable("$r0", value);
        this.currentScope = scope;
        this.pc = stack.top().getPc();
        this.specialVariables.put("$r0", value);
    }

    public void ret() {
        ret(CLReference.newNull());
    }

    public void ret(boolean value) {
        ret(getBool(value).wrap());
    }

    public void call(String func, ArrayList<String> params) {
        CLReference obj = this.getVariable(func);
        if (obj.isNull()) {
            // todo
        }

        if (!(obj.getReferee() instanceof CLFunction)) {
            this.error(func + " is not a function");
            return;
        }

        CLFunction f = (CLFunction) obj.getReferee();

        // set parent scope
        CLRtScope newScope = new CLRtScope(f.getParentScope());

        // set this
//        String self = params.get(0);
//        if (!self.equals("null")) {
//            newScope.addVariable("this", this.getVariable(self).shallowClone());
//        }

        // set params
        int ps = Math.min(params.size(), f.getParams().size());
        for (int i = 0; i < ps; i++) {
            CLReference ref = this.getVariable(params.get(i));
            String name = f.getParams().get(i);
            newScope.addVariable(name, ref.shallowClone());
        }

        CLRtFunctionStackFrame frame = new CLRtFunctionStackFrame(newScope, func);

//        this.incPC();
        this.saveCurrentStackFrame();
        this.pushStack(frame);
        this.setCurrentScope(newScope);

        f.execute(this);
    }

    public void call(CLFunction func, ArrayList<CLReference> params) {
        // set parent scope
        CLRtScope newScope = new CLRtScope(func.getParentScope());

        // set this
//        CLReference self = params.get(0);
//        if (self != null) {
//            newScope.addVariable("this", self.shallowClone());
//        }

        // set params
        int ps = Math.min(params.size(), func.getParams().size());
        for (int i = 0; i < ps; i++) {
            String name = func.getParams().get(i);
            newScope.addVariable(name, params.get(i).shallowClone());
        }

        CLRtFunctionStackFrame frame = new CLRtFunctionStackFrame(newScope, func.getName());

        this.incPC();
        this.saveCurrentStackFrame();
        this.pushStack(frame);
        this.setCurrentScope(newScope);

        func.execute(this);
    }

    public void fileRet() {
        if (this.hasOwnVariable("$exports")) {
            CLReference value = this.currentScope.getOwnVariable("$exports");
            this.specialVariables.put("$exports", value);
        } else {
            this.specialVariables.remove("$exports");
        }

        // remove file stack frame
        stack.pop();
        // restore last state
        this.currentScope = stack.top().getScope();
        this.pc = stack.top().getPc();
    }

    public void fileCall(String source) {
        this.saveCurrentStackFrame();

        CLRtScope newFileScope = new CLRtScope(this.globalScope);

        CLRtFileStackFrame frame = new CLRtFileStackFrame(newFileScope, source);

        this.pushStack(frame);
        this.setCurrentScope(newFileScope);
        this.setPc(0);

        if (this.isFileExists(source)) {
            Path filePath = this.wd.resolve(source);
            String fileContent = CLUtilFileUtils.loadFile(filePath);
            CLUtilIRList ir = CLCompiler.compileFile(fileContent);
            System.out.println(ir);
            ir.execute(this);
        } else {
            String importName = CLMiscUtils.getDefaultPackageName(source);
            String pName = source.replaceAll("/", ".");
            String className = "cloudladder.std." + pName + ".CLStd" + StringUtils.capitalize(importName);
            CLMiscUtils.executeStdFile(className, "run", this);
        }

        this.fileRet();
    }

    private boolean isFileExists(String name) {
        Path path = this.wd.resolve(name).normalize();
        File file = path.toFile();

        return file.exists();
    }

    private boolean isFileExists(Path path) {
        Path p = this.wd.resolve(path).normalize();
        File file = path.toFile();

        return file.exists();
    }

    public void saveCurrentStackFrame() {
        CLRtStackFrame s = this.stack.top();
        s.setPc(pc);
        s.setScope(currentScope);
    }

    public void printStackTrace() {
        ArrayList<String> temp = new ArrayList<>();
        for (CLRtStackFrame s : stack.getSt()) {
            temp.add(s.getFrameDescription());
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            System.out.println("at " + temp.get(i));
        }
    }

    public void error(String msg) {
        System.out.println("CLError: " + msg);
        printStackTrace();
        this.error = true;
    }
}
