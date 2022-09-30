package cloudladder.core.compiler;

import cloudladder.core.compiler.ast.AST;
import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.*;
import cloudladder.core.compiler.ast.program.*;
import cloudladder.core.compiler.ast.statement.*;
import grammar.CLParser;
import grammar.CLParserBaseVisitor;

import java.util.ArrayList;

public class AntlrVisitor extends CLParserBaseVisitor<AST> {
    @Override
    public AST visitArrayLiteral(CLParser.ArrayLiteralContext ctx) {
        ArrayList<ASTExpression> exps = new ArrayList<>();
        for (int i = 0; i < ctx.exp.size(); i++) {
            exps.add((ASTExpression) visit(ctx.exp.get(i)));
        }

        return new ASTArrayLiteral(exps);
    }

//    @Override
//    public AST visitExpPipe(CLParser.ExpPipeContext ctx) {
//        ASTExpression left = (ASTExpression) visit(ctx.expression(0));
//        ASTExpression right = (ASTExpression) visit(ctx.expression(1));
//
//        return new ASTPipeExpression(left, right);
//    }
//
//    @Override
//    public AST visitExpArrow(CLParser.ExpArrowContext ctx) {
//        ASTExpression left = (ASTExpression) visit(ctx.expression(0));
//        ASTExpression right = (ASTExpression) visit(ctx.expression(1));
//
//        return new ASTArrowExpression(left, right);
//    }

    @Override
    public AST visitExpPipeAndArrow(CLParser.ExpPipeAndArrowContext ctx) {
        ASTExpression left = (ASTExpression) visit(ctx.expression(0));
        ASTExpression right = (ASTExpression) visit(ctx.expression(1));

        String op = ctx.op.getText();
        if (op.equals("->")) {
            return new ASTArrowExpression(left, right);
        } else if (op.equals("|")) {
            return new ASTPipeExpression(left, right);
        }
        return null;
    }

    @Override
    public AST visitObjLiteral(CLParser.ObjLiteralContext ctx) {
        ArrayList<ASTObjItem> items = new ArrayList<>();
        for (int i = 0; i < ctx.item.size(); i++) {
            items.add((ASTObjItem) visit(ctx.item.get(i)));
        }

        return new ASTObjLiteral(items);
    }

    @Override
    public AST visitStaticObjItem(CLParser.StaticObjItemContext ctx) {
        ASTToken name = new ASTToken(ctx.Identifier().getSymbol());

        if (ctx.expression() == null) {
            return new ASTObjItem(false, true, false, name, null, null);
        }
        ASTExpression value = (ASTExpression) visit(ctx.expression());

        return new ASTObjItem(false, false, false, name, null, value);
    }

    @Override
    public AST visitDynamicObjItem(CLParser.DynamicObjItemContext ctx) {
        ASTExpression key = (ASTExpression) visit(ctx.expression(0));
        ASTExpression value = (ASTExpression) visit(ctx.expression(1));

        return new ASTObjItem(true, false, false, null, key, value);
    }

    @Override
    public AST visitStringObjItem(CLParser.StringObjItemContext ctx) {
        ASTToken name = new ASTToken(ctx.StringLiteral().getSymbol());

        ASTExpression value = (ASTExpression) visit(ctx.expression());

        return new ASTObjItem(false, false, true, name, null, value);
    }

    @Override
    public AST visitExpUnaryExpression(CLParser.ExpUnaryExpressionContext ctx) {
        ASTToken op = new ASTToken(ctx.op);
        ASTExpression expression = (ASTExpression) visit(ctx.expression());

        return new ASTUnaryExpression(op, expression);
    }

    @Override
    public AST visitExpBinaryExpression(CLParser.ExpBinaryExpressionContext ctx) {
        ASTExpression left = (ASTExpression) visit(ctx.expression(0));
        ASTExpression right = (ASTExpression) visit(ctx.expression(1));
        ASTToken op = new ASTToken(ctx.op);

        return new ASTBinaryExpression(op, left, right);
    }

