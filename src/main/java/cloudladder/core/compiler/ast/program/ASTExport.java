package cloudladder.core.compiler.ast.program;

import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ASTExport extends ASTProgramUnit {
    private ASTToken name;
    private ASTExpression expression;
}
