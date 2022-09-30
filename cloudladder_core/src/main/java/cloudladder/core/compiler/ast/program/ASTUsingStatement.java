package cloudladder.core.compiler.ast.program;

import cloudladder.core.compiler.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ASTUsingStatement extends ASTProgramUnit {
    public String scope;
    public String name;
    public String path;
    public String alias;
}
