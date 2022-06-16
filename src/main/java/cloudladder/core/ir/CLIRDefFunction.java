package cloudladder.core.ir;

import cloudladder.core.runtime.data.CLFunction;
import cloudladder.core.runtime.data.CLUserFunction;
import cloudladder.core.compiler.ast.statement.ASTCompoundStatement;
import cloudladder.core.runtime.data.CLReference;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class CLIRDefFunction extends CLIR {
    private final String name;
    private final ArrayList<String> params;
    private final ASTCompoundStatement def;

    @Override
    public void execute(CLRtEnvironment environment) {
        CLFunction func = new CLUserFunction(def);
        func.setParentScope(environment.getCurrentScope());
        func.setName(name);
        for (String p : params) {
            func.addParam(p);
        }

        CLReference ref = new CLReference(func);
        environment.addVariable(name, ref);
        environment.incPC();
    }

    @Override
    public String toSelfString() {
        StringBuilder sb = new StringBuilder();
        sb.append("deffunc     ").append(this.name).append("(");
        for (String p : params) {
            sb.append(p).append(",");
        }
        sb.append(")");

        return new String(sb);
    }
}
