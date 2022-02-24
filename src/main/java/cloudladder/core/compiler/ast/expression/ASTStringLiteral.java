package cloudladder.core.compiler.ast.expression;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ASTStringLiteral extends ASTExpression {
    private ASTToken token;
}
