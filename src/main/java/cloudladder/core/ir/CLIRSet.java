package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLNumber;
import cloudladder.core.runtime.data.CLString;
import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CLIRSet extends CLIR {
    private final String object;
    private final String key;
    private final String target;

    @Override
    public void execute(CLRtEnvironment environment) {
        CLReference obj = environment.getVariable(object);
        CLData index = environment.getVariable(key).getReferee();

        CLReference field = null;
        if (index instanceof CLString) {
            field = obj.getReferee().getReferee(((CLString) index).getValue());
        } else if (index instanceof CLNumber) {
            field = obj.getReferee().getReferee(((CLNumber) index).getIntegralValue());
        } else {
            // todo;
        }

        CLReference t = environment.getVariable(target);

        field.setReferee(t.getReferee());
        environment.incPC();
    }

    @Override
    public String toSelfString() {
        return "set         " + object + ", " + key + ", " + target;
    }
}
