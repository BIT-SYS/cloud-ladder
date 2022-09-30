package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ASTWhileStatement extends ASTStatement {
    private final ASTExpression condition;
    private final ASTStatement statement;

    private final ASTToken label;
}
