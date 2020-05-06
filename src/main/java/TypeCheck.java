import symboltable.ProcedureSymbol;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

import static symboltable.Utils.*;

public class TypeCheck extends ASTBaseListener {

    // 👇 首先要exit所有表达式

    @Override
    public void exitRangeListInitializer(RangeListInitializer ctx) {
        if (!sameType(ctx.start.evalType, ctx.end.evalType)) {
            Utils.err("Type Check: RangeListInitializer", "start & end is not the same type!");
        }
        ctx.evalType = getType("List<" + ctx.start.evalType + ">");
    }

    @Override
    public void exitValuesListInitializer(ValuesListInitializer ctx) {
        List<ExpressionNode> values_tmp = new ArrayList<>(ctx.values);
        ExpressionNode firstValue = ctx.values.get(0);
        values_tmp.removeIf((ExpressionNode v) -> sameType(v.evalType, firstValue.evalType));
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
        System.out.println("exit call" + ctx.symbol);
        if (null == ctx.symbol) {
            Utils.err("Type Check: CallExpression", "Procedure symbol " + ctx.callee.name + " not found!");
        }
        ctx.evalType = ctx.symbol.type;
        int give = ctx.arguments.size();
        List<Type> signature = ((ProcedureSymbol) ctx.symbol).signature;
        int need = signature.size() - 1; // 减去的是返回值的类型
        if (need == give || give == need - 1) {
            int offset = need == give ? 0 : 1; // 0 是直接调用函数，1 是调用方法
            for (int i = 0; i < give; i++) {
                ExpressionNode ithArg = ctx.arguments.get(i);
                // 这个参数可能是函数
                if (((ithArg instanceof Identifier && ctx.scope.resolve(((Identifier) ithArg).name) instanceof ProcedureSymbol)
                        || ithArg instanceof LambdaExpression)
                        && signature.get(i + offset).toString().equals("Proc")) {
                    continue;
                }
                if (!sameParameterType(ctx.arguments.get(i).evalType, signature.get(i + offset))) {
                    Utils.err("Type Check: CallExpression", ctx.symbol + " : No. " + (i + 1 + offset) + " argument is not correct Type");
                }
                // todo 是泛型就建表、查表
            }
        } else {
            Utils.err("Type Check: CallExpression", "There are " + (need > give ? "more" : "fewer") + " arguments applied to " + ctx.symbol);
        }
    }

    @Override
    public void enterMemberExpression(MemberExpression ctx) {
        System.out.println("enter mem" + ctx.property.symbol);
    }

    @Override
    public void exitMemberExpression(MemberExpression ctx) {
        System.out.println("exit mem" + ctx.property.symbol + "\n\n");
        // 原来 MemberExpression 竟然是包括方法调用……
        // todo 检查
        ExpressionNode callee = ctx.property;
//        while (true) {
//            assert callee.symbol instanceof ProcedureSymbol;
//            ProcedureSymbol procedure = (ProcedureSymbol) callee.symbol;
//            assert !procedure.arguments.isEmpty();
//            Symbol firstSymbol = procedure.arguments.get("self");
//            if (null == firstSymbol) {
//                ProcedureSymbol next = procedure.next;
//                if (null == next)
//                    Utils.err("Type Check: MemberExpression", "Procedure " + procedure.name + " is not a method");
//                else {
//                    callee.symbol = next;
//                    continue;
//                }
//            }
//            assert null != firstSymbol;
//            if (!typeEquals(firstSymbol.type, ctx.object.evalType)) {
//                ProcedureSymbol next = procedure.next;
//                if (null == next)
//                    Utils.err("Type Check: MemberExpression", "Procedure " + procedure.name + " is not a method of " + ctx.object.evalType);
//                else {
//                    callee.symbol = next;
//                    continue;
//                }
//            }
//            break;
//        }
        ctx.evalType = ctx.property.evalType;
    }

    @Override
    public void exitIdentifier(Identifier ctx) {
        if (null == ctx.symbol) {
            Utils.err("Type Check: Identifier", "Identifier " + ctx.name + " not found!");
        }
        ctx.evalType = ctx.symbol.type;
    }

//    @Override
//    public void exitLiteral(Literal ctx) {
//        // 是放在这里好还是建立AST时？
//    }

    @Override
    public void exitArithmeticExpression(ArithmeticExpression ctx) {
        if (!sameType(ctx.left.evalType, ctx.right.evalType)) {
            Utils.err("Type Check: ArithmeticExpression", "left & right is not the same type!");
        }
        ctx.evalType = ctx.left.evalType;
    }

    @Override
    public void exitIndexExpression(IndexExpression ctx) {
        String typeStr = ctx.left.evalType.toString();
        if (typeStr.startsWith("List<")) {
            // 只有List<>可以有[]访问下标
            ctx.evalType = getElementType(typeStr);
        } else {
            Utils.err("Type Check: IndexExpression", "left is not a List Type");
        }
    }

    @Override
    public void exitForBlock(ForBlock ctx) {
        String typeStr = ctx.for_expr.evalType.toString();
        if (!typeStr.startsWith("List<")) {
            Utils.err("Type Check: ForBlock", "for_expr is not a List Type");
        }
        if (!sameType(ctx.for_id.evalType, getElementType(typeStr))) {
            Utils.err("Type Check: ForBlock",
                    "can not retrieve " + ctx.for_id.evalType + " from " + typeStr);
        }
    }

//    @Override
//    public void exitAssign(Assign ctx) {
//        if (!typeEquals(ctx.lvalue.evalType, ctx.rvalue.evalType)) {
//            Utils.err("Type Check: Assign", "left & right is not the same type!");
//        }
//    }
}
