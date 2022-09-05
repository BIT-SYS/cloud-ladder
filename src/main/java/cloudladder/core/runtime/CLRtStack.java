package cloudladder.core.runtime;

import cloudladder.core.object.CLObject;

import java.util.ArrayList;

public class CLRtStack {
    private ArrayList<CLObject> stack;

    public CLRtStack() {
        this.stack = new ArrayList<>();
    }

    public CLObject pop() {
        int size = this.stack.size();
        if (size == 0) {
            // todo error
            return null;
        }
        CLObject obj = this.stack.get(size - 1);
        this.stack.remove(size - 1);
        return obj;
    }

    public void push(CLObject object) {
        this.stack.add(object);
    }

    public int size() {
        return this.stack.size();
    }
}
