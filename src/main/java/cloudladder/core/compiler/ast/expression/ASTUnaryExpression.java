package cloudladder.core.compiler.ast.expression;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
public class ASTUnaryExpression extends ASTExpression {
    private final ASTToken op;
    private final ASTExpression expression;
}
