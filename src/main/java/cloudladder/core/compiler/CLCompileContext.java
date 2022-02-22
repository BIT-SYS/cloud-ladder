package cloudladder.core.compiler;

import cloudladder.core.ir.*;
import cloudladder.core.misc.CLUtilIRList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class CLCompileContext {
    private final CLUtilIRList ir;

    private int tempVarCount = 0;

    // label => ir index
    private final HashMap<String, Integer> labels;
    // ir index => label
    private final HashMap<Integer, String> zip;

    // iteration name stack
    private final ArrayList<String> iterName;
    private int iterCount = 0;

    // if name stack
    private final ArrayList<String> ifName;
    private int ifCount = 0;

    public String nextLabel = "";
    public boolean setNextLabel = false;

    // and/or iter
    private int andOrCount = 0;

    public int iterAndOr() {
        andOrCount++;
        return andOrCount - 1;
    }

    private void unzip(int irIndex, int offset) {
        CLIR ir = this.ir.getList().get(irIndex);

        if (ir instanceof CLIRJump) {
            ((CLIRJump) ir).setOffset(offset);
        } else if (ir instanceof CLIRBf) {
            ((CLIRBf) ir).setOffset(offset);
        } else if (ir instanceof CLIRBt) {
            ((CLIRBt) ir).setOffset(offset);
        }
    }


    public String tempString(String value) {
        String name = "$" + iterTempVar();
        CLIRDefString defString = new CLIRDefString(name, value);
        addIr(defString);
        return name;
    }

    public void endFunction() {
        if (!endsWithRet()) {
            addIr(new CLIRRet(""));
        }
    }

    public boolean endsWithRet() {
        CLIR last = ir.get(ir.size() - 1);
        return last instanceof CLIRRet;
    }

    public void addNop() {
        CLIRNop nop = new CLIRNop();
        addIr(nop);
    }

    public CLCompileContext() {
        ir = new CLUtilIRList();
        labels = new HashMap<>();
        zip = new HashMap<>();
        iterName = new ArrayList<>();
        ifName = new ArrayList<>();
    }

    public void pushIterName(String name) {
        this.iterName.add(name);
    }

    public void popIterName() {
        this.iterName.remove(iterName.size() - 1);
    }

    public void pushIfName(String name) {
        this.ifName.add(name);
    }

    public void popIfName() {
        this.ifName.remove(ifName.size() - 1);
    }

    public int iterIfCount() {
        this.ifCount++;
        return ifCount - 1;
    }

    public int iterTempVar() {
        tempVarCount++;
        return tempVarCount - 1;
    }

    public int iterIterCount() {
        iterCount++;
        return iterCount - 1;
    }

    public void addIr(CLIR ir) {
        if (setNextLabel) {
            ir.setLabel(nextLabel);
            setNextLabel = false;
            int index = this.ir.size();
            this.labels.put(nextLabel, index);

            ArrayList<Integer> del = new ArrayList<>();
            for (int i : zip.keySet()) {
                if (zip.get(i).equals(nextLabel)) {
                    unzip(i, index - i);
                    del.add(i);
                }
            }
            for (int i : del) {
                zip.remove(i);
            }
        }
        this.ir.add(ir);
    }

    public void nextLabel(String label) {
        if (this.setNextLabel) {
            this.addNop();
        }
        this.nextLabel = label;
        this.setNextLabel = true;
    }

    public String getCurIterName() {
        if (this.iterName.size() > 0) {
            return iterName.get(iterName.size() - 1);
        }
        return null;
    }

    public void addZip(String listen, CLIR ir) {
        int index = this.ir.size();
//        this.ir.add(ir);
        addIr(ir);
        zip.put(index, listen);
    }

    public int getLabelOffset(String label) {
        int index = labels.get(label);
        return index - ir.size();
    }

    public CLUtilIRList getIrList() {
        return ir;
    }
}
