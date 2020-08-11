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
        @Override
        public void visit(Apply apply) {
            System.out.println("visiting " + apply.toString());
            if (null == apply.children) return;
            for (Node child : apply.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Assign assign) {
            System.out.println("visiting " + assign.toString());
            if (null == assign.children) return;
            for (Node child : assign.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Block block) {
            System.out.println("visiting " + block.toString());
            if (null == block.children) return;
            for (Node child : block.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Expression expression) {
            System.out.println("visiting " + expression.toString());
            if (null == expression.children) return;
            for (Node child : expression.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Identifier identifier) {
            System.out.println("visiting " + identifier.toString());
            if (null == identifier.children) return;
            for (Node child : identifier.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(ListNode listnode) {
            System.out.println("visiting " + listnode.toString());
            if (null == listnode.children) return;
            for (Node child : listnode.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Literal literal) {
            System.out.println("visiting " + literal.toString());
            if (null == literal.children) return;
            for (Node child : literal.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Node node) {
            System.out.println("visiting " + node.toString());
            if (null == node.children) return;
            for (Node child : node.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Param param) {
            System.out.println("visiting " + param.toString());
            if (null == param.children) return;
            for (Node child : param.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(ParamList paramlist) {
            System.out.println("visiting " + paramlist.toString());
            if (null == paramlist.children) return;
            for (Node child : paramlist.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(ProcDef procdef) {
            System.out.println("visiting " + procdef.toString());
            if (null == procdef.children) return;
            for (Node child : procdef.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Range range) {
            System.out.println("visiting " + range.toString());
            if (null == range.children) return;
            for (Node child : range.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(Type type) {
            System.out.println("visiting " + type.toString());
            if (null == type.children) return;
            for (Node child : type.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(TypeApply typeapply) {
            System.out.println("visiting " + typeapply.toString());
            if (null == typeapply.children) return;
            for (Node child : typeapply.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(TypeName typename) {
            System.out.println("visiting " + typename.toString());
            if (null == typename.children) return;
            for (Node child : typename.children) {
                if (null != child) visit(child);
            }
        }

        @Override
        public void visit(VarDecl vardecl) {
            System.out.println("visiting " + vardecl.toString());
            if (null == vardecl.children) return;
            for (Node child : vardecl.children) {
                if (null != child) visit(child);
            }
        }
    }
}
