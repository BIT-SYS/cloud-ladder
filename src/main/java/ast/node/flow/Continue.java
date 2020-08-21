package ast.node.flow;

import ast.AstVisitor;
import ast.node.Text;
import org.antlr.v4.runtime.ParserRuleContext;

public class Continue extends Text {
    public Continue(ParserRuleContext ctx) {
        super(ctx, "continue");
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
