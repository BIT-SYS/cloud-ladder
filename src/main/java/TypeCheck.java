import java.util.ArrayList;
import java.util.List;

import static symboltable.Utils.getType;
import static symboltable.Utils.typeEquals;

public class TypeCheck extends ASTBaseListener {

    // 👇 首先要exit所有表达式

    @Override
    public void exitRangeListInitializer(RangeListInitializer ctx) {
        if (!typeEquals(ctx.start.evalType, ctx.end.evalType)) {
            Utils.err("Type Check: RangeListInitializer", "start & end is not the same type!");
        }
        ctx.evalType = getType("List<" + ctx.start.evalType + ">");
    }

    @Override
    public void exitValuesListInitializer(ValuesListInitializer ctx) {
        List<ExpressionNode> values_tmp = new ArrayList<>(ctx.values);
        ExpressionNode firstValue = ctx.values.get(0);
        values_tmp.removeIf((ExpressionNode v) -> v.evalType != firstValue.evalType);
        if (0 != values_tmp.size()) {
            Utils.err("Type Check: ValuesListInitializer", "[a, b, c, ...] is not the same type!");
        }
        ctx.evalType = getType("List<" + firstValue.evalType + ">");
    }

    @Override
    public void exitLambdaExpression(LambdaExpression ctx) {
        ctx.evalType = getType(ctx.retType);
    }

    @Override
    public void exitCallExpression(CallExpression ctx) {
        if (null == ctx.symbol) {
            Utils.err("Type Check: CallExpression", "Procedure symbol " + ctx.callee.name + " not found!");
        }
        ctx.evalType = ctx.symbol.type;
    }

    @Override
    public void exitMemberExpression(MemberExpression ctx) {
        // 实际并没有 MemberExpression
    }

    @Override
    public void exitIdentifier(Identifier ctx) {
        if (null == ctx.symbol) {
            Utils.err("Type Check: Identifier", "Identifier " + ctx.name + " not found!");
        }
        ctx.evalType = ctx.symbol.type;
    }

    @Override
    public void exitLiteral(Literal ctx) {
        // 是放在这里好还是建立AST时？
    }

    @Override
    public void exitBinaryExpression(BinaryExpression ctx) {
        if (!typeEquals(ctx.left.evalType, ctx.right.evalType)) {
            //todo
            Utils.err("Type Check: BinaryExpression", "left & right is not the same type!");
        }
    }

    @Override
    public void exitIndexExpression(IndexExpression ctx) {
        String typeStr = ctx.left.evalType.toString();
        if (typeStr.startsWith("List<")) {
            // 只有List<>可以有[]访问下标
            ctx.evalType = getType(typeStr.substring(typeStr.indexOf('<'), typeStr.length() - 1));
        } else {
            Utils.err("Type Check: IndexExpression", "left is not a List Type");
        }
    }
}
