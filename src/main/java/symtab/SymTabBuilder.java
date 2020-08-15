package symtab;

import ast.AstVisitor;
import ast.node.*;
import ast.node.flow.*;
import ast.node.type.Type;
import ast.node.type.TypeApply;
import ast.node.type.TypeName;

import java.util.stream.Collectors;

public class SymTabBuilder implements AstVisitor {
    Scope currentScope = new BaseScope(null, "global"); // TODO predefined
    LoopWatcher loop = new LoopWatcher();
    int blockCounter = 0;
    int whileCounter = 0;

    private void visitChildren(Node node) {
        node.children.forEach(this::visit);
    }

    @Override
    public void visit(Block node) {
        currentScope = new BaseScope(currentScope, "local" + blockCounter++);

        visitChildren(node);

        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void visit(VarDecl node) {
//        (var-decl Number i
//            (lit Number 1))
        Type type = (Type) node.children.get(0);
        String name = node.children.get(1).toString();
        VarSymbol var = new VarSymbol(name, type, currentScope);
        currentScope.define(var);
    }

    @Override
    public void visit(ProcDef node) {
//        (proc-def Number foot
//            (params
//                (param String self))
//            (block
//                (lit Number 42)))
        Type retType = (Type) node.children.get(0);
        String paramTypes = (node.children.get(2)).children
                .stream().map(p -> p.children.get(0).toStringTree()).collect(Collectors.joining());
        currentScope = new ProcSymbol(node.children.get(1).toString() + paramTypes,
                retType, currentScope);

        visitChildren(node.children.get(3));

        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void visit(While node) {
//        (while
//            (lit Bool true)
//            (block
//                (assign a
//                    (apply + a i))))
        loop.push();
        currentScope = new BaseScope(currentScope, "while" + whileCounter++);

        visitChildren(node.children.get(1));

        loop.pop();
    }

    @Override
    public void visit(For node) {
//        (for cat string_list
//            (block
//                (var-decl Boolean is_the_same
//                        (apply ==
//                                (apply foot cat) how_many_foot))))
        loop.push();
        // TODO define new var, zip or merge?
        visitChildren(node.children.get(2));

        loop.pop();
    }

    @Override
    public void visit(Apply node) {

    }

    @Override
    public void visit(Assign node) {

    }


    @Override
    public void visit(Expression node) {

    }

    @Override
    public void visit(Break node) {
        loop.addBreak();
    }

    @Override
    public void visit(Continue node) {
        loop.addContinue();
    }

    @Override
    public void visit(Pass node) {

    }

    @Override
    public void visit(Identifier node) {

    }

    @Override
    public void visit(IfElse node) {

    }

    @Override
    public void visit(ListNode node) {

    }

    @Override
    public void visit(Literal node) {

    }

    @Override
    public void visit(Node node) {

    }

    @Override
    public void visit(Param node) {

    }

    @Override
    public void visit(ParamList node) {

    }

    @Override
    public void visit(Range node) {

    }

    @Override
    public void visit(Text node) {

    }

    @Override
    public void visit(Type node) {

    }

    @Override
    public void visit(TypeApply node) {

    }

    @Override
    public void visit(TypeName node) {

    }

    private static class LoopWatcher {
        int loopCounter = 0;

        public void push() {
            loopCounter++;
        }

        public void pop() {
            loopCounter--;
        }

        public void addBreak() {
            if (loopCounter < 1) {
                System.err.println("<break> not in a loop");
            }
        }

        public void addContinue() {
            if (loopCounter < 1) {
                System.err.println("<continue> not in a loop");
            }
        }
    }
}
