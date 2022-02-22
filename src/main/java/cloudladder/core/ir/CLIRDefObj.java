package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLObject;
import cloudladder.core.runtime.data.CLString;
import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CLIRDefObj extends CLIR {
    private final String name;
    private final ArrayList<String> keys;
    private final ArrayList<String> values;

    public CLIRDefObj(String name) {
        this.name = name;
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    @Override
    public void execute(CLRtEnvironment environment) {
        CLObject obj = new CLObject();
        for (int i = 0; i < keys.size(); i++) {
            CLReference value = environment.getVariable(values.get(i));
            CLReference key = environment.getVariable(keys.get(i));

            if (key.isNull()) {
                // todo
            }

            CLData keyData = key.getReferee();
            if (!(keyData instanceof CLString)) {
                // todo
            }

            String str = ((CLString) keyData).getValue();
            obj.addStringRefer(str, value.shallowClone());
        }

        obj.setExts(environment.getVariable("Object"));
        environment.addVariable(name, obj.wrap());
        environment.incPC();
    }

    public void addItem(String key, String value) {
        this.keys.add(key);
        this.values.add(value);
    }

    @Override
    public String toSelfString() {
        StringBuilder sb = new StringBuilder();
        sb.append("defobj      ").append(name).append(" [");
        for (int i = 0; i < keys.size(); i++) {
            sb.append("(").append(keys.get(i)).append(",").append(values.get(i)).append("), ");
        }
        sb.append("]");

        return new String(sb);
    }
}
