package cloudladder.core.runtime.data;

import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.data.CLReference;

import java.util.ArrayList;

public class CLArray extends CLData {
    private int len;

    public CLArray() {
        this.len = 0;
    }

    @Override
    public String toString() {
        ArrayList<CLReference> refs = this.getNumberRefers();
        if (refs.size() == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(refs.get(0).getReferee().toString());
            for (int i = 1; i < refs.size(); i++) {
                sb.append(",").append(refs.get(i).getReferee().toString());
            }
            sb.append("]");
            return new String(sb);
        }
    }
}
