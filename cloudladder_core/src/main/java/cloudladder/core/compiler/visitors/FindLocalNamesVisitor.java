package cloudladder.core.compiler.visitors;

import cloudladder.core.compiler.ast.expression.ASTIdentifierExpression;
import cloudladder.core.compiler.ast.statement.ASTDataStatement;
import cloudladder.core.compiler.ast.statement.ASTDataStatementItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FindLocalNamesVisitor extends ASTVisitor {
    private final HashSet<String> names;

    public FindLocalNamesVisitor() {
        this.names = new HashSet<>();
    }

    @Override
    public void visitDataStatement(ASTDataStatement node) {
        for (ASTDataStatementItem item : node.getItems()) {
            String name = item.getName().getText();
            this.names.add(name);
        }
    }

    public HashSet<String> getNames() {
        return this.names;
    }
}
