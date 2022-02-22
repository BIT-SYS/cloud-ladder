package cloudladder.core.misc;

import cloudladder.core.ir.CLIR;
import cloudladder.core.ir.CLIRRet;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class CLUtilIRList {
    private ArrayList<CLIR> list;

    public CLUtilIRList() {
        this.list = new ArrayList<>();
    }

    public void add(CLIR ir) {
        this.list.add(ir);
    }

    public int size() {
        return list.size();
    }

    public CLIR get(int index) {
        return list.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CLIR ir : list) {
            sb.append(ir.toString()).append("\n");
        }

        return new String(sb);
    }

    public void execute(CLRtEnvironment env) {
        env.setPc(0);
        while (!env.isError()) {
            int pc = env.getPc();
            if (pc >= this.list.size()) {
                return;
            }
            CLIR ir = this.list.get(pc);
            ir.execute(env);
            if (ir instanceof CLIRRet) {
                return;
            }
        }
    }
}
