import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;
import symboltable.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class SymbolCheck {

    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) {
            is = new FileInputStream(inputFile);
        }

        ANTLRInputStream input = new ANTLRInputStream(is);
        CLParserLexer lexer = new CLParserLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParserParser parser = new CLParserParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        // show tree in text form
        System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        SymbolChecker symbolChecker = new SymbolChecker();
        walker.walk(symbolChecker, tree);
    }

    public static class SymbolChecker extends CLParserBaseListener {

        ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
        GlobalScope globals;
        Scope currentScope; // define symbols in this scope

        @Override
        public void enterProgram(CLParserParser.ProgramContext ctx) {
            globals = new GlobalScope(null);
            currentScope = globals;
        }

        @Override
        public void exitProgram(CLParserParser.ProgramContext ctx) {
            System.out.println("-----final:");
            System.out.println(globals);
        }

        @Override
        public void enterProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {
            String name = ctx.IDENTIFIER().getText();
            String typeType = ctx.typeType().getText();
            Symbol.Type type = getType(typeType);

            ProcedureSymbol procedureSymbol = new ProcedureSymbol(name, type, currentScope);
            currentScope.define(procedureSymbol);
            pushScope(ctx, procedureSymbol);
        }
        //todo lambda name, retType??

        @Override
        public void exitProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {
            System.out.println("-----procedure " + ctx.IDENTIFIER() + ":");
            System.out.println(currentScope);
            popScope();
        }

        private void popScope() {
            currentScope = currentScope.getEnclosingScope();
        }

        @Override
        public void enterBlock(CLParserParser.BlockContext ctx) {
            System.out.println("enter:" + ctx.getText());
            LocalScope localScope = new LocalScope(currentScope);
            pushScope(ctx, localScope);
        }

        private void pushScope(RuleContext ctx, Scope localScope) {
            scopes.put(ctx, localScope);
            currentScope = localScope;
        }

        @Override
        public void exitBlock(CLParserParser.BlockContext ctx) {
            System.out.println("-----some block:");
            System.out.println(ctx.getText());
            System.out.println(currentScope);
            popScope();
        }

        //todo for Number i in ...

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

        //todo lambda parameter

        private Symbol.Type getType(String typeType) {
            //todo List<xxx>那些
            switch (typeType) {
                case "Number":
                    return Symbol.Type.Number;
                case "Image":
                    return Symbol.Type.Image;
                case "String":
                    return Symbol.Type.String;
            }
            return Symbol.Type.INVALID;
        }
    }
}
