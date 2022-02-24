package cloudladder.core.runtime.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CLReference {
    private CLData referee;

    public boolean isNull() {
        return referee == null;
    }

    public static CLReference newNull() {
        return new CLReference(null);
    }

    public CLReference shallowClone() {
        return new CLReference(referee);
    }
}
