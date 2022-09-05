package cloudladder.core.compiler.visitors;

import cloudladder.core.compiler.ast.expression.*;
import cloudladder.core.compiler.ast.statement.ASTAssignmentStatement;
import cloudladder.core.compiler.ast.statement.ASTDataStatement;
import cloudladder.core.compiler.ast.statement.ASTDataStatementItem;
import cloudladder.core.compiler.ast.statement.ASTStatement;

public abstract class ASTVisitor {
    public void visitExpression(ASTExpression node) {
        if (node instanceof ASTArrowExpression) {
            this.visitArrowExpression((ASTArrowExpression) node);
        } else if (node instanceof ASTPipeExpression) {
            this.visitPipeExpression((ASTPipeExpression) node);
        } else if (node instanceof ASTFunctionExpression) {
            this.visitFunctionExpression((ASTFunctionExpression) node);
        } else if (node instanceof ASTObjLiteral) {
            this.visitObjectLiteral((ASTObjLiteral) node);
        } else if (node instanceof ASTArrayLiteral) {
            this.visitArrayLiteral((ASTArrayLiteral) node);
        } else if (node instanceof ASTStringLiteral) {
            this.visitStringLiteral((ASTStringLiteral) node);
        } else if (node instanceof ASTNumberLiteral) {
            this.visitNumberLiteral((ASTNumberLiteral) node);
        } else if (node instanceof ASTIndexing) {
            this.visitIndexing((ASTIndexing) node);
        } else if (node instanceof ASTIdentifierExpression) {
            this.visitIdentifierExpression((ASTIdentifierExpression) node);
        } else if (node instanceof ASTFunctionCall) {
            this.visitFunctionCall((ASTFunctionCall) node);
        } else if (node instanceof ASTFieldAccess) {
            this.visitFieldAccess((ASTFieldAccess) node);
        } else if (node instanceof ASTBooleanLiteral) {
            this.visitBooleanLiteral((ASTBooleanLiteral) node);
        } else if (node instanceof ASTUnaryExpression) {
            this.visitUnaryExpression((ASTUnaryExpression) node);
        } else if (node instanceof ASTBinaryExpression) {
            this.visitBinaryExpression((ASTBinaryExpression) node);
        }
    }

    public void visitArrowExpression(ASTArrowExpression node) {
        this.visitExpression(node.getLeft());
        this.visitExpression(node.getRight());
    }

    public void visitPipeExpression(ASTPipeExpression node) {
        this.visitExpression(node.getLeft());
        this.visitExpression(node.getRight());
    }

    public void visitFunctionExpression(ASTFunctionExpression node) {
    }

    public void visitObjectLiteral(ASTObjLiteral node) {
        for (ASTObjItem item : node.getItems()) {
            if (item.isDynamic()) {
                this.visitExpression(item.getDyKey());
                this.visitExpression(item.getValue());
            } else {
                this.visitExpression(item.getValue());
            }
        }
    }

    public void visitArrayLiteral(ASTArrayLiteral node) {
        for (ASTExpression exp : node.getItems()) {
            this.visitExpression(exp);
        }
    }

    public void visitStringLiteral(ASTStringLiteral node) {}

    public void visitNumberLiteral(ASTNumberLiteral node) {}

    public void visitIndexing(ASTIndexing node) {
        this.visitExpression(node.getIndex());
        this.visitExpression(node.getIndexer());
    }

    public void visitIdentifierExpression(ASTIdentifierExpression node) {}

    public void visitFunctionCall(ASTFunctionCall node) {
        this.visitExpression(node.getFunc());
        for (ASTExpression arg : node.getArgs()) {
            this.visitExpression(arg);
        }
    }

    public void visitFieldAccess(ASTFieldAccess node) {
        this.visitExpression(node.getLeft());
    }

    public void visitBooleanLiteral(ASTBooleanLiteral node) {}

    public void visitUnaryExpression(ASTUnaryExpression node) {
        this.visitExpression(node.getExpression());
    }

    public void visitBinaryExpression(ASTBinaryExpression node) {
        this.visitExpression(node.getLeft());
        this.visitExpression(node.getRight());
    }

    public void visitStatement(ASTStatement node) {
        if (node instanceof ASTDataStatement) {
            this.visitDataStatement((ASTDataStatement) node);
        } else if (node instanceof ASTAssignmentStatement) {
            this.visitAssignmentStatement((ASTAssignmentStatement) node);
        }
    }

    public void visitDataStatement(ASTDataStatement node) {
        for (ASTDataStatementItem item : node.getItems()) {
            this.visitExpression(item.getInitializer());
        }
    }

    public void visitAssignmentStatement(ASTAssignmentStatement node) {
        this.visitExpression(node.getRight());
    }
}
