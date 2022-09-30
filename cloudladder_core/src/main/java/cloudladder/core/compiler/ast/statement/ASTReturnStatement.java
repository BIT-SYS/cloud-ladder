package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTReturnStatement extends ASTStatement {
    private final ASTExpression value;
}
