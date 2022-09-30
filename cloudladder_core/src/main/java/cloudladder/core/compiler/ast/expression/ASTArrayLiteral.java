package cloudladder.core.compiler.ast.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTArrayLiteral extends ASTExpression {
    private final ArrayList<ASTExpression> items;
}
