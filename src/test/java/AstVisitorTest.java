import ast.AstVisitor;
import ast.node.*;
import ast.node.flow.*;
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
        private void realVisit(Node node, String name) {
            System.out.println("visiting " + node.toString() + " through " + name);
            if (null == node.children) return;
            for (Node child : node.children) {
                if (null != child)
                    child.accept(this);
            }
        }

        @Override
        public void visit(Apply apply) {
            realVisit(apply, "apply");
        }

        @Override
        public void visit(Assign assign) {
            realVisit(assign, "assign");
        }

        @Override
        public void visit(Block block) {
            realVisit(block, "block");
        }

        @Override
        public void visit(Break node) {
            realVisit(node, "break");
        }

        @Override
        public void visit(Continue node) {
            realVisit(node, "continue");
        }

        @Override
        public void visit(Expression expression) {
            realVisit(expression, "expr");
        }

        @Override
        public void visit(For forloop) {
            realVisit(forloop, "for");
        }

        @Override
        public void visit(Pass node) {
            realVisit(node, "pass");
        }

        @Override
        public void visit(Identifier identifier) {
            realVisit(identifier, "id");
        }

        @Override
        public void visit(IfElse ifelse) {
            realVisit(ifelse, "if-else");
        }

        @Override
        public void visit(ListNode listnode) {
            realVisit(listnode, "list");
        }

        @Override
        public void visit(Literal literal) {
            realVisit(literal, "lit");
        }

        @Override
        public void visit(Param param) {
            realVisit(param, "param");
        }

        @Override
        public void visit(ParamList paramlist) {
            realVisit(paramlist, "paramlist");
        }

        @Override
        public void visit(ProcDef procdef) {
            realVisit(procdef, "procdef");
        }

        @Override
        public void visit(Range range) {
            realVisit(range, "range");
        }

        @Override
        public void visit(Text text) {
            realVisit(text, "text");
        }

        @Override
        public void visit(Type type) {
            realVisit(type, "type");
        }

        @Override
        public void visit(TypeApply typeapply) {
            realVisit(typeapply, "type.apply");
        }

        @Override
        public void visit(TypeName typename) {
            realVisit(typename, "type.name");
        }

        @Override
        public void visit(VarDecl vardecl) {
            realVisit(vardecl, "vardecl");
        }

        @Override
        public void visit(While whileloop) {
            realVisit(whileloop, "while");
        }
    }
}