    @Override
    public AST visitExpFieldAccess(CLParser.ExpFieldAccessContext ctx) {
        ASTExpression left = (ASTExpression) visit(ctx.expression());
        ASTToken right = new ASTToken(ctx.Identifier().getSymbol());

        return new ASTFieldAccess(left, right);
    }

    @Override
    public AST visitExpFunctionCall(CLParser.ExpFunctionCallContext ctx) {
        ASTExpression func = (ASTExpression) visit(ctx.expression());

        ArrayList<ASTExpression> args = new ArrayList<>();
        if (ctx.argumentList() != null) {
            for (int i = 0; i < ctx.argumentList().exp.size(); i++) {
                args.add((ASTExpression) visit(ctx.argumentList().exp.get(i)));
            }
        }

        return new ASTFunctionCall(args, func);
    }

    @Override
    public AST visitExpArrayAccess(CLParser.ExpArrayAccessContext ctx) {
        ASTExpression indexer = (ASTExpression) visit(ctx.expression(0));
        ASTExpression index = (ASTExpression) visit(ctx.expression(1));

        return new ASTIndexing(indexer, index);
    }

    @Override
    public AST visitExpFunctionExpression(CLParser.ExpFunctionExpressionContext ctx) {
        ASTCompoundStatement body = (ASTCompoundStatement) visit(ctx.compoundStatement());
        ArrayList<ASTToken> params = new ArrayList<>();
        if (ctx.paramList() != null) {
            for (int i = 0; i < ctx.paramList().item.size(); i++) {
                params.add(new ASTToken(ctx.paramList().item.get(i)));
            }
        }

        return new ASTFunctionExpression(params, body);
    }

    @Override
    public AST visitPeIdentifier(CLParser.PeIdentifierContext ctx) {
        ASTToken token = new ASTToken(ctx.Identifier().getSymbol());

        return new ASTIdentifierExpression(token);
    }

