package cloudladder.core.compiler.ast.expression;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.Token;

@AllArgsConstructor
@Getter
@Setter
public class ASTIdentifierExpression extends ASTExpression {
    private ASTToken token;
}
