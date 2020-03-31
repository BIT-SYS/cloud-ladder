import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;
import symboltable.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class SymbolCheck {

    public static void main(String[] args) throws Exception {
//        String inputFile = null;
////        if ( args.length>0 ) inputFile = args[0];
////        InputStream is = System.in;
////        if ( inputFile!=null ) {
////            is = new FileInputStream(inputFile);
////        }

        InputStream is = new FileInputStream("examples/leap-year.cl");

        ANTLRInputStream input = new ANTLRInputStream(is);
        CLParserLexer lexer = new CLParserLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParserParser parser = new CLParserParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        SymbolChecker symbolChecker = new SymbolChecker();
        walker.walk(symbolChecker, tree);
    }

    public static class SymbolChecker extends CLParserBaseListener {

        ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
        GlobalScope globals;
        Scope currentScope; // define symbols in this scope
        LoopWatcher loopWatcher = new LoopWatcher();

        private void pushScope(RuleContext ctx, Scope localScope) {
            scopes.put(ctx, localScope);
            currentScope = localScope;
        }

        private void popScope() {
            currentScope = currentScope.getEnclosingScope();
        }

        @Override
        public void enterProgram(CLParserParser.ProgramContext ctx) {
            globals = new GlobalScope(null);
            currentScope = globals;
            System.out.println(">>>>> enter program");
        }

        @Override
        public void exitProgram(CLParserParser.ProgramContext ctx) {
            System.out.println("<<<<< exit program:");
            System.out.println(globals);
        }

        @Override
        public void enterProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {
            String name = ctx.IDENTIFIER().getText();
            String typeType = ctx.typeType().getText();
            Symbol.Type type = getType(typeType);

            System.out.println(">>>>> enter procedure " + name + ":\n" + ctx.getText());

            ProcedureSymbol procedureSymbol = new ProcedureSymbol(name, type, currentScope);
            currentScope.define(procedureSymbol);
            pushScope(ctx, procedureSymbol);
        }
        //todo lambda name, retType??

        @Override
        public void exitProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {
            System.out.println("<<<<< exit procedure " + ctx.IDENTIFIER() + ":");
            System.out.println(currentScope);
            popScope();
        }

        @Override
        public void enterBlock(CLParserParser.BlockContext ctx) {
            System.out.println(">>>>> enter block:\n" + ctx.getText());
            LocalScope localScope = new LocalScope(currentScope);
            pushScope(ctx, localScope);
        }

        @Override
        public void exitBlock(CLParserParser.BlockContext ctx) {
            System.out.println("<<<<< exit block:");
            System.out.println(ctx.getText());
            System.out.println(currentScope);
            popScope();
        }

        //todo 给 for 换名字，F 应该起了别名，就不用 enter/exitStatement 了
        @Override
        public void enterStatement(CLParserParser.StatementContext ctx) {
            if (null != ctx.FOR() && null != ctx.typeType()) {
                // for 创建了新变量
                LocalScope forScope = new LocalScope(currentScope);
                pushScope(ctx, forScope);

                String name = ctx.IDENTIFIER().getText();
                Symbol.Type type = getType(ctx.typeType().getText());
                VariableSymbol variableSymbol = new VariableSymbol(name, type);
                currentScope.define(variableSymbol);
            }
            if (null != ctx.FOR() || null != ctx.WHILE()) {
                loopWatcher.pushLoop();
            }
        }

        @Override
        public void exitStatement(CLParserParser.StatementContext ctx) {
            if (null != ctx.FOR() && null != ctx.typeType()) {
                System.out.println("<<<<< exit for:");
                System.out.println(currentScope);
                popScope();
            }
            if (null != ctx.BREAK()) {
                loopWatcher.addBreak();
            }
            if (null != ctx.CONTINUE()) {
                loopWatcher.addContinue();
            }
            if (null != ctx.FOR() || null != ctx.WHILE()) {
                loopWatcher.popLoop();
            }
        }

        @Override
        public void exitVariableDeclaration(CLParserParser.VariableDeclarationContext ctx) {
            String name = ctx.IDENTIFIER().getText();
            Symbol.Type type = getType(ctx.typeType().getText());
            VariableSymbol variableSymbol = new VariableSymbol(name, type);
            currentScope.define(variableSymbol);
        }

        @Override
        public void exitParameter(CLParserParser.ParameterContext ctx) {
            String name = ctx.IDENTIFIER().getText();
            Symbol.Type type = getType(ctx.typeType().getText());
            VariableSymbol variableSymbol = new VariableSymbol(name, type);
            currentScope.define(variableSymbol);
        }

        // 👇 验证变量、函数是否存在

        @Override
        public void exitPrimary(CLParserParser.PrimaryContext ctx) {
            if (null != ctx.IDENTIFIER()) {
                String identifier = ctx.IDENTIFIER().getText();
                if (null == currentScope.resolve(identifier)) {
                    System.err.println("<variable " + identifier + "> not found in " + currentScope.getScopeName());
                }
            }
        }

        @Override
        public void exitProcedureCall(CLParserParser.ProcedureCallContext ctx) {
            String identifier = ctx.IDENTIFIER().getText();
            if (null == currentScope.resolve(identifier)) {
                System.err.println("<procedure " + identifier + "> not found in " + currentScope.getScopeName());
            }
        }

        //todo lambda parameter

        private Symbol.Type getType(String typeType) {
            //todo List<xxx>那些
            switch (typeType) {
                case "Number":
                    return Symbol.Type.Number;
                case "Image":
                    return Symbol.Type.Image;
                case "Audio":
                    return Symbol.Type.Audio;
                case "Video":
                    return Symbol.Type.Video;
                case "String":
                    return Symbol.Type.String;
                case "Boolean":
                    return Symbol.Type.Boolean;
            }
            return Symbol.Type.INVALID;
        }
    }

    public static class LoopWatcher {
        int loopCounter = 0;

        public void pushLoop() {
            loopCounter ++;
        }

        public void popLoop() {
            loopCounter --;
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
