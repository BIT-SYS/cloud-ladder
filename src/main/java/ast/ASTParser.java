package ast;

import ast.node.*;
import ast.node.type.TypeApply;
import ast.node.type.TypeName;
import grammar.CLParserBaseVisitor;
import grammar.CLParserParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 返回AST节点的Visitor
public class ASTParser extends CLParserBaseVisitor<Node> {

    // proc start
    @Override
    public Node visitProcedureDecl(CLParserParser.ProcedureDeclContext ctx) {
        return visit(ctx.procedureDeclaration());
    }

    @Override
    public Node visitProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {
        return new ProcDef(ctx) {{
            addChild(visit(ctx.typeType())); // type
            addChild(new Identifier(ctx.IDENTIFIER().getText())); // id
            addChild(visit(ctx.parameterList())); // params
            addChild(visit(ctx.procedureBody().block())); // body
        }};
    }

    @Override
    public Node visitParameter(CLParserParser.ParameterContext ctx) {
        return new Param(ctx) {{
            addChild(visit(ctx.typeType()));
            addChild(new Identifier(ctx.IDENTIFIER().getText()));
        }};
    }

    @Override
    public Node visitParameterList(CLParserParser.ParameterListContext ctx) {
        ParamList paramList = new ParamList(ctx);
        ctx.parameter().stream().map(this::visit).forEach(paramList::addChild);
        return paramList;
    }
    // proc end

    // block start
    private Node visitBlockLike(ParserRuleContext ctx, List<CLParserParser.StatementContext> statements) {
        // Java限制起来传入参数必须要实现statement方法貌似比较麻烦
        Block bl = new Block(ctx);
        statements.forEach(
                n -> {
                    if (n == null)
                        return;
                    Node v = visit(n);
                    if (v != null)
                        bl.addChild(v);
                }
        );
        return bl;
    }

    @Override
    public Node visitProgram(CLParserParser.ProgramContext ctx) {
        return visitBlockLike(ctx, ctx.statement());
    }

    @Override
    public Node visitBlock(CLParserParser.BlockContext ctx) {
        return visitBlockLike(ctx, ctx.statement());
    }
    // block end

    // type start
    @Override
    public Node visitTypeType(CLParserParser.TypeTypeContext ctx) {
        return super.visitTypeType(ctx); // basic type or composite type
    }

    @Override
    public Node visitBasicType(CLParserParser.BasicTypeContext ctx) {
        return new TypeName(ctx);
    }

    @Override
    public Node visitCompositeType(CLParserParser.CompositeTypeContext ctx) {
        TypeApply ta = new TypeApply(ctx);
        ctx.typeType().stream().map(this::visit).forEach(ta::addChild);
        return ta;
    }
    // type end

    // var start
    @Override
    public Node visitVariableDecl(CLParserParser.VariableDeclContext ctx) {
        return new VarDecl(ctx) {{
            addChild(visit(ctx.typeType())); // type
            addChild(new Identifier(ctx.IDENTIFIER().getText())); // id
            addChild(visit(ctx.expression())); // expr
        }};
    }

    @Override
    public Node visitAssign(CLParserParser.AssignContext ctx) {
        return new Assign(ctx) {{
            addChild(visit(actx.children.get(0))); // VisitId
            addChild(visit(actx.children.get(1))); // VisitExpr
        }};
    }
    // var end

    // sugar start
    @Override
    public Node visitValuesListInitializer(CLParserParser.ValuesListInitializerContext ctx) {
        ListNode list = new ListNode(null);
        ctx.expression().stream().map(this::visit).forEach(list::addChild);
        return list;
    }

    @Override
    public Node visitRangeListInitializer(CLParserParser.RangeListInitializerContext ctx) {
        return super.visitRangeListInitializer(ctx);
    }
    // sugar end

    // literal start
    private Node visitLiteral(ParserRuleContext ctx, String type) {
        Literal lit = new Literal(ctx);
        TypeName typeName = new TypeName(type);
        lit.addChild(typeName);
        lit.text = ctx.getText();
        return lit;
    }

    @Override
    public Node visitIntegerLiteral(CLParserParser.IntegerLiteralContext ctx) {
        return visitLiteral(ctx, "Number");
    }

    @Override
    public Node visitFloatLiteral(CLParserParser.FloatLiteralContext ctx) {
        return visitLiteral(ctx, "Number");
    }

    @Override
    public Node visitString(CLParserParser.StringContext ctx) {
        return visitLiteral(ctx, "String");

    }

    @Override
    public Node visitBool(CLParserParser.BoolContext ctx) {
        return visitLiteral(ctx, "Bool");
    }
    // literal end

