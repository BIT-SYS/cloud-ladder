package cloudladder.core.compiler.ast.statement;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ASTContinueStatement extends ASTStatement {
    private ASTToken label;
}
