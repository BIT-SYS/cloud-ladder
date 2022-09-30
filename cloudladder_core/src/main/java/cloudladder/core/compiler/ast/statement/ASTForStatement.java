package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ASTForStatement extends ASTStatement {
    private ASTStatement init;
    private ASTExpression condition;
    private ASTStatement step;

    private ASTStatement content;

    private ASTToken label;
}
