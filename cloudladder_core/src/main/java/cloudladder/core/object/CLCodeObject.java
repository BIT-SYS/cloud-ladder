package cloudladder.core.object;

import cloudladder.core.ir.CLIR;

import java.util.ArrayList;
import java.util.HashSet;

@CLObjectAnnotation(typeIdentifier = "code_object")
public class CLCodeObject extends CLObject {
    public ArrayList<CLIR> instructions;
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

    public String beautify() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.instructions.size(); i++) {
            String s = this.instructions.get(i).toString();
            sb.append(i).append(": ").append(s).append("\n");
        }

        return new String(sb);
    }

    @Override
    public String getTypeIdentifier() {
        return "code_object";
    }

    @Override
    public String defaultStringify() {
        return "code_object@" + this.id;
    }

    //    public String getCodes() {
//        StringBuilder sb = new StringBuilder();
//        for (CLIR ir : this.instructions) {
//
//        }
//    }
}
