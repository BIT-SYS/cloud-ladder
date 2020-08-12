import ast.AstVisitor;
import ast.node.*;
import ast.node.type.Type;
import ast.node.type.TypeApply;
import ast.node.type.TypeName;

import java.io.IOException;

public class AstVisitorTest extends AstTester {
    @Override
    public void start(String file_name) throws IOException {
        super.start(file_name);
        TestAstVisitor visitor = new TestAstVisitor();
        nodeRoot.accept(visitor);
    }

    static class TestAstVisitor implements AstVisitor {
        private void realVisit(Node node) {
            System.out.println("visiting " + node.toString());
            if (null == node.children) return;
            for (Node child : node.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Apply apply) {
            realVisit(apply);
        }

        @Override
        public void visit(Assign assign) {
            realVisit(assign);
        }

        @Override
        public void visit(Block block) {
            realVisit(block);
        }

        @Override
        public void visit(Expression expression) {
            realVisit(expression);
        }

        @Override
        public void visit(ForLoop forloop) {
            realVisit(forloop);
        }

        @Override
        public void visit(Identifier identifier) {
            realVisit(identifier);
        }

        @Override
        public void visit(IfElse ifelse) {
            realVisit(ifelse);
        }

        @Override
        public void visit(ListNode listnode) {
            realVisit(listnode);
        }

        @Override
        public void visit(Literal literal) {
            realVisit(literal);
        }

        @Override
        public void visit(Node node) {
            realVisit(node);
        }

        @Override
        public void visit(Param param) {
            realVisit(param);
        }

        @Override
        public void visit(ParamList paramlist) {
            realVisit(paramlist);
        }

        @Override
        public void visit(ProcDef procdef) {
            realVisit(procdef);
        }

        @Override
        public void visit(Range range) {
            realVisit(range);
        }

        @Override
        public void visit(Text text) {
            realVisit(text);
        }

        @Override
        public void visit(Type type) {
            realVisit(type);
        }

        @Override
        public void visit(TypeApply typeapply) {
            realVisit(typeapply);
        }

        @Override
        public void visit(TypeName typename) {
            realVisit(typename);
        }

        @Override
        public void visit(VarDecl vardecl) {
            realVisit(vardecl);
        }

        @Override
        public void visit(WhileLoop whileloop) {
            realVisit(whileloop);
        }
    }
}
