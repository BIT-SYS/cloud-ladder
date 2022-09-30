package cloudladder.core.compiler.ast.program;

import cloudladder.core.compiler.ast.statement.ASTStatement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ASTRootStatement extends ASTProgramUnit {
    private final ASTStatement statement;
}
