package ast.node.type;

import ast.node.Text;
import org.antlr.v4.runtime.ParserRuleContext;

abstract public class Type extends Text {
    public Type(ParserRuleContext ctx, String string) {
        super(ctx, string);
    }
}
