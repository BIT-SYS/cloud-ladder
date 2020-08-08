package ast;

import ast.type.Type;
import grammar.CLParserBaseVisitor;
import grammar.CLParserParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

// 返回AST节点的Visitor
public class ASTParser extends CLParserBaseVisitor<Node> {

    private Node visitBlockLike(ParserRuleContext ctx, List<CLParserParser.StatementContext> statements) {
        // Java限制起来传入参数必须要实现statement方法貌似比较麻烦
        Block bl = new Block(ctx);
        statements.forEach(
                (n) -> {
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
    public Node visitTypeType(CLParserParser.TypeTypeContext ctx) {
        return new Type(ctx);
    }

    @Override
    public Node visitProgram(CLParserParser.ProgramContext ctx) {
        return visitBlockLike(ctx, ctx.statement());
    }

    @Override
    public Node visitInt(CLParserParser.IntContext ctx) {
        return super.visitInt(ctx);
    }

    @Override
    public Node visitFloat(CLParserParser.FloatContext ctx) {
        return super.visitFloat(ctx);
    }

    @Override
    public Node visitChar(CLParserParser.CharContext ctx) {
        return super.visitChar(ctx);
    }

    @Override
    public Node visitString(CLParserParser.StringContext ctx) {
        return super.visitString(ctx);
    }

    @Override
    public Node visitBool(CLParserParser.BoolContext ctx) {
        return super.visitBool(ctx);
    }

    @Override
    public Node visitIntegerLiteral(CLParserParser.IntegerLiteralContext ctx) {
        return super.visitIntegerLiteral(ctx);
    }

    @Override
    public Node visitFloatLiteral(CLParserParser.FloatLiteralContext ctx) {
        return super.visitFloatLiteral(ctx);
    }

    @Override
    public Node visitBlock(CLParserParser.BlockContext ctx) {
        return visitBlockLike(ctx, ctx.statement());
    }

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
    public Node visitProcedureDecl(CLParserParser.ProcedureDeclContext ctx) {
        return super.visitProcedureDecl(ctx);
    }

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
    public Node visitAddSub(CLParserParser.AddSubContext ctx) {
        return super.visitAddSub(ctx);
    }

    @Override
    public Node visitPrefix(CLParserParser.PrefixContext ctx) {
        return super.visitPrefix(ctx);
    }

    @Override
    public Node visitIndex(CLParserParser.IndexContext ctx) {
        return super.visitIndex(ctx);
    }

    @Override
    public Node visitProcedure(CLParserParser.ProcedureContext ctx) {
        return super.visitProcedure(ctx);
    }

    @Override
    public Node visitMulDivMod(CLParserParser.MulDivModContext ctx) {
        return super.visitMulDivMod(ctx);
    }

    @Override
    public Node visitPartialEqual(CLParserParser.PartialEqualContext ctx) {
        return super.visitPartialEqual(ctx);
    }

    @Override
    public Node visitLit(CLParserParser.LitContext ctx) {
        return super.visitLit(ctx);
    }

    @Override
    public Node visitMember(CLParserParser.MemberContext ctx) {
        return super.visitMember(ctx);
    }

    @Override
    public Node visitLam(CLParserParser.LamContext ctx) {
        return super.visitLam(ctx);
    }

    @Override
    public Node visitCompare(CLParserParser.CompareContext ctx) {
        return super.visitCompare(ctx);
    }

    @Override
    public Node visitId(CLParserParser.IdContext ctx) {
        return new Identifier(ctx);
    }

    @Override
    public Node visitExp(CLParserParser.ExpContext ctx) {
        return super.visitExp(ctx);
    }

    @Override
    public Node visitLogic(CLParserParser.LogicContext ctx) {
        return super.visitLogic(ctx);
    }

    @Override
    public Node visitValuesListInitializer(CLParserParser.ValuesListInitializerContext ctx) {
        return new ListNode(null);
    }

    @Override
    public Node visitRangeListInitializer(CLParserParser.RangeListInitializerContext ctx) {
        return super.visitRangeListInitializer(ctx);
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

    @Override
    public Node visitProcedureCall(CLParserParser.ProcedureCallContext ctx) {
        return super.visitProcedureCall(ctx);
    }

    @Override
    public Node visitProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {
        return super.visitProcedureDeclaration(ctx);
    }

    @Override
    public Node visitProcedureBody(CLParserParser.ProcedureBodyContext ctx) {
        return super.visitProcedureBody(ctx);
    }

    @Override
    public Node visitParameter(CLParserParser.ParameterContext ctx) {
        return super.visitParameter(ctx);
    }

    @Override
    public Node visitParameterList(CLParserParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }
}
