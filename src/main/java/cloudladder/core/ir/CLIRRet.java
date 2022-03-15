package cloudladder.core.ir;

import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CLIRRet extends CLIR {
    private final String value;

    @Override
    public void execute(CLRtEnvironment environment) {
        if (value == null || value.isEmpty()) {
            environment.ret();
        } else {
            environment.ret(value);
        }
    }

    @Override
    public String toSelfString() {
        return "ret         " + value;
    }
}
