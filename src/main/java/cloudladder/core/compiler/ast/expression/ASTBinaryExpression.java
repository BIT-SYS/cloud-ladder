package cloudladder.core.compiler.ast.expression;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.Token;

@AllArgsConstructor
@Getter
@Setter
public class ASTBinaryExpression extends ASTExpression {
    private ASTToken op;
    private ASTExpression left;
    private ASTExpression right;
}
