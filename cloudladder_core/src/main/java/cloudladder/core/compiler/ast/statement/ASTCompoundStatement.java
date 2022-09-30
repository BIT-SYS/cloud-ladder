package cloudladder.core.compiler.ast.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class ASTCompoundStatement extends ASTStatement {
    private ArrayList<ASTStatement> items;
}
