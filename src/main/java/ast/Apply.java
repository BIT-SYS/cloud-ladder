package ast;

import org.antlr.v4.runtime.ParserRuleContext;

// 更加广义的apply，不是函数也可以
// 第一个孩子是op，剩下的是参数
public class Apply extends Expression {
    public Apply(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "Apply";
    }
}
