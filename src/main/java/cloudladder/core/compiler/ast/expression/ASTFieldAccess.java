package cloudladder.core.compiler.ast.expression;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ASTFieldAccess extends ASTExpression {
    private ASTExpression left;
    private ASTToken right;
}
