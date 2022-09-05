package cloudladder.core.compiler.ast.program;

import cloudladder.core.compiler.ast.AST;
import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.statement.ASTCompoundStatement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTFunctionDefinition extends ASTProgramUnit {
    private final ASTToken name;
    private final ArrayList<ASTToken> params;

    private final ASTCompoundStatement body;
}
