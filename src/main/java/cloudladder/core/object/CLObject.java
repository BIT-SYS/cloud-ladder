package cloudladder.core.object;

import java.util.HashMap;
import java.util.WeakHashMap;

public abstract class CLObject {
    private static int NEXT_ID = 0;

    private int id;

    public CLObject() {
        this.id = CLObject.NEXT_ID;
        CLObject.NEXT_ID += 1;
    }

    public CLObject add(CLObject other) {
        // todo
        return null;
    }


}
