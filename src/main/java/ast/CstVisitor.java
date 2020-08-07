package ast;

import grammar.CLParserBaseVisitor;
import grammar.CLParserParser;

// 返回AST节点的Visitor
public class CstVisitor extends CLParserBaseVisitor<AST> {
    @Override
    public AST visitProgram(CLParserParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    @Override
    public AST visitBasicType(CLParserParser.BasicTypeContext ctx) {
        return super.visitBasicType(ctx);
    }

    @Override
    public AST visitTypeType(CLParserParser.TypeTypeContext ctx) {
        return super.visitTypeType(ctx);
    }

    @Override
    public AST visitInt(CLParserParser.IntContext ctx) {
        return super.visitInt(ctx);
    }

    @Override
    public AST visitFloat(CLParserParser.FloatContext ctx) {
        return super.visitFloat(ctx);
    }

    @Override
    public AST visitChar(CLParserParser.CharContext ctx) {
        return super.visitChar(ctx);
    }

    @Override
    public AST visitString(CLParserParser.StringContext ctx) {
        return super.visitString(ctx);
    }

    @Override
    public AST visitBool(CLParserParser.BoolContext ctx) {
        return super.visitBool(ctx);
    }

    @Override
    public AST visitIntegerLiteral(CLParserParser.IntegerLiteralContext ctx) {
        return super.visitIntegerLiteral(ctx);
    }

    @Override
    public AST visitFloatLiteral(CLParserParser.FloatLiteralContext ctx) {
        return super.visitFloatLiteral(ctx);
    }

    @Override
    public AST visitBlock(CLParserParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public AST visitIfBlock(CLParserParser.IfBlockContext ctx) {
        return super.visitIfBlock(ctx);
    }

    @Override
    public AST visitForBlock(CLParserParser.ForBlockContext ctx) {
        return super.visitForBlock(ctx);
    }

    @Override
    public AST visitWhileBlock(CLParserParser.WhileBlockContext ctx) {
        return super.visitWhileBlock(ctx);
    }

    @Override
    public AST visitBreak(CLParserParser.BreakContext ctx) {
        return super.visitBreak(ctx);
    }

    @Override
    public AST visitContinue(CLParserParser.ContinueContext ctx) {
        return super.visitContinue(ctx);
    }

    @Override
    public AST visitProcedureDecl(CLParserParser.ProcedureDeclContext ctx) {
        return super.visitProcedureDecl(ctx);
    }

    @Override
    public AST visitVariableDecl(CLParserParser.VariableDeclContext ctx) {
        return super.visitVariableDecl(ctx);
    }

    @Override
    public AST visitAssign(CLParserParser.AssignContext ctx) {
        return super.visitAssign(ctx);
    }

    @Override
    public AST visitExpr(CLParserParser.ExprContext ctx) {
        return super.visitExpr(ctx);
    }

    @Override
    public AST visitEmpty(CLParserParser.EmptyContext ctx) {
        return super.visitEmpty(ctx);
    }

    @Override
    public AST visitAssignment(CLParserParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public AST visitEmptyLines(CLParserParser.EmptyLinesContext ctx) {
        return super.visitEmptyLines(ctx);
    }

    @Override
    public AST visitParens(CLParserParser.ParensContext ctx) {
        return super.visitParens(ctx);
    }

    @Override
    public AST visitAddSub(CLParserParser.AddSubContext ctx) {
        return super.visitAddSub(ctx);
    }

    @Override
    public AST visitPrefix(CLParserParser.PrefixContext ctx) {
        return super.visitPrefix(ctx);
    }

    @Override
    public AST visitIndex(CLParserParser.IndexContext ctx) {
        return super.visitIndex(ctx);
    }

    @Override
    public AST visitProcedure(CLParserParser.ProcedureContext ctx) {
        return super.visitProcedure(ctx);
    }

    @Override
    public AST visitMulDivMod(CLParserParser.MulDivModContext ctx) {
        return super.visitMulDivMod(ctx);
    }

    @Override
    public AST visitPartialEqual(CLParserParser.PartialEqualContext ctx) {
        return super.visitPartialEqual(ctx);
    }

    @Override
    public AST visitLit(CLParserParser.LitContext ctx) {
        return super.visitLit(ctx);
    }

    @Override
    public AST visitMember(CLParserParser.MemberContext ctx) {
        return super.visitMember(ctx);
    }

    @Override
    public AST visitLam(CLParserParser.LamContext ctx) {
        return super.visitLam(ctx);
    }

    @Override
    public AST visitCompare(CLParserParser.CompareContext ctx) {
        return super.visitCompare(ctx);
    }

    @Override
    public AST visitId(CLParserParser.IdContext ctx) {
        return super.visitId(ctx);
    }

    @Override
    public AST visitExp(CLParserParser.ExpContext ctx) {
        return super.visitExp(ctx);
    }

    @Override
    public AST visitLogic(CLParserParser.LogicContext ctx) {
        return super.visitLogic(ctx);
    }

    @Override
    public AST visitListInit(CLParserParser.ListInitContext ctx) {
        return super.visitListInit(ctx);
    }

    @Override
    public AST visitValuesListInitializer(CLParserParser.ValuesListInitializerContext ctx) {
        return super.visitValuesListInitializer(ctx);
    }

    @Override
    public AST visitRangeListInitializer(CLParserParser.RangeListInitializerContext ctx) {
        return super.visitRangeListInitializer(ctx);
    }

    @Override
    public AST visitExpressionList(CLParserParser.ExpressionListContext ctx) {
        return super.visitExpressionList(ctx);
    }

    @Override
    public AST visitLambdaParameter(CLParserParser.LambdaParameterContext ctx) {
        return super.visitLambdaParameter(ctx);
    }

    @Override
    public AST visitLambdaParameterList(CLParserParser.LambdaParameterListContext ctx) {
        return super.visitLambdaParameterList(ctx);
    }

    @Override
    public AST visitLambda(CLParserParser.LambdaContext ctx) {
        return super.visitLambda(ctx);
    }

    @Override
    public AST visitProcedureCall(CLParserParser.ProcedureCallContext ctx) {
        return super.visitProcedureCall(ctx);
    }

    @Override
    public AST visitProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {
        return super.visitProcedureDeclaration(ctx);
    }

    @Override
    public AST visitProcedureBody(CLParserParser.ProcedureBodyContext ctx) {
        return super.visitProcedureBody(ctx);
    }

    @Override
    public AST visitParameter(CLParserParser.ParameterContext ctx) {
        return super.visitParameter(ctx);
    }

    @Override
    public AST visitParameterList(CLParserParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }
}
