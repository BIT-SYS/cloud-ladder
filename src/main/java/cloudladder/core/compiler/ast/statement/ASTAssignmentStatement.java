package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ASTAssignmentStatement extends ASTStatement {
    private final ASTExpression left;
    private final ASTExpression right;
    private final ASTToken op;
}
