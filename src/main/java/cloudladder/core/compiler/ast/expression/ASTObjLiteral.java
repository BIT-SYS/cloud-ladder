package cloudladder.core.compiler.ast.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTObjLiteral extends ASTExpression {
    private final ArrayList<ASTObjItem> items;
}
