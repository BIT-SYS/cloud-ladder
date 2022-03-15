package cloudladder.core.compiler;

import cloudladder.core.compiler.ast.AST;
import cloudladder.core.ir.CLIR;

import java.util.ArrayList;

public interface CompilerASTToIR {
    ArrayList<CLIR> compileASTToIR(AST ast);
}
