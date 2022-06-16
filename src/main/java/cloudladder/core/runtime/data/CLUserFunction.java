package cloudladder.core.runtime.data;

import cloudladder.core.compiler.CLCompileContext;
import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.compiler.ast.statement.ASTCompoundStatement;
import cloudladder.core.ir.CLIR;
import cloudladder.core.ir.CLIRRet;
import cloudladder.core.misc.CLUtilIRList;
import cloudladder.core.runtime.env.CLRtEnvironment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CLUserFunction extends CLFunction {
    private ASTCompoundStatement ast;
    private boolean compiled;
    private CLUtilIRList codes;

    public CLUserFunction(ASTCompoundStatement ast) {
        this.ast = ast;
        compiled = false;
        codes = null;
    }

    public void compile() {
        CLCompileContext context = new CLCompileContext();
        CLCompiler compiler = new CLCompiler();
        compiler.compileCompoundStatement(ast, context);
        context.endFunction();

        this.codes = context.getIrList();
        this.compiled = true;
    }

    @Override
    public void execute(CLRtEnvironment env) {
        if (!this.compiled) {
            this.compile();
        }
        this.codes.execute(env);
    }
}
