package ast.node.flow;

import ast.AstVisitor;
import ast.node.Text;
import org.antlr.v4.runtime.ParserRuleContext;

public class Break extends Text {
    public Break(ParserRuleContext ctx) {
        super(ctx, "break");
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
