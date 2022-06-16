package cloudladder.core.compiler.ast.type;

import cloudladder.core.compiler.ast.ASTToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class ASTGenericType extends ASTType {
    private ASTToken name;
    private ArrayList<ASTType> types;
}
