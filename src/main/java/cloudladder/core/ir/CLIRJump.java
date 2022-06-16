package cloudladder.core.ir;

import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class CLIRJump extends CLIR {
    @Setter
    private int offset;
    private final String label;

    @Override
    public void execute(CLRtEnvironment environment) {
        environment.setPCBy(offset);
    }

    @Override
    public String toSelfString() {
        return "jump        " + label + "(" + offset + ")";
    }
}
