package cloudladder.core.compiler.ast.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTFunctionCall extends ASTExpression {
    private final ArrayList<ASTExpression> args;
    private final ASTExpression func;
}
