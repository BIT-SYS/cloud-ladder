package cloudladder.core.compiler;

import cloudladder.core.ir.CLIR;
import cloudladder.core.object.CLCodeObject;
import cloudladder.core.object.CLNumber;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CLCompileContext {
    private final ArrayList<CLIR> ir;
    private final HashMap<String, Integer> stringLiterals;
    private final HashMap<Integer, Integer> intLiteral;
    private final HashMap<Double, Integer> doubleLiteral;
    private final ArrayList<CLObject> constants;
    private final HashMap<String, Integer> namesMap;
    private final ArrayList<String> names;
    private final HashSet<String> localNames;

    public CLCompileContext() {
        this.ir = new ArrayList<>();
        this.stringLiterals = new HashMap<>();
        this.intLiteral = new HashMap<>();
        this.doubleLiteral = new HashMap<>();
        this.constants = new ArrayList<>();
        this.namesMap = new HashMap<>();
        this.names = new ArrayList<>();
        this.localNames = new HashSet<>();
    }

    public void addIr(CLIR ir) {
        this.ir.add(ir);
    }

    public void addLocalName(String name) {
        this.localNames.add(name);
    }

    public int addName(String name) {
        if (this.namesMap.containsKey(name)) {
            return this.namesMap.get(name);
        } else {
            this.names.add(name);
            int index = this.names.size() - 1;
            this.namesMap.put(name, index);
            return index;
        }
    }

    public int addStringLiteral(String literal) {
        if (this.stringLiterals.containsKey(literal)) {
            return this.stringLiterals.get(literal);
        } else {
            CLString object = new CLString(literal);
            this.constants.add(object);
            int index = this.constants.size() - 1;
            this.stringLiterals.put(literal, index);
            return index;
        }
    }

    public int addIntegerLiteral(int value) {
        if (this.intLiteral.containsKey(value)) {
            return this.intLiteral.get(value);
        } else {
            CLNumber object = new CLNumber((double) value);
            this.constants.add(object);
            int index = this.constants.size() - 1;
            this.intLiteral.put(value, index);
            return index;
        }
    }

    public int addDoubleLiteral(double value) {
        if (this.doubleLiteral.containsKey(value)) {
            return this.doubleLiteral.get(value);
        } else {
            CLNumber object = new CLNumber(value);
            this.constants.add(object);
            int index = this.constants.size() - 1;
            this.doubleLiteral.put(value, index);
            return index;
        }
    }

    public CLCodeObject getCodeObject() {
        CLCodeObject code = new CLCodeObject();

        code.instructions = this.ir;
        code.constants = this.constants;
        code.localNames = this.localNames;
        code.names = this.names;

        HashSet<String> nonLocalNames = new HashSet<>();
        for (String name : this.names) {
            if (!this.localNames.contains(name)) {
                nonLocalNames.add(name);
            }
        }

        code.nonLocalNames = nonLocalNames;

        return code;
    }
}
