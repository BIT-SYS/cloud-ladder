package cloudladder.core.compiler.ast.expression;

import cloudladder.core.compiler.ast.AST;
import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ASTObjItem extends AST {
    private final boolean isDynamic;
    private final boolean isSingle;
    private final boolean isString;
    private final ASTToken key;
    private final ASTExpression dyKey;
    private final ASTExpression value;
}
