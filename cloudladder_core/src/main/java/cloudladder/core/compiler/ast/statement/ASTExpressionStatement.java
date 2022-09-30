package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ASTExpressionStatement extends ASTStatement {
    private final ASTExpression expression;
}
