package cloudladder.core.compiler.ast.expression;

import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.statement.ASTCompoundStatement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTFunctionExpression extends ASTExpression {
    private final ArrayList<ASTToken> params;

    private final ASTCompoundStatement ast;
}