    // bop start
    private Node visitBOp(ParserRuleContext ctx, String op, int l, int r) {
        Apply bop = new Apply(ctx);
        bop.addChild(new Identifier(op));
        bop.addChild(visit(ctx.children.get(l)));
        bop.addChild(visit(ctx.children.get(r)));
        return bop;
    }

    @Override
    public Node visitMulDivMod(CLParserParser.MulDivModContext ctx) {
        return visitBOp(ctx, ctx.bop.getText(), 0, 2);
    }

    @Override
    public Node visitAddSub(CLParserParser.AddSubContext ctx) {
        return visitBOp(ctx, ctx.bop.getText(), 0, 2);
    }

    @Override
    public Node visitExp(CLParserParser.ExpContext ctx) {
        return visitBOp(ctx, ctx.bop.getText(), 0, 2);
    }

    @Override
    public Node visitPartialEqual(CLParserParser.PartialEqualContext ctx) {
        return visitBOp(ctx, ctx.bop.getText(), 0, 2);
    }

    @Override
    public Node visitCompare(CLParserParser.CompareContext ctx) {
        return visitBOp(ctx, ctx.bop.getText(), 0, 2);
    }

    @Override
    public Node visitLogic(CLParserParser.LogicContext ctx) {
        return visitBOp(ctx, ctx.bop.getText(), 0, 2);
    }

    @Override
    public Node visitIndex(CLParserParser.IndexContext ctx) {
        return visitBOp(ctx, "!!", 0, 2);
    }
    // bop end

    // call start
    private Node visitProcCall(ParserRuleContext ctx, String id, List<Node> args) {
        Apply call = new Apply(ctx);
        call.addChild(new Identifier(id));
        for (Node arg : args) {
            call.addChild(arg);
        }
        return call;
    }

    @Override
    public Node visitProcedureCall(CLParserParser.ProcedureCallContext ctx) {
        List<Node> args;
        if (ctx.expressionList() == null) {
            args = new ArrayList<>();
        } else {
            args = ctx.expressionList().expression().stream().map(this::visit).collect(Collectors.toList());
        }
        return visitProcCall(ctx, ctx.IDENTIFIER().getText(), args);
    }

    @Override
    public Node visitMethod(CLParserParser.MethodContext ctx) {
        List<Node> args = new ArrayList<>();
        args.add(visit(ctx.expression()));
        if (ctx.procedureCall().expressionList() != null) {
            ctx.procedureCall().expressionList().expression().stream().map(this::visit).forEach(args::add);
        }
        return visitProcCall(ctx, ctx.procedureCall().IDENTIFIER().getText(), args);
    }
    // call end

    @Override
    public Node visitIfBlock(CLParserParser.IfBlockContext ctx) {
        return super.visitIfBlock(ctx);
    }

    @Override
    public Node visitForBlock(CLParserParser.ForBlockContext ctx) {
        return super.visitForBlock(ctx);
    }

    @Override
    public Node visitWhileBlock(CLParserParser.WhileBlockContext ctx) {
        return super.visitWhileBlock(ctx);
    }

    @Override
    public Node visitBreak(CLParserParser.BreakContext ctx) {
        return super.visitBreak(ctx);
    }

    @Override
    public Node visitContinue(CLParserParser.ContinueContext ctx) {
        return super.visitContinue(ctx);
    }

    @Override
    public Node visitExpr(CLParserParser.ExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Node visitEmpty(CLParserParser.EmptyContext ctx) {
        return super.visitEmpty(ctx);
    }

    @Override
    public Node visitAssignment(CLParserParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public Node visitEmptyLines(CLParserParser.EmptyLinesContext ctx) {
        return super.visitEmptyLines(ctx);
    }

    @Override
    public Node visitParens(CLParserParser.ParensContext ctx) {
        return super.visitParens(ctx);
    }

    @Override
    public Node visitPrefix(CLParserParser.PrefixContext ctx) {
        return super.visitPrefix(ctx);
    }

    @Override
    public Node visitLam(CLParserParser.LamContext ctx) {
        return super.visitLam(ctx);
    }

    @Override
    public Node visitId(CLParserParser.IdContext ctx) {
        return new Identifier(ctx.getText());
    }

    @Override
    public Node visitExpressionList(CLParserParser.ExpressionListContext ctx) {
        return super.visitExpressionList(ctx);
    }

    @Override
    public Node visitLambdaParameter(CLParserParser.LambdaParameterContext ctx) {
        return super.visitLambdaParameter(ctx);
    }

    @Override
    public Node visitLambdaParameterList(CLParserParser.LambdaParameterListContext ctx) {
        return super.visitLambdaParameterList(ctx);
    }

    @Override
    public Node visitLambda(CLParserParser.LambdaContext ctx) {
        return super.visitLambda(ctx);
    }
}
