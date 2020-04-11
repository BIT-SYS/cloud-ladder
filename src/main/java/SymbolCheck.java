import symboltable.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.IdentityHashMap;
import java.util.Random;

public class SymbolCheck extends ASTBaseListener {

    IdentityHashMap<Node, Scope> scopes = new IdentityHashMap<>();

    GlobalScope globals;
    Scope currentScope; // define symbols in this scope
    LoopWatcher loopWatcher = new LoopWatcher();

    Random random = new Random();

    private void pushScope(Node ctx, Scope localScope) {
        scopes.put(ctx, localScope);
        currentScope = localScope;
    }

    private void popScope() {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterProgram(Program ctx) {
        globals = new GlobalScope(null);
        currentScope = globals;
        System.out.println(">>>>> enter program");
    }

    @Override
    public void exitProgram(Program ctx) {
        System.out.println("<<<<< exit program:");
        System.out.println(globals);
    }

    @Override
    public void enterProcedureDefinition(ProcedureDefinition ctx) {
        String name = ctx.id.name;
        String typeType = ctx.returnType;
        Symbol.Type type = getType(typeType);

        System.out.println(">>>>> enter procedure " + name);

        ProcedureSymbol procedureSymbol = new ProcedureSymbol(name, type, currentScope);
        currentScope.define(procedureSymbol);
        pushScope(ctx, procedureSymbol);
    }

    @Override
    public void exitProcedureDefinition(ProcedureDefinition ctx) {
        System.out.println("<<<<< exit procedure " + ctx.id.name + ":");
        System.out.println(currentScope);
        popScope();
    }

    @Override
    public void enterLambdaExpression(LambdaExpression ctx) {
        String retType = ctx.retType;
        byte[] chaos = new byte[8];
        random.nextBytes(chaos);
        String name = "^\\-" + new String(chaos, StandardCharsets.US_ASCII) + "-" +retType;
        Symbol.Type type = getType(retType);

        System.out.println(">>>>> enter lambda " + name);

        ProcedureSymbol procedureSymbol = new ProcedureSymbol(name, type, currentScope);
        currentScope.define(procedureSymbol);
        pushScope(ctx, procedureSymbol);
    }

    @Override
    public void exitLambdaExpression(LambdaExpression node) {
        System.out.println("<<<<< exit lambda:");
        System.out.println(currentScope);
        popScope();
        //要不要删除这个匿名函数的symbol呢？应该不要
    }

    // 貌似block是匿名函数独占的子节点了
    @Override
    public void enterBlock(Block ctx) {
        System.out.println(">>>>> enter block(lambda):");
        LocalScope localScope = new LocalScope(currentScope);
        pushScope(ctx, localScope);
    }

    @Override
    public void exitBlock(Block ctx) {
        System.out.println("<<<<< exit block(lambda):");
        System.out.println(currentScope);
        popScope();
    }

    private void enterBlockKai(Block ctx) {
        LocalScope localScope = new LocalScope(currentScope);
        pushScope(ctx, localScope);
    }

    private void exitBlockKai() {
        System.out.println(currentScope);
        popScope();
    }

    @Override
    public void enterForBlock(ForBlock ctx) {
        System.out.println(">>>>> enter for:");
        //现在必新建作用域+变量了
        enterBlockKai(ctx);

        String name = ctx.for_id.name;
        Symbol.Type type = getType(ctx.iter_type);
        VariableSymbol variableSymbol = new VariableSymbol(name, type);
        currentScope.define(variableSymbol);

        loopWatcher.pushLoop();
    }

    @Override
    public void exitForBlock(ForBlock ctx) {
        System.out.println("<<<<< exit for:");
        exitBlockKai();
        loopWatcher.popLoop();
    }

    @Override
    public void enterWhileBlock(WhileBlock ctx) {
        System.out.println(">>>>> enter while:");
        enterBlockKai(ctx);
        loopWatcher.pushLoop();
    }

    @Override
    public void exitWhileBlock(WhileBlock ctx) {
        System.out.println("<<<<< exit while:");
        exitBlockKai();
        loopWatcher.popLoop();
    }

    @Override
    public void enterIfBlock(IfBlock ctx) {
        System.out.println(">>>>> enter block(if):");
        enterBlockKai(ctx);
    }

    @Override
    public void exitIfBlock(IfBlock ctx) {
        System.out.println("<<<<< exit block(if):");
        exitBlockKai();
    }

    @Override
    public void enterElifBlock(ElifBlock ctx) {
        System.out.println(">>>>> enter block(elif):");
        enterBlockKai(ctx);
    }

    @Override
    public void exitElifBlock(ElifBlock ctx) {
        System.out.println("<<<<< exit block(elif):");
        exitBlockKai();
    }

    @Override
    public void enterElseBlock(ElseBlock ctx) {
        System.out.println(">>>>> enter block(else):");
        enterBlockKai(ctx);
    }

    @Override
    public void exitElseBlock(ElseBlock ctx) {
        System.out.println("<<<<< exit block(else):");
        exitBlockKai();
    }

    @Override
    public void exitBreak(Break ctx) {
        loopWatcher.addBreak();
    }

    @Override
    public void exitContinue(Continue ctx) {
        loopWatcher.addContinue();
    }

    @Override
    public void enterVariableDeclaration(VariableDeclaration ctx) {
        String name = ctx.id.name;
        Symbol.Type type = getType(ctx.type);
        VariableSymbol variableSymbol = new VariableSymbol(name, type);
        currentScope.define(variableSymbol);
    }

    @Override
    public void enterParameter(Parameter node) {
        super.enterParameter(node);
    }

    @Override
    public void exitParameter(Parameter ctx) {
        String name = ctx.id.name;
        Symbol.Type type = getType(ctx.type);
        VariableSymbol variableSymbol = new VariableSymbol(name, type);
        currentScope.define(variableSymbol);
    }

    // 👇 验证变量、函数是否存在

    @Override
    public void exitIdentifier(Identifier ctx) {
        String identifier = ctx.name;
        if (null == currentScope.resolve(identifier)) {
            System.err.println("<variable " + identifier + "> not found in " + currentScope.getScopeName());
        }
    }

    @Override
    public void enterCallExpression(CallExpression ctx) {
        String identifier = ctx.callee.name;
        if (null == currentScope.resolve(identifier)) {
            System.err.println("<function " + identifier + "> not found in " + currentScope.getScopeName());
        }
    }


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

    public static class LoopWatcher {
        int loopCounter = 0;

        public void pushLoop() {
            loopCounter++;
        }

        public void popLoop() {
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
