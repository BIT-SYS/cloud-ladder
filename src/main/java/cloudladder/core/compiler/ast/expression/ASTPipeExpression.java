package cloudladder.core.compiler.ast.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ASTPipeExpression extends ASTExpression {
    private final ASTExpression left;
    private final ASTExpression right;
}
