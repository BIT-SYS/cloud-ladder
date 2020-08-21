package ast.node.flow;

import ast.AstVisitor;
import ast.node.Text;
import org.antlr.v4.runtime.ParserRuleContext;

public class Pass extends Text {
    public Pass(ParserRuleContext ctx) {
        super(ctx, "pass");
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
