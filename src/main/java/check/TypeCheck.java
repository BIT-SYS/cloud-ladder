package check;

import ast.*;
import symboltable.GenericType;
import symboltable.ProcedureSymbol;
import symboltable.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Error.debugTypeCheck;
import static util.Error.die;
import static util.Type.*;


public class TypeCheck extends ASTBaseListener {

    private static final Map<GenericType, Type> genericHelper = new HashMap<>();
    // 👇 首先要exit所有表达式

    @Override
    public void exitRangeListInitializer(RangeListInitializer ctx) {
        if (!ctx.start.evalType.equals(ctx.end.evalType)) {
            die("Type Check: RangeListInitializer", "start & end is not the same type!");
        }
        ctx.evalType = getType("List<" + ctx.start.evalType + ">");
    }

    @Override
    public void exitValuesListInitializer(ValuesListInitializer ctx) {
        List<ExpressionNode> values_tmp = new ArrayList<>(ctx.values);
        ExpressionNode firstValue = ctx.values.get(0);
        values_tmp.removeIf((ExpressionNode v) -> v.evalType.equals(firstValue.evalType));
        if (0 != values_tmp.size()) {
            die("Type Check: ValuesListInitializer", "[a, b, c, ...] is not the same type!");
        }
        ctx.evalType = getType("List<" + firstValue.evalType + ">");
    }

    @Override
    public void exitLambdaExpression(LambdaExpression ctx) {
        ctx.evalType = getType(ctx.retType);
    }

    @Override
    public void exitCallExpression(CallExpression ctx) {
        if (debugTypeCheck) {
            System.out.println("exit call" + ctx.symbol);
        }
        if (null == ctx.symbol) {
            die("Type Check: CallExpression", "Procedure symbol " + ctx.callee.name + " not found!");
        }

        int give = ctx.arguments.size();
        ProcedureSymbol proc = ((ProcedureSymbol) ctx.symbol);
        List<Type> signature = proc.signature;
        int need = signature.size() - 1; // 减去的是返回值的类型
        if (need == give) {
            if (ctx.isMethodCall != proc.isMethod()) {
                die("Type Check: CallExpression", proc + (proc.isMethod()?" is a method":" is not a method"));
            }
            for (int i = 0; i < give; i++) {
                // 比较
                ExpressionNode ithArg = ctx.arguments.get(i);
                Type ithParType = signature.get(i);

                // 这个参数可能是函数
                if (((ithArg instanceof Identifier && ctx.scope.resolve(((Identifier) ithArg).name) instanceof ProcedureSymbol)
                        || ithArg instanceof LambdaExpression)
                        && ithParType.toString().equals("Proc")) {
                    continue; // 如果这个实参是函数，且形参是Proc，就别往下走了：在这里批准了。
                }

                // 检查参数类型
                if (!sameParameterType(ithArg.evalType, ithParType)) {
                    die("Type Check: CallExpression", ctx.symbol + " : No. " + (i + 1) + " argument is not correct Type");
                }
                if (containsGeneric(ithParType)) {
                    // 是泛型就建表、查表
                    GenericType gType = (GenericType) getInnermostElementType(ithParType);
                    Type matchType = matchGenericType(ithArg.evalType, ithParType);
                    genericHelper.computeIfAbsent(gType, k -> matchType);
                    if (!genericHelper.get(gType).equals(matchType)) {
                        die("Type Check: CallExpression", "Wrong generic Type! The Types of " + gType + " is different!");
                    }
                }
            }
            // 填写返回值
            Type retType = ctx.symbol.type;
            if (containsGeneric(retType)) {
                GenericType gType = (GenericType) getInnermostElementType(retType);
                Type corriType = genericHelper.get(gType);
                ctx.evalType = replceGenericType(retType, corriType);
            } else {
                ctx.evalType = retType;
            }
            genericHelper.clear();
        } else {
            die("Type Check: CallExpression", "Wrong arguments number of " + ctx.symbol);
        }
    }

    @Override
    public void exitMemberExpression(MemberExpression ctx) {
        die("Type Check: MemberExpression", "You can't be here ᛠᛡᛢᛣᛤᛥᛦᛧᛨᛩᛪ᛫᛬᛭ᛮᛯ");
    }

    @Override
    public void exitIdentifier(Identifier ctx) {
        if (null == ctx.symbol) {
            die("Type Check: Identifier", "Identifier " + ctx.name + " not found!");
        }
        ctx.evalType = ctx.symbol.type;
    }

    @Override
    public void exitArithmeticExpression(ArithmeticExpression ctx) {
        // 加啊，减啊，可不一定只有同类型才行。但目前认为不行，必须强制转换
        Type left = ctx.left.evalType;
        Type right = ctx.right.evalType;
        if (!left.equals(right)) {
            die("Type Check: ArithmeticExpression", "left & right is not the same type: " + left + " & " + right);
        }
        ctx.evalType = left;
    }

    @Override
    public void exitIndexExpression(IndexExpression ctx) {
        Type type = ctx.left.evalType;
        if (type.toString().startsWith("List<")) {
            // 只有List<>可以有[]访问下标
            ctx.evalType = getElementType(type);
        } else {
            die("Type Check: IndexExpression", "left is not a List Type");
        }
    }

    @Override
    public void exitForBlock(ForBlock ctx) {
        Type type = ctx.for_expr.evalType;
        if (!type.toString().startsWith("List<")) {
            die("Type Check: ForBlock", "for_expr is not a List Type");
        }
        if (!ctx.for_id.evalType.equals(getElementType(type))) {
            die("Type Check: ForBlock",
                    "can not retrieve " + ctx.for_id.evalType + " from " + type);
        }
    }
}
