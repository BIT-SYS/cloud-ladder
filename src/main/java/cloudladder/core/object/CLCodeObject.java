package cloudladder.core.object;

import cloudladder.core.ir.CLIR;

import java.util.ArrayList;
import java.util.HashSet;

public class CLCodeObject extends CLObject {
    public  ArrayList<CLIR> instructions;
    public ArrayList<String> names;
    public HashSet<String> localNames;
    public HashSet<String> nonLocalNames;
    public ArrayList<CLObject> constants;

    public CLIR getInstruction(int index) {
        return this.instructions.get(index);
    }

    public String getName(int index) {
        return this.names.get(index);
    }

    public CLObject getConstant(int index) {
        return this.constants.get(index);
    }
}
