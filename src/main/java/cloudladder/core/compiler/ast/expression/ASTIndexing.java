package cloudladder.core.compiler.ast.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ASTIndexing extends ASTExpression {
    private ASTExpression indexer;
    private ASTExpression index;
}
