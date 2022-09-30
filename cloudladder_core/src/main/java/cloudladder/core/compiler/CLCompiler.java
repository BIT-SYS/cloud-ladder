package cloudladder.core.compiler;

import cloudladder.core.compiler.ast.AST;
import cloudladder.core.compiler.ast.ASTToken;
import cloudladder.core.compiler.ast.expression.*;
import cloudladder.core.compiler.ast.program.*;
import cloudladder.core.compiler.ast.statement.*;
import cloudladder.core.error.CLCompileError;
import cloudladder.core.error.CLCompileErrorType;
import cloudladder.core.ir.*;
import cloudladder.core.object.*;
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

    public void compileExpression(ASTExpression tree, CLCompileContext context) throws CLCompileError {
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

    public void compileArrowExpression(ASTArrowExpression tree, CLCompileContext context) throws CLCompileError {
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

    public void compilePipeExpression(ASTPipeExpression tree, CLCompileContext context) throws CLCompileError {
        ASTExpression left = tree.getLeft();
        ASTExpression right = tree.getRight();

        if (right instanceof ASTFunctionCall functionCall) {
            ArrayList<ASTExpression> args = functionCall.getArgs();
            for (int i = args.size() - 1; i >= 0; i--) {
                this.compileExpression(args.get(i), context);
            }

            this.compileExpression(left, context);
            this.compileExpression(functionCall.getFunc(), context);

            context.addIr(new CLIRCall(args.size() + 1));
        } else {
            this.compileExpression(left, context);
            this.compileExpression(right, context);
            context.addIr(new CLIRCall(1));
        }
    }

    public void compileFunctionExpression(ASTFunctionExpression tree, CLCompileContext context) throws CLCompileError {
        CLCompileContext functionContext = new CLCompileContext();
        ArrayList<String> params = new ArrayList<>();
        for (ASTToken name : tree.getParams()) {
            functionContext.addLocalName(name.getText());
            params.add(name.getText());
        }

        this.compileStatement(tree.getAst(), functionContext);

        // if the last ir is not return, manually add one
        if (!(functionContext.ir.get(functionContext.ir.size() - 1) instanceof CLIRReturn)) {
            functionContext.addIr(new CLIRPushUnit());
            functionContext.addIr(new CLIRReturn());
        }

        CLCodeObject codeObject = functionContext.getCodeObject();

        CLFunctionDefinition function = new CLFunctionDefinition(codeObject, params, false);
        int index = context.addConstant(function);

        context.addIr(new CLIRLoadConst(index));
        context.addIr(new CLIRBuildFunction());
    }

    public void compileObjLiteral(ASTObjLiteral tree, CLCompileContext context) throws CLCompileError {
        for (ASTObjItem item : tree.getItems()) {
            if (item.isSingle()) {
                String key = item.getKey().getText();
                int keyIndex = context.addStringLiteral(key);

                context.addIr(new CLIRLoadName(context.addName(key)));
                context.addIr(new CLIRLoadConst(keyIndex));
            } else if (item.isDynamic()) {
                this.compileExpression(item.getValue(), context);
                this.compileExpression(item.getDyKey(), context);
            } else if (item.isString()) {
                String key = item.getKey().getText();
                key = key.substring(1, key.length() - 1);
                int keyIndex = context.addStringLiteral(key);

                this.compileExpression(item.getValue(), context);
                context.addIr(new CLIRLoadConst(keyIndex));
            } else {
                String key = item.getKey().getText();
                int keyIndex = context.addStringLiteral(key);

                this.compileExpression(item.getValue(), context);
                context.addIr(new CLIRLoadConst(keyIndex));
            }
        }

        context.addIr(new CLIRBuildDict(tree.getItems().size()));
    }

    public void compileArrayLiteral(ASTArrayLiteral tree, CLCompileContext context) throws CLCompileError {
        ArrayList<ASTExpression> items = tree.getItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            this.compileExpression(items.get(i), context);
        }

        CLIR ir = new CLIRBuildArray(items.size());
        context.addIr(ir);
    }

    public void compileStringLiteral(ASTStringLiteral tree, CLCompileContext context) throws CLCompileError {
        String str = tree.getToken().getText();
        str = str.substring(1, str.length() - 1);
        str = StringEscapeUtils.unescapeJava(str);

        int constIndex = context.addStringLiteral(str);
        CLIR ir = new CLIRLoadConst(constIndex);
        context.addIr(ir);
    }

    public void compileNumberLiteral(ASTNumberLiteral tree, CLCompileContext context) throws CLCompileError {
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

    public void compileIndexing(ASTIndexing tree, CLCompileContext context) throws CLCompileError {
        this.compileExpression(tree.getIndex(), context);
        this.compileExpression(tree.getIndexer(), context);

        CLIR ir = new CLIRIndex();
        context.addIr(ir);
    }

    public void compileIdentifierExpression(ASTIdentifierExpression tree, CLCompileContext context) throws CLCompileError {
        String identifier = tree.getToken().getText();
        int index = context.addName(identifier);

        CLIR ir = new CLIRLoadName(index);
        context.addIr(ir);
    }

    public void compileFunctionCall(ASTFunctionCall tree, CLCompileContext context) throws CLCompileError {
        ArrayList<ASTExpression> args = tree.getArgs();
        for (int i = args.size() - 1; i >= 0; i--) {
            this.compileExpression(args.get(i), context);
        }
        this.compileExpression(tree.getFunc(), context);

        CLIRCall ir = new CLIRCall(args.size());
        context.addIr(ir);
    }

    public void compileFieldAccess(ASTFieldAccess tree, CLCompileContext context) throws CLCompileError {
        String fieldName = tree.getRight().getText();
        int nameIndex = context.addStringLiteral(fieldName);

        context.addIr(new CLIRLoadConst(nameIndex));
        this.compileExpression(tree.getLeft(), context);
        context.addIr(new CLIRIndex());
    }

    public void compileBooleanLiteral(ASTBooleanLiteral tree, CLCompileContext context) throws CLCompileError {
        String value = tree.getToken().getText();
        boolean b = value.equals("true");
        context.addIr(new CLIRLoadBool(b));
    }

    public void compileUnaryExpression(ASTUnaryExpression tree, CLCompileContext context) throws CLCompileError {
        this.compileExpression(tree.getExpression(), context);

        CLIR ir = switch (tree.getOp().getText()) {
            case "-" -> new CLIRNeg();
            case "!" -> new CLIRNot();
            default -> throw new CLCompileError(CLCompileErrorType.Unexpected, "operator `" + tree.getOp().getText() + "` not implemented");
        };
        context.addIr(ir);
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
    private void compileAndExpression(ASTBinaryExpression tree, CLCompileContext context) throws CLCompileError {
        this.compileExpression(tree.getLeft(), context);

        context.addIr(new CLIRDup());
        CLIRJumpFalse j1 = new CLIRJumpFalse(0);
        context.addIr(j1);
        int j1Position = context.ir.size() - 1;

        this.compileExpression(tree.getRight(), context);
        context.addIr(new CLIRAnd());

        j1.step = context.ir.size() - j1Position - 1;
    }

    private void compileOrExpression(ASTBinaryExpression tree, CLCompileContext context) throws CLCompileError {
        this.compileExpression(tree.getLeft(), context);

        context.addIr(new CLIRDup());
        CLIRJumpTrue j1 = new CLIRJumpTrue(0);
        context.addIr(j1);
        int j1Position = context.ir.size() - 1;

        this.compileExpression(tree.getRight(), context);
        context.addIr(new CLIROr());

        j1.step = context.ir.size() - j1Position - 1;
    }

    public void compileBinaryExpression(ASTBinaryExpression tree, CLCompileContext context) throws CLCompileError {
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
            case "-" -> new CLIRSub();
            case "*" -> new CLIRMul();
            case "/" -> new CLIRDiv();
            case "%" -> new CLIRMod();
            case "==" -> new CLIREq();
            case ">" -> new CLIRGt();
            case ">=" -> new CLIRGe();
            case "<" -> new CLIRLt();
            case "<=" -> new CLIRLe();
            case "!=" -> new CLIRNe();
            default -> throw new CLCompileError(CLCompileErrorType.Unexpected, "cannot recognize operator `" + op + "`");
        };

        context.addIr(ir);
    }

    public void compileStatement(ASTStatement tree, CLCompileContext context) throws CLCompileError {
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

    public void compileExpressionStatement(ASTExpressionStatement tree, CLCompileContext context) throws CLCompileError {
        compileExpression(tree.getExpression(), context);
        context.addIr(new CLIRPop());
    }

    public void compileWhileStatement(ASTWhileStatement tree, CLCompileContext context) throws CLCompileError {
        int startPosition = context.ir.size();

        // push loop context
        String loopName;
        if (tree.getLabel() == null) {
            loopName = context.pushLoopContext(null);
        } else {
            loopName = context.pushLoopContext(tree.getLabel().getText());
        }

        this.compileExpression(tree.getCondition(), context);

        CLIRJumpFalse jf = new CLIRJumpFalse(0);
        context.addIr(jf);
        int jfPosition = context.ir.size() - 1;

        this.compileStatement(tree.getStatement(), context);

        CLIRJump j = new CLIRJump(startPosition - context.ir.size() - 1);
        context.addIr(j);

        jf.step = context.ir.size() - jfPosition - 1;

        // process continues and breaks
        context.signalJump(loopName + "end", context.ir.size());
        context.signalJump(loopName + "start", startPosition);
        context.popLoopContext();
    }

    public void compileAssignStatement(ASTAssignmentStatement tree, CLCompileContext context) throws CLCompileError {
        this.compileExpression(tree.getRight(), context);

        if (tree.getLeft() instanceof ASTIdentifierExpression) {
            String name = ((ASTIdentifierExpression) tree.getLeft()).getToken().getText();
            int nameIndex = context.addName(name);
//            CLIR ir = new CLIRStoreNameExist(nameIndex);
            CLIR ir = new CLIRStoreName(nameIndex);
            context.addIr(ir);
        } else if (tree.getLeft() instanceof ASTIndexing indexing) {
            this.compileExpression(indexing.getIndex(), context);
            this.compileExpression(indexing.getIndexer(), context);
            context.addIr(new CLIRSet());
        } else if (tree.getLeft() instanceof ASTFieldAccess fieldAccess) {
            String fieldName = fieldAccess.getRight().getText();
            int nameIndex = context.addStringLiteral(fieldName);
            context.addIr(new CLIRLoadConst(nameIndex));
            this.compileExpression(fieldAccess.getLeft(), context);
            context.addIr(new CLIRSet());
        }
    }

    public void compileSelectionStatement(ASTSelectionStatement tree, CLCompileContext context) throws CLCompileError {
        this.compileExpression(tree.getCondition(), context);

        CLIRJumpFalse jump1 = new CLIRJumpFalse(0);
        context.addIr(jump1);
        int jump1Position = context.ir.size() - 1;

        this.compileStatement(tree.getStatementTrue(), context);

        if (tree.getStatementFalse() != null) {
            CLIRJump jump2 = new CLIRJump(0);
            context.addIr(jump2);
            int jump2Position = context.ir.size() - 1;

            this.compileStatement(tree.getStatementFalse(), context);

            jump1.step = jump2Position - jump1Position;
            jump2.step = context.ir.size() - 1 - jump2Position;
        } else {
            jump1.step = context.ir.size() - 1 - jump1Position;
        }
    }

    public void compileReturnStatement(ASTReturnStatement tree, CLCompileContext context) throws CLCompileError {
        this.compileExpression(tree.getValue(), context);
        CLIRReturn ir = new CLIRReturn();
        context.addIr(ir);
    }

    public void compileForStatement(ASTForStatement tree, CLCompileContext context) throws CLCompileError {
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

    public void compileForDeclStatement(ASTForDeclStatement tree, CLCompileContext context) throws CLCompileError {
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

    public void compileDataStatement(ASTDataStatement tree, CLCompileContext context) throws CLCompileError {
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

    public void compileContinueStatement(ASTContinueStatement tree, CLCompileContext context) throws CLCompileError {
        if (tree.getLabel() == null) {
            context.addContinue(null);
        } else {
            context.addContinue(tree.getLabel().getText());
        }
    }

    public void compileCompoundStatement(ASTCompoundStatement tree, CLCompileContext context) throws CLCompileError {
        for (ASTStatement stat : tree.getItems()) {
            compileStatement(stat, context);
        }
    }

    public void compileBreakStatement(ASTBreakStatement tree, CLCompileContext context) throws CLCompileError {
        if (tree.getLabel() == null) {
            context.addBreak(null);
        } else {
            context.addBreak(tree.getLabel().getText());
        }
    }

    // program unit
    public void compileProgram(ASTProgram tree, CLCompileContext context) throws CLCompileError {
        for (ASTProgramUnit u : tree.getProgramUnits()) {
            compileProgramUnit(u, context);
        }
    }

    public void compileProgramUnit(ASTProgramUnit tree, CLCompileContext context) throws CLCompileError {
        if (tree instanceof ASTFunctionDefinition) {
            compileFunctionDefinition((ASTFunctionDefinition) tree, context);
        } else if (tree instanceof ASTRootStatement) {
            compileStatement(((ASTRootStatement) tree).getStatement(), context);
        } else if (tree instanceof ASTImport) {
            compileImport((ASTImport) tree, context);
        } else if (tree instanceof ASTExport) {
            compileExport((ASTExport) tree, context);
        } else if (tree instanceof ASTUsingStatement) {
            compileUsingStatement((ASTUsingStatement) tree, context);
        }
    }

    public void compileUsingStatement(ASTUsingStatement tree, CLCompileContext context) throws CLCompileError {
        int nameIndex = context.addName(tree.getName());
        int pathIndex = context.addConstant(new CLString(tree.getPath()));
        int scopeIndex = -1;
        if (tree.getScope() != null) {
            scopeIndex = context.addName(tree.getScope());
        }
        int aliasIndex = -1;
        if (tree.getAlias() != null) {
            aliasIndex = context.addName(tree.getAlias());
        }

        CLIRUsing using = new CLIRUsing(aliasIndex, scopeIndex, nameIndex, pathIndex);
        context.addIr(using);
    }

    public void compileImport(ASTImport tree, CLCompileContext context) throws CLCompileError {
        String file = tree.getPath().getText();
        file = file.substring(1, file.length() - 1);
        int index = context.addStringLiteral(file);

        context.addIr(new CLIRLoadConst(index));
        context.addIr(new CLIRCallFile());

        if (tree.getAs() == null) {
            String[] temp = file.split("[/:]");
            String defaultName = temp[temp.length - 1];
            temp = defaultName.split("\\.");
            defaultName = temp[0];
            int defaultNameIndex = context.addName(defaultName);
            context.addIr(new CLIRStoreName(defaultNameIndex));

            context.addLocalName(defaultName);
        } else {
            String name = tree.getAs().getText();
//            name = name.substring(1, name.length() - 1);
            int nameIndex = context.addName(name);
            context.addIr(new CLIRStoreName(nameIndex));

            context.addLocalName(name);
        }
    }

    public void compileExport(ASTExport tree, CLCompileContext context) throws CLCompileError {
        String name = tree.getName().getText();
        int nameIndex = context.addStringLiteral(name);

        context.addIr(new CLIRLoadConst(nameIndex));

        this.compileExpression(tree.getExpression(), context);

        context.addIr(new CLIRExport());
    }

    public void compileFunctionDefinition(ASTFunctionDefinition tree, CLCompileContext context) throws CLCompileError {
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
