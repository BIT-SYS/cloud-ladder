package cloudladder.core.runtime.data;

import cloudladder.core.compiler.CLCompileContext;
import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.compiler.ast.program.ASTFunctionDefinition;
import cloudladder.core.compiler.ast.statement.ASTCompoundStatement;
import cloudladder.core.ir.CLIR;
import cloudladder.core.ir.CLIRRet;
import cloudladder.core.misc.CLUtilIRList;
import cloudladder.core.runtime.data.CLData;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class CLFunction extends CLData {
    @Getter
    private final ArrayList<String> params;

    @Getter
    @Setter
    private String name;

    @Setter
    @Getter
    private CLRtScope parentScope = null;

    public CLFunction() {
        params = new ArrayList<>();
    }

    public void addParam(String param) {
        params.add(param);
    }

    public abstract void execute(CLRtEnvironment env);

    @Override
    public String toString() {
        return "f[" + this.name + "]";
    }
}
