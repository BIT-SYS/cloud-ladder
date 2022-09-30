package cloudladder.core.compiler.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.Token;

@AllArgsConstructor
@Getter
@Setter
public class ASTToken extends AST {
    private int start;
    private int end;
    private String text;
    private int line;

    public ASTToken(Token token) {
        this.start = token.getStartIndex();
        this.end = token.getStopIndex();
        this.text = token.getText();
        this.line = token.getLine();
    }
}
