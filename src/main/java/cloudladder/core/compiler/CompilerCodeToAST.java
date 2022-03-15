package cloudladder.core.compiler;

import cloudladder.core.compiler.ast.AST;

public interface CompilerCodeToAST {
    AST compileCodeToAST(String code);
}
