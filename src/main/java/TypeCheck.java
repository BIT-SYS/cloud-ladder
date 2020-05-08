import AST.*;
import symboltable.ProcedureSymbol;
import symboltable.Symbol;

import java.util.ArrayList;
import java.util.List;

import static symboltable.Utils.*;

public class TypeCheck extends ASTBaseListener {

    // 👇 首先要exit所有表达式

    @Override
    public void exitRangeListInitializer(RangeListInitializer ctx) {
        if (!typeEquals(ctx.start.evalType, ctx.end.evalType)) {
            Utils.err("Type Check: AST.RangeListInitializer", "start & end is not the same type!");
        }
        ctx.evalType = getType("List<" + ctx.start.evalType + ">");
    }

    @Override
    public void exitValuesListInitializer(ValuesListInitializer ctx) {
        List<ExpressionNode> values_tmp = new ArrayList<>(ctx.values);
        ExpressionNode firstValue = ctx.values.get(0);
        values_tmp.removeIf((ExpressionNode v) -> typeEquals(v.evalType, firstValue.evalType));
        if (0 != values_tmp.size()) {
            Utils.err("Type Check: AST.ValuesListInitializer", "[a, b, c, ...] is not the same type!");
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
            Utils.err("Type Check: AST.CallExpression", "Procedure symbol " + ctx.callee.name + " not found!");
        }
        ctx.evalType = ctx.symbol.type;
    }

    @Override
    public void exitMemberExpression(MemberExpression ctx) {
        // 原来 AST.MemberExpression 竟然是包括方法调用……
        // todo 检查
        ExpressionNode callee = ctx.property;
        while (true) {
            assert callee.symbol instanceof ProcedureSymbol;
            ProcedureSymbol procedure = (ProcedureSymbol) callee.symbol;
            assert !procedure.arguments.isEmpty();
            Symbol firstSymbol = procedure.arguments.get("self");
            if (null == firstSymbol) {
                ProcedureSymbol next = procedure.next;
                if (null == next)
                    Utils.err("Type Check: AST.MemberExpression", "Procedure " + procedure.name + " is not a method");
                else {
                    callee.symbol = next;
                    continue;
                }
            }
            assert null != firstSymbol;
            if (!typeEquals(firstSymbol.type, ctx.object.evalType)) {
                ProcedureSymbol next = procedure.next;
                if (null == next)
                    Utils.err("Type Check: AST.MemberExpression", "Procedure " + procedure.name + " is not a method of " + ctx.object.evalType);
                else {
                    callee.symbol = next;
                    continue;
                }
            }
            break;
        }
        ctx.evalType = ctx.property.evalType;
    }

    @Override
    public void exitIdentifier(Identifier ctx) {
        if (null == ctx.symbol) {
            Utils.err("Type Check: AST.Identifier", "AST.Identifier " + ctx.name + " not found!");
        }
        ctx.evalType = ctx.symbol.type;
    }

//    @Override
//    public void exitLiteral(AST.Literal ctx) {
//        // 是放在这里好还是建立AST时？
//    }

    @Override
    public void exitBinaryExpression(BinaryExpression ctx) {
        if (!typeEquals(ctx.left.evalType, ctx.right.evalType)) {
            Utils.err("Type Check: AST.BinaryExpression", "left & right is not the same type!");
        }
    }

    @Override
    public void exitIndexExpression(IndexExpression ctx) {
        String typeStr = ctx.left.evalType.toString();
        if (typeStr.startsWith("List<")) {
            // 只有List<>可以有[]访问下标
            ctx.evalType = getElementType(typeStr);
        } else {
            Utils.err("Type Check: AST.IndexExpression", "left is not a List Type");
        }
    }

    @Override
    public void exitForBlock(ForBlock ctx) {
        String typeStr = ctx.for_expr.evalType.toString();
        if (!typeStr.startsWith("List<")) {
            Utils.err("Type Check: AST.ForBlock", "for_expr is not a List Type");
        }
        if (!typeEquals(ctx.for_id.evalType, getElementType(typeStr))) {
            Utils.err("Type Check: AST.ForBlock",
                    "can not retrieve " + ctx.for_id.evalType + " from " + typeStr);
        }
    }

//    @Override
//    public void exitAssign(AST.Assign ctx) {
//        if (!typeEquals(ctx.lvalue.evalType, ctx.rvalue.evalType)) {
//            Utils.err("Type Check: AST.Assign", "left & right is not the same type!");
//        }
//    }
}
