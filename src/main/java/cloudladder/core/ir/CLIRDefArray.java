package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLArray;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CLIRDefArray extends CLIR {
    private final String name;
    private final ArrayList<String> value;

    public CLIRDefArray(String name) {
        this.name = name;
        this.value = new ArrayList<>();
    }

    @Override
    public void execute(CLRtEnvironment environment) {
        CLArray arr = new CLArray();
        for (int i = 0; i < value.size(); i++) {
            CLReference data = environment.getVariable(value.get(i));
            if (data == null) {
                // todo
            }

            arr.addNumberRefer(data.shallowClone());
        }
        arr.setExts(environment.getVariable("Array"));

        CLReference ref = new CLReference(arr);
        environment.addVariable(name, ref);
        environment.incPC();
    }

    public void addValue(String name) {
        value.add(name);
    }

    @Override
    public String toSelfString() {
        StringBuilder sb = new StringBuilder();
        sb.append("defarray    ").append(this.name);

        for (String s : value) {
            sb.append(" ").append(s).append(",");
        }

        return new String(sb);
    }
}
