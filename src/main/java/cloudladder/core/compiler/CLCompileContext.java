package cloudladder.core.compiler;

import cloudladder.core.error.CLCompileError;
import cloudladder.core.error.CLCompileErrorType;
import cloudladder.core.ir.CLIR;
import cloudladder.core.ir.CLIRJump;
import cloudladder.core.object.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class CLCompileContext {
    public final ArrayList<CLIR> ir;
    private final HashMap<String, Integer> stringLiterals;
    private final HashMap<Integer, Integer> intLiteral;
    private final HashMap<Double, Integer> doubleLiteral;
    private final ArrayList<CLObject> constants;
    private final HashMap<String, Integer> namesMap;
    private final ArrayList<String> names;
    private final HashSet<String> localNames;

    private final HashMap<String, ArrayList<JumpInfo>> jumpDeferList;

    private final ArrayList<String> loopStack;
    private static int loopName;

    @AllArgsConstructor
    public static class JumpInfo {
        public int pos;
        public CLIRJump jump;
    }

    public CLCompileContext() {
        this.ir = new ArrayList<>();
        this.stringLiterals = new HashMap<>();
        this.intLiteral = new HashMap<>();
        this.doubleLiteral = new HashMap<>();
        this.constants = new ArrayList<>();
        this.namesMap = new HashMap<>();
        this.names = new ArrayList<>();
        this.localNames = new HashSet<>();
        this.loopStack = new ArrayList<>();
        this.jumpDeferList = new HashMap<>();
    }

    public String pushLoopContext(String name) {
        if (name == null) {
            name = "loop" + CLCompileContext.loopName;
            CLCompileContext.loopName++;
        }

        this.loopStack.add(name);

        return name;
    }

    public String popLoopContext() {
        String ctx = this.loopStack.get(this.loopStack.size() - 1);
        this.loopStack.remove(this.loopStack.size() - 1);
        return ctx;
    }

    public void signalJump(String label, int pos) {
        if (this.jumpDeferList.containsKey(label)) {
            ArrayList<JumpInfo> jumpInfos = this.jumpDeferList.get(label);
            for (JumpInfo jumpInfo : jumpInfos) {
                jumpInfo.jump.step = pos - jumpInfo.pos - 1;
            }

            this.jumpDeferList.remove(label);
        }
    }

    public void addBreak(String loopName) throws CLCompileError {
        if (this.loopStack.size() == 0) {
            throw new CLCompileError(CLCompileErrorType.BreakNotInLoop, "break have to be in a loop");
        }
        if (loopName != null) {
            boolean flag = false;
            for (String name : this.loopStack) {
                if (name.equals(loopName)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new CLCompileError(CLCompileErrorType.BreakNotInLoop, "break loop `" + loopName + "` not found");
            }
        }

        if (loopName == null) {
            loopName = this.loopStack.get(this.loopStack.size() - 1);
        }

        CLIRJump ir = new CLIRJump(0);
        this.addIr(ir);

        String end = loopName + "end";
        if (!this.jumpDeferList.containsKey(end)) {
            this.jumpDeferList.put(end, new ArrayList<>());
        }
        ArrayList<JumpInfo> jumpInfos = this.jumpDeferList.get(end);
        jumpInfos.add(new JumpInfo(this.ir.size() - 1, ir));
    }

    public void addContinue(String loopName) throws CLCompileError {
        if (this.loopStack.size() == 0) {
            throw new CLCompileError(CLCompileErrorType.ContinueNotInLoop, "continue have to be in a loop");
        }
        if (loopName != null) {
            boolean flag = false;
            for (String name : this.loopStack) {
                if (name.equals(loopName)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new CLCompileError(CLCompileErrorType.ContinueNotInLoop, "continue loop `" + loopName + "` not found");
            }
        }

        if (loopName == null) {
            loopName = this.loopStack.get(this.loopStack.size() - 1);
        }

        CLIRJump ir = new CLIRJump(0);
        this.addIr(ir);

        String start = loopName + "start";
        if (!this.jumpDeferList.containsKey(start)) {
            this.jumpDeferList.put(start, new ArrayList<>());
        }
        ArrayList<JumpInfo> jumpInfos = this.jumpDeferList.get(start);
        jumpInfos.add(new JumpInfo(this.ir.size() - 1, ir));
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

    public int addConstant(CLObject f) {
        this.constants.add(f);
        return this.constants.size() - 1;
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
        for (CLObject c : this.constants) {
            if (c instanceof CLFunctionDefinition fd) {
                for (String closureNonLocalName : fd.codeObject.nonLocalNames) {
                    if (!this.localNames.contains(closureNonLocalName)) {
                        nonLocalNames.add(closureNonLocalName);
                    }
                }
            }
        }

        code.nonLocalNames = nonLocalNames;

        return code;
    }
}
