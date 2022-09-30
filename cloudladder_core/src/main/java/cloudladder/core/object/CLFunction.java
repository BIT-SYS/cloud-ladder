package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@CLObjectAnnotation(typeIdentifier = "function")
public abstract class CLFunction extends CLObject {
    public ArrayList<String> paramsNames;
    public boolean catchAllParam;

    public CLFunction() {
        this.paramsNames = new ArrayList<>();
    }

    public void addParamName(String name) {
        this.paramsNames.add(name);
    }

    public abstract CLObject execute(CLRtFrame frame) throws CLRuntimeError;
}
