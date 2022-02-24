package cloudladder.core.compiler.ast.type;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ASTNormalType extends ASTType {
    private ASTToken token;
}
