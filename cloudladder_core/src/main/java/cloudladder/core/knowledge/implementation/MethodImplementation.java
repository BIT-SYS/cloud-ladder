package cloudladder.core.knowledge.implementation;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLFunction;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.util.ArrayList;

public abstract class MethodImplementation {
    public String id;
    public String alias;
    public ArrayList<String> paramNames;
    public boolean catchAll;
//    public String outputType;

    public MethodImplementation(String alias, boolean catchAll) {
        this.alias = alias;
        this.id = NanoIdUtils.randomNanoId();
        this.paramNames = new ArrayList<>();
        this.catchAll = catchAll;
    }

    public void addInput(String arg) {
        this.paramNames.add(arg);
    }

    public abstract CLFunction buildFunction() throws CLRuntimeError;
}
