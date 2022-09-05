package cloudladder.core.compiler;

import cloudladder.core.compiler.ast.AST;
import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.*;
import cloudladder.core.compiler.ast.program.*;
import cloudladder.core.compiler.ast.statement.*;
import cloudladder.core.ir.*;
import cloudladder.core.object.CLNumber;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import grammar.CLLexer;
import grammar.CLParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CLCompiler {
    public CLCompiler() {
    }

    public void compileExpression(ASTExpression tree, CLCompileContext context) {
        if (tree instanceof ASTBinaryExpression) {
            compileBinaryExpression((ASTBinaryExpression) tree, context);
        } else if (tree instanceof ASTUnaryExpression) {
            compileUnaryExpression((ASTUnaryExpression) tree, context);
        } else if (tree instanceof ASTBooleanLiteral) {
            compileBooleanLiteral((ASTBooleanLiteral) tree, context);
        } else if (tree instanceof ASTFieldAccess) {
            compileFieldAccess((ASTFieldAccess) tree, context);
        } else if (tree instanceof ASTFunctionCall) {
            compileFunctionCall((ASTFunctionCall) tree, context);
        } else if (tree instanceof ASTIdentifierExpression) {
            compileIdentifierExpression((ASTIdentifierExpression) tree, context);
        } else if (tree instanceof ASTIndexing) {
            compileIndexing((ASTIndexing) tree, context);
        } else if (tree instanceof ASTNumberLiteral) {
            compileNumberLiteral((ASTNumberLiteral) tree, context);
        } else if (tree instanceof ASTStringLiteral) {
            compileStringLiteral((ASTStringLiteral) tree, context);
        } else if (tree instanceof ASTArrayLiteral) {
            compileArrayLiteral((ASTArrayLiteral) tree, context);
        } else if (tree instanceof ASTObjLiteral) {
            compileObjLiteral((ASTObjLiteral) tree, context);
        } else if (tree instanceof ASTFunctionExpression) {
            compileFunctionExpression((ASTFunctionExpression) tree, context);
        } else if (tree instanceof ASTArrowExpression) {
            compileArrowExpression((ASTArrowExpression) tree, context);
        } else if (tree instanceof ASTPipeExpression) {
            compilePipeExpression((ASTPipeExpression) tree, context);
        }
    }

    public void compileArrowExpression(ASTArrowExpression tree, CLCompileContext context) {
//        ASTExpression rightExpression = tree.getRight();
//        ExpressionSpecific left = compileExpression(tree.getLeft(), context);
////        ExpressionSpecific right = compileExpression(tree.getRight(), context);
//
//        if (rightExpression instanceof ASTIdentifierExpression) {
//            String name = ((ASTIdentifierExpression) rightExpression).getToken().getText();
//            CLIRRef ref = new CLIRRef(name, left.varName);
//            context.addIr(ref);
//            return new ExpressionSpecific(name);
//        } else if (rightExpression instanceof ASTArrayLiteral) {
//            ArrayList<ASTExpression> expressions = ((ASTArrayLiteral) rightExpression).getItems();
//            for (int i = 0; i < expressions.size(); i++) {
//                ASTExpression expression = expressions.get(i);
//                if (!(expression instanceof ASTIdentifierExpression)) {
//                    // todo error
//                    return new ExpressionSpecific("$" + context.iterTempVar());
//                }
//                String name = ((ASTIdentifierExpression) expression).getToken().getText();
//
//                int t = context.iterTempVar();
//                CLIRIndexingLiteral indexing = new CLIRIndexingLiteral(
//                        left.varName,
//                        i,
//                        null,
//                        "int",
//                        "$" + t
//                );
//                CLIRRef ref = new CLIRRef(name, "$" + t);
//                context.addIr(indexing);
//                context.addIr(ref);
//            }
//        }
//
//        return null;
    }

    public void compilePipeExpression(ASTPipeExpression tree, CLCompileContext context) {
//        ASTExpression leftExpression = tree.getLeft();
//        ASTExpression rightExpression = tree.getRight();
//
//        ExpressionSpecific left = compileExpression(leftExpression, context);
//        if (rightExpression instanceof ASTFunctionCall) {
//            ArrayList<ASTExpression> args = ((ASTFunctionCall) rightExpression).getArgs();
//
//            ASTExpression func = ((ASTFunctionCall) rightExpression).getFunc();
//            ExpressionSpecific funcSpec = compileExpression(func, context);
//
//            int t = context.iterTempVar();
//            CLIRCall callIr = new CLIRCall(funcSpec.varName, "$" + t);
//            callIr.addParam(left.varName);
//            for (int i = 0; i < args.size(); i++) {
//                ExpressionSpecific arg = compileExpression(args.get(i), context);
//
//                callIr.addParam(arg.varName);
//            }
//
//            context.addIr(callIr);
//
//            return new ExpressionSpecific("$" + t);
//        } else {
//            ExpressionSpecific right = compileExpression(rightExpression, context);
//
//            int t = context.iterTempVar();
//            CLIRCall callIr = new CLIRCall(right.varName, "$" + t);
//            callIr.addParam(left.varName);
//            context.addIr(callIr);
//
//            return new ExpressionSpecific("$" + t);
//        }
    }

    public void compileFunctionExpression(ASTFunctionExpression tree, CLCompileContext context) {
//        String name = "$" + context.iterTempVar();
//        ArrayList<String> params = new ArrayList<>();
//        for (ASTToken token : tree.getParams()) {
//            params.add(token.getText());
//        }
//        CLIRDefFunction def = new CLIRDefFunction(name, params, tree.getAst());
//        context.addIr(def);
//
//        return new ExpressionSpecific(name);
    }

    public void compileObjLiteral(ASTObjLiteral tree, CLCompileContext context) {
//        String name = "$" + context.iterTempVar();
//        CLIRDefObj def = new CLIRDefObj(name);
//
//        for (ASTObjItem item : tree.getItems()) {
//            if (item.isSingle()) {
//                String keyName = "$" + context.iterTempVar();
//                CLIRDefString defString = new CLIRDefString(keyName, item.getKey().getText());
//                context.addIr(defString);
//
//                def.addItem(keyName, item.getKey().getText());
//            } else if (item.isDynamic()) {
//                ExpressionSpecific key = compileExpression(item.getDyKey(), context);
//                ExpressionSpecific value = compileExpression(item.getValue(), context);
//                def.addItem(key.getVarName(), value.getVarName());
//            } else if (item.isString()) {
//                String keyName = "$" + context.iterTempVar();
//                String key = item.getKey().getText();
//                key = key.substring(1, key.length() - 1);
//                CLIRDefString defString = new CLIRDefString(keyName, key);
//                context.addIr(defString);
//
//                ExpressionSpecific value = compileExpression(item.getValue(), context);
//
//                def.addItem(keyName, value.getVarName());
//            } else {
//                String keyName = "$" + context.iterTempVar();
//                CLIRDefString defString = new CLIRDefString(keyName, item.getKey().getText());
//                context.addIr(defString);
//
//                ExpressionSpecific value = compileExpression(item.getValue(), context);
//
//                def.addItem(keyName, value.getVarName());
//            }
//        }
//
//        context.addIr(def);
//        return new ExpressionSpecific(name);
    }

    public void compileArrayLiteral(ASTArrayLiteral tree, CLCompileContext context) {
//        int i = context.iterTempVar();
//        String name = "$" + i;
//        CLIRDefArray def = new CLIRDefArray(name);
//
//        for (ASTExpression exp : tree.getItems()) {
//            ExpressionSpecific e = compileExpression(exp, context);
//            def.addValue(e.getVarName());
//        }
//
//        context.addIr(def);
//
//        return new ExpressionSpecific(name);
    }

    public void compileStringLiteral(ASTStringLiteral tree, CLCompileContext context) {
        String str = tree.getToken().getText();
        str = str.substring(1, str.length() - 1);
        str = StringEscapeUtils.unescapeJava(str);

        int constIndex = context.addStringLiteral(str);
        CLIR ir = new CLIRLoadConst(constIndex);
        context.addIr(ir);
    }

    public void compileNumberLiteral(ASTNumberLiteral tree, CLCompileContext context) {
        double value = Double.parseDouble(tree.getToken().getText());
        int index = 0;
        if (value % 1 == 0) {
            // int
            index = context.addIntegerLiteral((int) value);
        } else {
            index = context.addDoubleLiteral(value);
        }

        CLIR ir = new CLIRLoadConst(index);
        context.addIr(ir);
    }

    public void compileIndexing(ASTIndexing tree, CLCompileContext context) {

    }

    public void compileIdentifierExpression(ASTIdentifierExpression tree, CLCompileContext context) {
//        return new ExpressionSpecific(tree.getToken().getText());
        String identifier = tree.getToken().getText();
        int index = context.addName(identifier);

        CLIR ir = new CLIRLoadName(index);
        context.addIr(ir);
    }

    public void compileFunctionCall(ASTFunctionCall tree, CLCompileContext context) {
////        String funcName = "";
////        String self = "";
//
////        if (tree.getFunc() instanceof ASTFieldAccess) {
////            ASTFieldAccess fa = (ASTFieldAccess) tree.getFunc();
////            ExpressionSpecific e = compileExpression(fa.getLeft(), context);
////            self = e.getVarName();
////
////            String faTemp = "$" + context.iterTempVar();
////            CLIRIndexingLiteral clirIndexingLiteral = new CLIRIndexingLiteral(
////                    e.varName,
////                    0,
////                    fa.getRight().getText(),
////                    "string",
////                    faTemp
////            );
////            context.addIr(clirIndexingLiteral);
////
////            funcName = faTemp;
////        } else if (tree.getFunc() instanceof ASTIndexing) {
////            ASTIndexing indexing = (ASTIndexing) tree.getFunc();
////
////            // a[b]()
////            // compile a
////            ExpressionSpecific indexer = compileExpression(indexing.getIndexer(), context);
////            // compile b
////            ExpressionSpecific index = compileExpression(indexing.getIndex(), context);
////
////            self = indexer.getVarName();
////
////            CLIRCall ir1 = new CLIRCall("[[access]]");
////            ir1.addParam(index.getVarName());
////            context.addIr(ir1);
////
////            int t = context.iterTempVar();
////            CLIRRef ir2 = new CLIRRef("$" + t, "$r0");
////            context.addIr(ir2);
////
////            funcName = "$" + t;
////        } else {
////            ExpressionSpecific e1 = compileExpression(tree.getFunc(), context);
////            funcName = e1.getVarName();
////            self = "null";
////        }
//
//        ExpressionSpecific e1 = compileExpression(tree.getFunc(), context);
//        String funcName = e1.getVarName();
////        self = "null";
//
//        String dest = "$" + context.iterTempVar();
//        CLIRCall ir = new CLIRCall(funcName, dest);
////        ir.addParam(self);
//        for (int i = 0; i < tree.getArgs().size(); i++) {
//            ASTExpression arg = tree.getArgs().get(i);
//            ExpressionSpecific es = compileExpression(arg, context);
//
//            ir.addParam(es.getVarName());
//        }
//        context.addIr(ir);
//
//        return new ExpressionSpecific(dest);
    }

    public void compileFieldAccess(ASTFieldAccess tree, CLCompileContext context) {
//        ExpressionSpecific e1 = compileExpression(tree.getLeft(), context);
//
//        String fieldName = tree.getRight().getText();
//        int t = context.iterTempVar();
//        CLIRIndexingLiteral irIndexing = new CLIRIndexingLiteral(
//                e1.varName,
//                0,
//                fieldName,
//                "string",
//                "$" + t
//        );
//
//        context.addIr(irIndexing);
//
//        return new ExpressionSpecific("$" + t);
    }

    public void compileBooleanLiteral(ASTBooleanLiteral tree, CLCompileContext context) {
//        String value = tree.getToken().getText();
//        return new ExpressionSpecific("$" + value);
    }

    public void compileUnaryExpression(ASTUnaryExpression tree, CLCompileContext context) {
//        ExpressionSpecific e1 = compileExpression(tree.getExpression(), context);
//
//        String op = tree.getOp().getText();
//
//        String methodToBeCalled = "error";
//        if (op.equals("-")) {
//            methodToBeCalled = "[[neg]]";
//        } else if (op.equals("!")) {
//            methodToBeCalled = "[[not]]";
//        }
//
//        String dest = "$" + context.iterTempVar();
//        CLIRCall ir1 = new CLIRCall(methodToBeCalled, dest);
//        ir1.addParam(e1.getVarName());
//        context.addIr(ir1);
//
//        return new ExpressionSpecific(dest);
    }

    /**
     *     [left]
     *     bt [left] and_right
     *     ref t $false
     *     jump and_end
     * and_right:
     *     [right]
     *     call [[and]] [left] [right]
     *     ref t $r0
     * and_end:
     *
     * @param tree
     * @param context
     * @return
     */
    private void compileAndExpression(ASTBinaryExpression tree, CLCompileContext context) {
//        String name = "and" + context.iterAndOr();
//        String endName = name + "_end";
//        String secondName = name + "_right";
//
//        ExpressionSpecific e1 = compileExpression(tree.getLeft(), context);
//        CLIRBt bt = new CLIRBt(e1.getVarName(), secondName, 0);
//        context.addZip(secondName, bt);
//
//        String dest = "$" + context.iterTempVar();
//        CLIRRef ref1 = new CLIRRef(dest, "$false");
//        context.addIr(ref1);
//        CLIRJump jump = new CLIRJump(0, endName);
//        context.addZip(endName, jump);
//
//        context.nextLabel(secondName);
//
////        String dest = "$" + context.iterTempVar();
//        ExpressionSpecific e2 = compileExpression(tree.getRight(), context);
//        CLIRCall call = new CLIRCall("[[and]]", dest);
//        call.addParam(e1.getVarName());
//        call.addParam(e2.getVarName());
//        context.addIr(call);
//
//        context.nextLabel(endName);
//        return new ExpressionSpecific(dest);
    }

    private void compileOrExpression(ASTBinaryExpression tree, CLCompileContext context) {
//        String name = "or" + context.iterAndOr();
//        String endName = name + "_end";
//        String secondName = name + "_right";
//
//        ExpressionSpecific e1 = compileExpression(tree.getLeft(), context);
//        CLIRBf bf = new CLIRBf(e1.getVarName(), secondName, 0);
//        context.addZip(secondName, bf);
//
//        String dest = "$" + context.iterTempVar();
//        CLIRRef ref1 = new CLIRRef(dest, "$true");
//        context.addIr(ref1);
//        CLIRJump jump = new CLIRJump(0, endName);
//        context.addZip(endName, jump);
//
//        context.nextLabel(secondName);
//
////        String dest = "$" + context.iterTempVar();
//        ExpressionSpecific e2 = compileExpression(tree.getRight(), context);
//        CLIRCall call = new CLIRCall("[[or]]", dest);
//        call.addParam(e1.getVarName());
//        call.addParam(e2.getVarName());
//        context.addIr(call);
//
//
//        context.nextLabel(endName);
//        return new ExpressionSpecific(dest);
    }

    public void compileBinaryExpression(ASTBinaryExpression tree, CLCompileContext context) {
        String op = tree.getOp().getText();
        if (op.equals("&&")) {
            compileAndExpression(tree, context);
            return;
        } else if (op.equals("||")) {
            compileOrExpression(tree, context);
            return;
        }

        this.compileExpression(tree.getRight(), context);
        this.compileExpression(tree.getLeft(), context);

        CLIR ir = switch (op) {
            case "+" -> new CLIRAdd();
            default -> new CLIRAdd();
        };

//        String methodToBeCalled = switch (op) {
//            case "+" -> "[[add]]";
//            case "-" -> "[[sub]]";
//            case "*" -> "[[mul]]";
//            case "/" -> "[[div]]";
//            case "%" -> "[[mod]]";
//            case "==" -> "[[eql]]";
//            case ">" -> "[[gt]]";
//            case "<" -> "[[lt]]";
//            case "!=" -> "[[neq]]";
//            case ">=" -> "[[ge]]";
//            case "<=" -> "[[le]]";
//            default -> "error";
//        };

        context.addIr(ir);
    }

    public void compileStatement(ASTStatement tree, CLCompileContext context) {
        if (tree instanceof ASTBreakStatement) {
            compileBreakStatement((ASTBreakStatement) tree, context);
        } else if (tree instanceof ASTCompoundStatement) {
            compileCompoundStatement((ASTCompoundStatement) tree, context);
        } else if (tree instanceof ASTContinueStatement) {
            compileContinueStatement((ASTContinueStatement) tree, context);
        } else if (tree instanceof ASTDataStatement) {
            compileDataStatement((ASTDataStatement) tree, context);
        } else if (tree instanceof ASTForDeclStatement) {
            compileForDeclStatement((ASTForDeclStatement) tree, context);
        } else if (tree instanceof ASTForStatement) {
            compileForStatement((ASTForStatement) tree, context);
        } else if (tree instanceof ASTReturnStatement) {
            compileReturnStatement((ASTReturnStatement) tree, context);
        } else if (tree instanceof ASTSelectionStatement) {
            compileSelectionStatement((ASTSelectionStatement) tree, context);
        } else if (tree instanceof ASTAssignmentStatement) {
            compileAssignStatement((ASTAssignmentStatement) tree, context);
        } else if (tree instanceof ASTWhileStatement) {
            compileWhileStatement((ASTWhileStatement) tree, context);
        } else if (tree instanceof ASTExpressionStatement) {
            compileExpressionStatement((ASTExpressionStatement) tree, context);
        }
    }

    public void compileExpressionStatement(ASTExpressionStatement tree, CLCompileContext context) {
        compileExpression(tree.getExpression(), context);
        context.addIr(new CLIRPop());
    }

    public void compileWhileStatement(ASTWhileStatement tree, CLCompileContext context) {
//        context.addIr(new CLIRNewBlock());
//
//        String iterName;
//        if (tree.getLabel() != null) {
//            iterName = tree.getLabel().getText();
//        } else {
//            iterName = "while" + context.iterIterCount();
//        }
//        context.pushIterName(iterName);
//
//        // cond
//        context.nextLabel(iterName + "_cond");
//        ExpressionSpecific e = compileExpression(tree.getCondition(), context);
//        CLIRBf bf = new CLIRBf(e.getVarName(), iterName + "_end", 0);
//        context.addZip(iterName + "_end", bf);
//
//        // body
//        compileStatement(tree.getStatement(), context);
//        int offset = context.getLabelOffset(iterName + "_cond");
//        CLIRJump jump = new CLIRJump(offset, iterName + "_cond");
//        context.addIr(jump);
//
//        context.nextLabel(iterName + "_end");
//
//        context.popIterName();
//        context.addIr(new CLIREndBlock());
    }

    public void compileAssignStatement(ASTAssignmentStatement tree, CLCompileContext context) {
//        ExpressionSpecific right = compileExpression(tree.getRight(), context);
//
//        if (tree.getLeft() instanceof ASTIdentifierExpression) {
//            String name = ((ASTIdentifierExpression) tree.getLeft()).getToken().getText();
//            CLIRRef ref = new CLIRRef(name, right.getVarName());
//            context.addIr(ref);
//        } else if (tree.getLeft() instanceof ASTIndexing) {
//            ASTIndexing indexing = (ASTIndexing) tree.getLeft();
//            ExpressionSpecific indexer = compileExpression(indexing.getIndexer(), context);
//            ExpressionSpecific index = compileExpression(indexing.getIndex(), context);
//
//            CLIRSet set = new CLIRSet(indexer.getVarName(), index.getVarName(), right.getVarName());
//            context.addIr(set);
//        } else if (tree.getLeft() instanceof ASTFieldAccess) {
//            ASTFieldAccess access = (ASTFieldAccess) tree.getLeft();
//            ExpressionSpecific indexer = compileExpression(access.getLeft(), context);
//            String index = context.tempString(access.getRight().getText());
//            CLIRSet set = new CLIRSet(indexer.getVarName(), index, right.getVarName());
//            context.addIr(set);
//        }
    }

    public void compileSelectionStatement(ASTSelectionStatement tree, CLCompileContext context) {
//        String ifName = "if" + context.iterIfCount();
//        context.pushIfName(ifName);
//
//        boolean hasElse = tree.getStatementFalse() != null;
//
//        // cond
//        ExpressionSpecific e = compileExpression(tree.getCondition(), context);
//        CLIRBf bf = new CLIRBf(e.getVarName(), ifName + "_end", 0);
//        if (hasElse) {
//            context.addZip(ifName + "_false", bf);
//        } else {
//            context.addZip(ifName + "_end", bf);
//        }
//
//        // true
//        context.addIr(new CLIRNewBlock());
//        compileStatement(tree.getStatementTrue(), context);
//        context.addIr(new CLIREndBlock());
//        CLIRJump jump = new CLIRJump(0, ifName + "_end");
//        if (hasElse) {
//            context.addZip(ifName + "_end", jump);
//        }
//
//        // false
//        if (tree.getStatementFalse() != null) {
//            context.nextLabel(ifName + "_false");
//            context.addIr(new CLIRNewBlock());
//            compileStatement(tree.getStatementFalse(), context);
//            context.addIr(new CLIREndBlock());
//        }
//
//        context.popIfName();
//        if (!hasElse) {
//            context.nextLabel(ifName + "_end");
//        }
    }

    public void compileReturnStatement(ASTReturnStatement tree, CLCompileContext context) {
        this.compileExpression(tree.getValue(), context);
        CLIRReturn ir = new CLIRReturn();
        context.addIr(ir);
    }

    public void compileForStatement(ASTForStatement tree, CLCompileContext context) {
//        context.addIr(new CLIRNewBlock());
//
//
//        String iterName;
//        if (tree.getLabel() == null) {
//            iterName = "iter" + context.iterIterCount();
//        } else {
//            iterName = tree.getLabel().getText();
//        }
//        context.pushIterName(iterName);
//
//        // init
//        compileStatement(tree.getInit(), context);
//
//        // cond
//        context.nextLabel(iterName + "_cond");
//        ExpressionSpecific condSpec = compileExpression(tree.getCondition(), context);
//        CLIRBf bf = new CLIRBf(condSpec.getVarName(), iterName + "_end", 0);
//        context.addZip(iterName + "_end", bf);
//
//        // body
//        compileStatement(tree.getContent(), context);
//
//        // step
//        context.nextLabel(iterName + "_step");
//        compileStatement(tree.getStep(), context);
//
//        CLIRJump jump = new CLIRJump(context.getLabelOffset(iterName + "_cond"), iterName + "_cond");
//        context.addIr(jump);
//
//        // end
//        context.nextLabel(iterName + "_end");
//
//        context.popIterName();
//        context.addIr(new CLIREndBlock());
    }

    public void compileForDeclStatement(ASTForDeclStatement tree, CLCompileContext context) {
//        context.addIr(new CLIRNewBlock());
//
//        String iterName;
//        if (tree.getLabel() == null) {
//            iterName = "iter" + context.iterIterCount();
//        } else {
//            iterName = tree.getLabel().getText();
//        }
//        context.pushIterName(iterName);
//
//        compileDataStatement(tree.getDecl(), context);
//
//        // cond
//        context.nextLabel(iterName + "_cond");
//        ExpressionSpecific condSpec = compileExpression(tree.getCondition(), context);
//        CLIRBf bf = new CLIRBf(condSpec.getVarName(), iterName + "_end", 0);
//        context.addZip(iterName + "_end", bf);
//
//        // body
//        compileStatement(tree.getContent(), context);
//
//        // step
//        context.nextLabel(iterName + "_step");
//        compileStatement(tree.getStep(), context);
//
//        CLIRJump jump = new CLIRJump(context.getLabelOffset(iterName + "_cond"), iterName + "_cond");
//        context.addIr(jump);
//
//        // end
//        context.nextLabel(iterName + "_end");
//
//        context.popIterName();
//        context.addIr(new CLIREndBlock());
    }

    public void compileDataStatement(ASTDataStatement tree, CLCompileContext context) {
        for (ASTDataStatementItem item : tree.getItems()) {
            String name = item.getName().getText();
            context.addLocalName(name);
            ASTExpression init = item.getInitializer();

            compileExpression(init, context);

            int nameIndex = context.addName(name);
            CLIR ir = new CLIRStoreName(nameIndex);
            context.addIr(ir);
        }
    }

    public void compileContinueStatement(ASTContinueStatement tree, CLCompileContext context) {
//        if (tree.getLabel() == null) {
//            String cur = context.getCurIterName();
//            CLIRJump jump = new CLIRJump(0, cur + "_step");
//            context.addZip(cur + "_step", jump);
//        } else {
//            String target = tree.getLabel().getText();
//            CLIRJump jump = new CLIRJump(0, target + "_step");
//            context.addZip(target + "_step", jump);
//        }
    }

    public void compileCompoundStatement(ASTCompoundStatement tree, CLCompileContext context) {
        for (ASTStatement stat : tree.getItems()) {
            compileStatement(stat, context);
        }
    }

    public void compileBreakStatement(ASTBreakStatement tree, CLCompileContext context) {
//        if (tree.getLabel() == null) {
//            String cur = context.getCurIterName();
//            CLIRJump jump = new CLIRJump(0, cur + "_end");
//            context.addZip(cur + "_end", jump);
//        } else {
//            String target = tree.getLabel().getText();
//            CLIRJump jump = new CLIRJump(0, target + "_end");
//            context.addZip(target + "_end", jump);
//        }
    }

    // program unit
    public void compileProgram(ASTProgram tree, CLCompileContext context) {
        for (ASTProgramUnit u : tree.getProgramUnits()) {
            compileProgramUnit(u, context);
        }
    }

    public void compileProgramUnit(ASTProgramUnit tree, CLCompileContext context) {
        if (tree instanceof ASTFunctionDefinition) {
            compileFunctionDefinition((ASTFunctionDefinition) tree, context);
        } else if (tree instanceof ASTRootStatement) {
            compileStatement(((ASTRootStatement) tree).getStatement(), context);
        } else if (tree instanceof ASTImport) {
            compileImport((ASTImport) tree, context);
        } else if (tree instanceof ASTExport) {
            compileExport((ASTExport) tree, context);
        }
    }

    public void compileImport(ASTImport tree, CLCompileContext context) {
//        String path = tree.fullPath();
//        String as = tree.getAsString();
//
//        CLIRImport ir = new CLIRImport(path, as);
//        context.addIr(ir);
    }

    public void compileExport(ASTExport tree, CLCompileContext context) {
//        String name = tree.getName().getText();
//
//        ExpressionSpecific e = compileExpression(tree.getExpression(), context);
//        CLIRExport ir = new CLIRExport(name, e.varName);
//        context.addIr(ir);
    }

    public void compileFunctionDefinition(ASTFunctionDefinition tree, CLCompileContext context) {
//        String name = tree.getName().getText();
//        ArrayList<String> params = new ArrayList<>();
//        for (ASTToken token : tree.getParams()) {
//            params.add(token.getText());
//        }
//        CLIRDefFunction def = new CLIRDefFunction(name, params, tree.getBody());
//        context.addIr(def);
    }

    public void compileFile(String program, CLCompileContext context) {
        try {
            CLLexer lexer = new CLLexer(CharStreams.fromString(program));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CLParser parser = new CLParser(tokens);
            CLParser.ProgramContext tree = parser.program();

            AntlrVisitor visitor = new AntlrVisitor();
            AST ast = visitor.visit(tree);

            this.compileProgram((ASTProgram) ast, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void compileFile(String program) {
//        CLCompileContext2 context = new CLCompileContext2();
//        CLCompiler compiler = new CLCompiler();
//        compiler.compileFile(program, context);
//    }

    public ASTProgram compileToAST(String code) {
        CLLexer lexer = new CLLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParser parser = new CLParser(tokens);
        CLParser.ProgramContext tree = parser.program();

        AntlrVisitor visitor = new AntlrVisitor();

        return (ASTProgram) visitor.visit(tree);
    }
}
