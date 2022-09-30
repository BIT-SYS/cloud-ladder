package cloudladder.core.compiler.ast.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class ASTDataStatement extends ASTStatement {
    public ArrayList<ASTDataStatementItem> items;
}