    @Override
    public AST visitPeParenExpression(CLParser.PeParenExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public AST visitPeLiteral(CLParser.PeLiteralContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public AST visitPeNumberLiteral(CLParser.PeNumberLiteralContext ctx) {
        ASTToken token = new ASTToken(ctx.NumberLiteral().getSymbol());

        return new ASTNumberLiteral(token);
    }

    @Override
    public AST visitPeBoolLiteral(CLParser.PeBoolLiteralContext ctx) {
        ASTToken token = new ASTToken(ctx.BoolLiteral().getSymbol());

        return new ASTBooleanLiteral(token);
    }

    @Override
    public AST visitPeStringLiteral(CLParser.PeStringLiteralContext ctx) {
        ASTToken token = new ASTToken(ctx.StringLiteral().getSymbol());

        return new ASTStringLiteral(token);
    }

    @Override
    public AST visitSelectionStatement(CLParser.SelectionStatementContext ctx) {
        int line = ctx.If().getSymbol().getLine();
        ASTExpression condition = (ASTExpression) visit(ctx.expression());
        ASTStatement statementTrue = (ASTStatement) visit(ctx.statement(0));

        ASTStatement statementFalse = null;
        if (ctx.statement().size() == 2) {
            statementFalse = (ASTStatement) visit(ctx.statement(1));
        }

        return new ASTSelectionStatement(condition, statementTrue, statementFalse, line);
    }

    @Override
    public AST visitAssIdentifier(CLParser.AssIdentifierContext ctx) {
        return new ASTIdentifierExpression(new ASTToken(ctx.Identifier().getSymbol()));
    }

    @Override
    public AST visitAssignArray1(CLParser.AssignArray1Context ctx) {
        ASTExpression indexer = (ASTExpression) visit(ctx.primaryExpression());
        ASTExpression index = (ASTExpression) visit(ctx.expression());

        return new ASTIndexing(indexer, index);
    }

    @Override
    public AST visitAssignArray2(CLParser.AssignArray2Context ctx) {
        ASTExpression indexer = (ASTExpression) visit(ctx.leftHandSideItem());
        ASTExpression index = (ASTExpression) visit(ctx.expression());

        return new ASTIndexing(indexer, index);
    }

    @Override
    public AST visitAssignField1(CLParser.AssignField1Context ctx) {
        ASTExpression exp = (ASTExpression) visit(ctx.primaryExpression());
        ASTToken identifier = new ASTToken(ctx.Identifier().getSymbol());

        return new ASTFieldAccess(exp, identifier);
    }

    @Override
    public AST visitAssignField2(CLParser.AssignField2Context ctx) {
        ASTExpression exp = (ASTExpression) visit(ctx.leftHandSideItem());
        ASTToken identifier = new ASTToken(ctx.Identifier().getSymbol());

        return new ASTFieldAccess(exp, identifier);
    }

    @Override
    public AST visitStatement(CLParser.StatementContext ctx) {
        int n = ctx.getChildCount();
        for (int i = 0; i < n; i++) {
            AST result = visit(ctx.getChild(i));
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    @Override
    public AST visitExpressionStatement(CLParser.ExpressionStatementContext ctx) {
        ASTExpression exp = (ASTExpression) visit(ctx.expression());
        return new ASTExpressionStatement(exp);
    }

    @Override
    public AST visitAssignmentStatement(CLParser.AssignmentStatementContext ctx) {
        ASTExpression left = (ASTExpression) visit(ctx.leftHandSideItem());
        ASTExpression right = (ASTExpression) visit(ctx.expression());
        ASTToken op = new ASTToken(ctx.assignmentOperator().op);

        return new ASTAssignmentStatement(left, right, op);
    }

    @Override
    public AST visitDataStatement(CLParser.DataStatementContext ctx) {
        ArrayList<ASTDataStatementItem> items = new ArrayList<>();
        for (int i = 0; i < ctx.dataStatementItemList().item.size(); i++) {
            items.add((ASTDataStatementItem) visit(ctx.dataStatementItemList().item.get(i)));
        }

        return new ASTDataStatement(items);
    }

    @Override
    public AST visitDataStatementItem(CLParser.DataStatementItemContext ctx) {
        ASTToken name = new ASTToken(ctx.Identifier().getSymbol());
        ASTExpression expression = null;
        if (ctx.expression() != null) {
            expression = (ASTExpression) visit(ctx.expression());
        }

        return new ASTDataStatementItem(name, expression);
    }

    @Override
    public AST visitCompoundStatement(CLParser.CompoundStatementContext ctx) {
        ArrayList<ASTStatement> items = new ArrayList<>();
        for (int i = 0; i < ctx.item.size(); i++) {
            ASTStatement stat = (ASTStatement) visit(ctx.item.get(i));
            items.add(stat);
        }

        return new ASTCompoundStatement(items);
    }

    @Override
    public AST visitBreakStatement(CLParser.BreakStatementContext ctx) {
        ASTToken label = null;
        if (ctx.Identifier() != null) {
            label = new ASTToken(ctx.Identifier().getSymbol());
        }
        return new ASTBreakStatement(label);
    }

    @Override
    public AST visitContinueStatement(CLParser.ContinueStatementContext ctx) {
        ASTToken label = null;
        if (ctx.Identifier() != null) {
            label = new ASTToken(ctx.Identifier().getSymbol());
        }
        return new ASTContinueStatement(label);
    }

    @Override
    public AST visitWhileStatement(CLParser.WhileStatementContext ctx) {
        ASTToken label = null;
        if (ctx.Identifier() != null) {
            label = new ASTToken(ctx.Identifier().getSymbol());
        }
        ASTExpression expression = (ASTExpression) visit(ctx.expression());
        ASTStatement statement = (ASTStatement) visit(ctx.statement());

        return new ASTWhileStatement(expression, statement, label);
    }

    @Override
    public AST visitReturnStatement(CLParser.ReturnStatementContext ctx) {
        ASTExpression expression = (ASTExpression) visit(ctx.expression());
        return new ASTReturnStatement(expression);
    }

    @Override
    public AST visitForStatement(CLParser.ForStatementContext ctx) {
        ASTToken label = null;
        if (ctx.Identifier() != null) {
            label = new ASTToken(ctx.Identifier().getSymbol());
        }
        ASTStatement init = (ASTStatement) visit(ctx.assignmentStatement(0));
        ASTExpression cond = (ASTExpression) visit(ctx.expression());
        ASTStatement step = (ASTStatement) visit(ctx.assignmentStatement(1));
        ASTStatement content = (ASTStatement) visit(ctx.statement());

        return new ASTForStatement(init, cond, step, content, label);
    }

    @Override
    public AST visitForDeclStatement(CLParser.ForDeclStatementContext ctx) {
        ASTToken label = null;
        if (ctx.Identifier() != null) {
            label = new ASTToken(ctx.Identifier().getSymbol());
        }
        ASTDataStatement init = (ASTDataStatement) visit(ctx.dataStatement());
        ASTExpression cond = (ASTExpression) visit(ctx.expression());
        ASTStatement step = (ASTStatement) visit(ctx.assignmentStatement());
        ASTStatement content = (ASTStatement) visit(ctx.statement());

        return new ASTForDeclStatement(init, cond, step, content, label);
    }

    @Override
    public AST visitFunctionDefinition(CLParser.FunctionDefinitionContext ctx) {
        ASTToken name = new ASTToken(ctx.Identifier().getSymbol());

        ArrayList<ASTToken> params = new ArrayList<>();
        if (ctx.paramList() != null) {
            for (int i = 0; i < ctx.paramList().item.size(); i++) {
                params.add(new ASTToken(ctx.paramList().item.get(i)));
            }
        }

        ASTCompoundStatement body = (ASTCompoundStatement) visit(ctx.compoundStatement());

        return new ASTFunctionDefinition(name, params, body);
    }

    @Override
    public AST visitProgramItem(CLParser.ProgramItemContext ctx) {
        if (ctx.functionDefinition() != null) {
            CLParser.FunctionDefinitionContext c = ctx.functionDefinition();
            ASTToken name = new ASTToken(c.Identifier().getSymbol());
            ASTCompoundStatement body = (ASTCompoundStatement) visit(c.compoundStatement());
            ArrayList<ASTToken> params = new ArrayList<>();
            if (c.paramList() != null) {
                for (int i = 0; i < c.paramList().item.size(); i++) {
                    params.add(new ASTToken(c.paramList().item.get(i)));
                }
            }

            return new ASTFunctionDefinition(name, params, body);
        } else if (ctx.statement() != null) {
            ASTStatement stat = (ASTStatement) visit(ctx.statement());
            return new ASTRootStatement(stat);
        } else if (ctx.importStatement() != null) {
            return visitImportStatement(ctx.importStatement());
        } else if (ctx.exportStatement() != null) {
            return visit(ctx.exportStatement());
        } else if (ctx.usingStatement() != null) {
            return visit(ctx.usingStatement());
        }

        return null;
    }

    @Override
    public AST visitUsingStatement(CLParser.UsingStatementContext ctx) {
        String name = ctx.name.getText();
        String scope = ctx.scope != null ? ctx.scope.getText() : null;
        String path = ctx.path.getText();
        path = path.substring(1, path.length() - 1);
        String alias = ctx.as != null ? ctx.as.getText() : null;

        return new ASTUsingStatement(scope, name, path, alias);
    }

    @Override
    public AST visitImportStatement(CLParser.ImportStatementContext ctx) {
        ASTToken path = new ASTToken(ctx.path);
        ASTToken as = null;
        if (ctx.as != null) {
            as = new ASTToken(ctx.as);
        }

        return new ASTImport(as, path);
    }

    @Override
    public AST visitExportStatement(CLParser.ExportStatementContext ctx) {
        ASTToken name = new ASTToken(ctx.Identifier().getSymbol());

        ASTExpression expression = (ASTExpression) visit(ctx.expression());

        return new ASTExport(name, expression);
    }

    @Override
    public AST visitProgram(CLParser.ProgramContext ctx) {
        ArrayList<ASTProgramUnit> items = new ArrayList<>();
        for (int i = 0; i < ctx.item.size(); i++) {
            ASTProgramUnit unit = (ASTProgramUnit) visit(ctx.item.get(i));
            items.add(unit);
        }

        return new ASTProgram(items);
    }
}
