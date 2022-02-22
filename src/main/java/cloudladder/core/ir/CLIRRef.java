package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CLIRRef extends CLIR {
    private final String newRef;
    private final String oldRef;

    @Override
    public void execute(CLRtEnvironment environment) {
        CLReference old = environment.getVariable(oldRef);
        if (old == null) {
            // todo
            return;
        }

        if (environment.hasVariable(newRef)) {
            CLReference r = environment.getVariable(newRef);
            r.setReferee(old.getReferee());
            environment.incPC();
        } else {
            CLReference ref = new CLReference(old.getReferee());
            environment.addVariable(newRef, ref);
            environment.incPC();
        }
    }

    @Override
    public String toSelfString() {
        return "ref         " + newRef + ", " + oldRef;
    }
}
