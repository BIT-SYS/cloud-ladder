package cloudladder.core.compiler.ast.program;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTImport extends ASTProgramUnit {
    private final ASTToken as;
    private final ASTToken path;

    public String fullPath() {
        String str = path.getText();
        return str.substring(1, str.length() - 1);
    }

    public String getAsString() {
        if (as == null) {
            return null;
        }
        return as.getText();
    }
}
