package check;

import symboltable.GenericType;
import symboltable.ProcedureSymbol;
import symboltable.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ast.*;

import static util.Error.die;
import static util.Type.*;


public class TypeCheck extends ASTBaseListener {

    private static Map<GenericType, Type> genericHelper = new HashMap<>();
    // 👇 首先要exit所有表达式

    @Override
    public void exitRangeListInitializer(RangeListInitializer ctx) {
        if (!sameType(ctx.start.evalType, ctx.end.evalType)) {
            die("Type Check: RangeListInitializer", "start & end is not the same type!");
        }
        ctx.evalType = getType("List<" + ctx.start.evalType + ">");
    }

    @Override
    public void exitValuesListInitializer(ValuesListInitializer ctx) {
        List<ExpressionNode> values_tmp = new ArrayList<>(ctx.values);
        ExpressionNode firstValue = ctx.values.get(0);
        values_tmp.removeIf((ExpressionNode v) -> sameType(v.evalType, firstValue.evalType));
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
        System.out.println("exit call" + ctx.symbol);
        if (null == ctx.symbol) {
            die("Type Check: CallExpression", "Procedure symbol " + ctx.callee.name + " not found!");
        }

        int give = ctx.arguments.size();
        List<Type> signature = ((ProcedureSymbol) ctx.symbol).signature;
        int need = signature.size() - 1; // 减去的是返回值的类型
        if (need == give || give == need - 1 && ((ProcedureSymbol) ctx.symbol).isMethod()) {
            int offset = need == give ? 0 : 1; // 0 是直接调用函数，1 是调用方法
            for (int i = 0; i < give; i++) {
                ExpressionNode ithArg = ctx.arguments.get(i);
                Type ithParType = signature.get(i + offset);

                // 这个参数可能是函数
                if (((ithArg instanceof Identifier && ctx.scope.resolve(((Identifier) ithArg).name) instanceof ProcedureSymbol)
                        || ithArg instanceof LambdaExpression)
                        && ithParType.toString().equals("Proc")) {
                    continue; // 如果这个实参是函数，且形参是Proc，就别往下走了：在这里批准了。
                }
                if (!sameParameterType(ithArg.evalType, ithParType)) {
                    die("Type Check: CallExpression", ctx.symbol + " : No. " + (i + 1 + offset) + " argument is not correct Type");
                }
                if (containsGeneric(ithParType)) {
                    // 是泛型就建表、查表
                    GenericType gType = (GenericType) getInnermostElementType(ithParType);
                    Type matchType = matchGenericType(ithArg.evalType, ithParType);
                    genericHelper.computeIfAbsent(gType, k -> matchType);
                    if (!sameType(genericHelper.get(gType), matchType)) {
                        die("Type Check: CallExpression", "Wrong generic Type! The Types of " + gType + " is different!");
                    }
                }
            }

            if (0 == offset) {
                // 检查返回值
                Type retType = ctx.symbol.type;
                if (containsGeneric(retType)) {
                    GenericType gType = (GenericType) getInnermostElementType(retType);
                    Type corriType = genericHelper.get(gType);
                    ctx.evalType = replceGenericType(retType, corriType);
                } else {
                    ctx.evalType = retType;
                }
                genericHelper.clear();
            }
        } else {
            die("Type Check: CallExpression", "There are " + (need > give ? "more" : "fewer") + " arguments applied to " + ctx.symbol);
        }

    }

    @Override
    public void exitMemberExpression(MemberExpression ctx) {
        System.out.println("exit mem" + ctx.property.symbol + "\n\n");
        // 原来 MemberExpression 竟然是包括方法调用……
        ExpressionNode callee = ctx.property;
        assert callee.symbol instanceof ProcedureSymbol;
        ProcedureSymbol procedure = (ProcedureSymbol) callee.symbol;
        if (procedure.name.equals("toString")){
            System.out.println("ssss");
        }
        if (procedure.isMethod()) {
            List<Type> signature = procedure.signature;
            Type selfType = signature.get(0);
            if (!sameParameterType(ctx.object.evalType, selfType)) {
                die("Type Check: MemberExpression",
                        "Procedure " + procedure.name + " is not a method of " + ctx.object.evalType);
            }
            if (containsGeneric(selfType)) {
                GenericType gType = (GenericType)getInnermostElementType(selfType);
                Type matchType = matchGenericType(ctx.object.evalType, selfType);
                genericHelper.computeIfAbsent(gType, k -> matchType); // 有可能self的类型是第一次出现的TypeX
                if (!sameType(genericHelper.get(gType), matchType)) {
                    die("Type Check: MemberExpression", "Procedure " + procedure.name + " have wrong GenericType");
                }
            }
            Type retType = procedure.type ;
            if (containsGeneric(retType)) {
                GenericType gType = (GenericType) getInnermostElementType(retType);
                Type corriType = genericHelper.get(gType);
                ctx.evalType = replceGenericType(retType, corriType);
            } else {
                ctx.evalType = retType;
            }
            genericHelper.clear();
        } else {
            die("Type Check: MemberExpression", "Procedure " + procedure.name + " is not a method");
        }
    }

    @Override
    public void exitIdentifier(Identifier ctx) {
        if (null == ctx.symbol) {
            die("Type Check: Identifier", "Identifier " + ctx.name + " not found!");
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
            die("Type Check: ArithmeticExpression", "left & right is not the same type!");
        }
        ctx.evalType = ctx.left.evalType;
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
        if (!sameType(ctx.for_id.evalType, getElementType(type))) {
            die("Type Check: ForBlock",
                    "can not retrieve " + ctx.for_id.evalType + " from " + type);
        }
    }

//    @Override
//    public void exitAssign(Assign ctx) {
//        if (!typeEquals(ctx.lvalue.evalType, ctx.rvalue.evalType)) {
//            err("Type Check: Assign", "left & right is not the same type!");
//        }
//    }
}
