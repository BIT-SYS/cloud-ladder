package ast.node.flow;

import ast.node.Text;
import org.antlr.v4.runtime.ParserRuleContext;

public class Pass extends Text {
    public Pass(ParserRuleContext ctx) {
        super(ctx, "pass");
    }
}
