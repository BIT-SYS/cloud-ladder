package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.AST;
import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.ASTExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ASTDataStatementItem extends AST {
    private ASTToken name;
    private ASTExpression initializer;
}
