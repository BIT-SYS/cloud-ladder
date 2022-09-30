package cloudladder.core.compiler.ast.program;

import cloudladder.core.compiler.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ASTProgram extends AST {
    private final ArrayList<ASTProgramUnit> programUnits;
}
