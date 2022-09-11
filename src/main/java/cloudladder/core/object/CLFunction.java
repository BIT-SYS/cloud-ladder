package cloudladder.core.object;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public abstract class CLFunction extends CLObject {
    public ArrayList<String> paramsNames;
    public boolean catchAllParam;

    public void addParamName(String name) {
        this.paramsNames.add(name);
    }
}
