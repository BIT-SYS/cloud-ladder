package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLBuiltinFunction;
import cloudladder.core.runtime.data.CLFunction;
import cloudladder.core.runtime.data.CLUserFunction;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.core.runtime.env.CLRtStackFrame;

import java.util.ArrayList;

public class CLIRCall extends CLIR {
    private final String functionName;
    private final ArrayList<String> params;
    private final String destination;

    public CLIRCall(String functionName, String destination) {
        this.functionName = functionName;
        this.params = new ArrayList<>();
        this.destination = destination;
    }

    public void addParam(String p) {
        this.params.add(p);
    }

    @Override
    public void execute(CLRtEnvironment environment) {
        environment.call(functionName, params);

        CLReference ret = environment.getVariable("$r0");
        environment.addVariable(destination, ret.shallowClone());

        environment.incPC();
    }

    @Override
    public String toSelfString() {
        StringBuilder temp = new StringBuilder("call        " + this.functionName);
        for (String param : params) {
            temp.append(", ").append(param);
        }
        temp.append(" -> ").append(destination);

        return new String(temp);
    }
}
