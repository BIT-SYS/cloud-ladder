package cloudladder.core.object;

import java.util.ArrayList;

public class CLArray extends CLObject {
    public ArrayList<CLObject> items;

    public CLArray() {
        this.items = new ArrayList<>();
    }

    public void addItem(CLObject item) {
        this.items.add(item);
    }
}
