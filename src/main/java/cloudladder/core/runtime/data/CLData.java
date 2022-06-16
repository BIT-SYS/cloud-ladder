package cloudladder.core.runtime.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class CLData {
    public static int ID_ITER = 0;

    protected String typeName;

    protected boolean isBuildIn;

    protected int id;

    protected HashMap<String, CLReference> stringRefers;
    protected ArrayList<CLReference> numberRefers;

    protected CLReference exts = null;

    public CLData() {
        this.id = ID_ITER;
        ID_ITER++;

        this.stringRefers = new HashMap<>();
        this.numberRefers = new ArrayList<>();
    }

    public void addStringRefer(String key, CLReference ref) {
        this.stringRefers.put(key, ref);
    }

    public void addNumberRefer(CLReference ref) {
        this.numberRefers.add(ref);
    }

    public void set(int index, CLReference ref) {
        if (index >= numberRefers.size()) {
            return;
            // todo
        }

        numberRefers.set(index, ref);
    }

    public CLReference getReferee(String key) {
        if (stringRefers.containsKey(key)) {
            return stringRefers.get(key);
        } else if(exts != null) {
            return exts.getReferee().getReferee(key);
        }

        return null;
    }

    public CLReference getReferee(int key) {
        return numberRefers.get(key);
    }

    public CLReference wrap() {
        return new CLReference(this);
    }

    public CLReference pop() {
        if (numberRefers.size() == 0) {
            return null;
        }
        CLReference ret = numberRefers.get(numberRefers.size() - 1);
        numberRefers.remove(numberRefers.size() - 1);
        return ret;
    }
}
