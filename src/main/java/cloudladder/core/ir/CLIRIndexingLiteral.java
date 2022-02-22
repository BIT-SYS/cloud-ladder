package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLArray;
import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CLIRIndexingLiteral extends CLIR {
    private final String object;
    private final int index;
    private final String indexStr;
    private final String type;
    private final String destination;

    private void exeInt(CLRtEnvironment environment) {
        CLReference ref = environment.getVariable(object);
        if (ref.isNull()) {
            // todo
        }

        CLData data = ref.getReferee();
        CLReference target = data.getReferee(index);
        environment.addVariable(destination, target.shallowClone());
    }

    private void exeString(CLRtEnvironment environment) {
        CLReference ref = environment.getVariable(object);
        if (ref.isNull()) {
            // todo
        }

        CLData data = ref.getReferee();
        CLReference target = data.getReferee(indexStr);
        environment.addVariable(destination, target.shallowClone());
    }

    @Override
    public void execute(CLRtEnvironment environment) {
        if (this.type.equals("int")) {
            exeInt(environment);
        } else {
            exeString(environment);
        }

        environment.incPC();
    }

    @Override
    public String toSelfString() {
        if (type.equals("int")) {
            return "index       " + object + ", " + index + " -> " + destination;
        } else {
            return "index       " + object + ", " + indexStr + " -> " + destination;
        }
    }
}
