package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ASTSelectionStatement extends ASTStatement {
    private ASTExpression condition;
    private ASTStatement statementTrue;
    private ASTStatement statementFalse;

    private int line;
}
