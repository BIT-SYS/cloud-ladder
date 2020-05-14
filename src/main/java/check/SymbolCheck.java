package check;

import ast.*;
import symboltable.*;

import java.util.IdentityHashMap;
import java.util.UUID;

import static util.Error.debugSymbolCheck;
import static util.Error.die;
import static util.Type.getType;

public class SymbolCheck extends ASTBaseListener {

    IdentityHashMap<Node, Scope> scopes = new IdentityHashMap<>();

    GlobalScope globals;
    Scope currentScope; // define symbols in this scope
    LoopWatcher loopWatcher = new LoopWatcher();

    private void pushScope(Node ctx, Scope localScope) {
        scopes.put(ctx, localScope);
        currentScope = localScope;
    }

    private void popScope() {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterProgram(Program ctx) {
        globals = new GlobalScope(new PredefinedScope(null));
        currentScope = globals;
        if (debugSymbolCheck) {
            System.out.println(">>>>> enter program");
            System.out.println("stdlib: " + currentScope.getEnclosingScope());
        }
        ctx.scope = globals;
    }

    @Override
    public void exitProgram(Program ctx) {
        if (debugSymbolCheck) {
            System.out.println("<<<<< exit program:");
            System.out.println(globals);
        }
    }

    @Override
    public void enterProcedureDefinition(ProcedureDefinition ctx) {
        String name = ctx.id.name;
        String typeType = ctx.returnType;
        Type type = getType(typeType);

        if (debugSymbolCheck) {
            System.out.println(">>>>> enter procedure " + name);
        }

        ProcedureSymbol procedureSymbol = new ProcedureSymbol(name, type, currentScope);
//        currentScope.define(procedureSymbol); 生成signature之后再define
        pushScope(ctx, procedureSymbol);

        ctx.scope = currentScope;
        ctx.evalType = type;
        ctx.symbol = procedureSymbol;
    }

    @Override
    public void exitProcedureDefinition(ProcedureDefinition ctx) {
        exitBlockKai("<<<<< exit procedure " + ctx.id.name + ":");
        currentScope.define(ctx.symbol);
    }

    @Override
    public void enterLambdaExpression(LambdaExpression ctx) {
        String retType = ctx.retType;
        String name = "^\\" + UUID.randomUUID().toString().replace("-", "");
        Type type = getType(retType);

        if (debugSymbolCheck) {
            System.out.println(">>>>> enter lambda " + name);

        }

        ProcedureSymbol procedureSymbol = new ProcedureSymbol(name, type, currentScope);
//        currentScope.define(procedureSymbol);
        pushScope(ctx, procedureSymbol);

        ctx.scope = currentScope;
        ctx.evalType = type;
        ctx.symbol = procedureSymbol;
    }

    @Override
    public void exitLambdaExpression(LambdaExpression ctx) {
        exitBlockKai("<<<<< exit lambda:");
        //要不要删除这个匿名函数的symbol呢？应该不要
        currentScope.define(ctx.symbol);
    }

    @Override
    public void exitCallExpression(CallExpression ctx) {
        // 为了TypeCheck里检查参数类型对不对
        ctx.scope = currentScope;
    }

    // 貌似block是匿名函数独占的子节点了
    // 好日子来临力！现在for、if、else也改回来啦！
    @Override
    public void enterBlock(Block ctx) {
        if (debugSymbolCheck) {
            System.out.println(">>>>> enter block(prog/lamb/proc/for/while/if-else):");
        }
        LocalScope localScope = new LocalScope(currentScope);
        pushScope(ctx, localScope);

        ctx.scope = currentScope;
    }

    @Override
    public void exitBlock(Block ctx) {
        exitBlockKai("<<<<< exit block(porg/lamb/proc/for/while/if-else):");
    }

    private void enterBlockKai(Node ctx) {
        LocalScope localScope = new LocalScope(currentScope);
        pushScope(ctx, localScope);
    }

    private void exitBlockKai(String exitMessage) {
        if (debugSymbolCheck) {
            System.out.println(exitMessage);
            System.out.println(currentScope);
        }
        popScope();
    }

    @Override
    public void enterForBlock(ForBlock ctx) {
        if (debugSymbolCheck) {
            System.out.println(">>>>> enter for:");
        }
        if (null != ctx.iter_type) {
            enterBlockKai(ctx);
            // 创建新变量
            String name = ctx.for_id.name;
            Type type = getType(ctx.iter_type);
            VariableSymbol variableSymbol = new VariableSymbol(name, type);
            currentScope.define(variableSymbol);
        }
        loopWatcher.pushLoop();
    }

    @Override
    public void exitForBlock(ForBlock ctx) {
        if (null != ctx.iter_type) {
            // 创建了新变量就要弹出作用域
            exitBlockKai("<<<<< exit for:");
        } else {
            if (debugSymbolCheck) {
                System.out.println("<<<<< exit for:");
            }
        }
        loopWatcher.popLoop();
    }

    @Override
    public void enterWhileBlock(WhileBlock ctx) {
        if (debugSymbolCheck) {
            System.out.println(">>>>> enter while:");
        }
        enterBlockKai(ctx);
        loopWatcher.pushLoop();
    }

    @Override
    public void exitWhileBlock(WhileBlock ctx) {
        if (debugSymbolCheck) {
            exitBlockKai("<<<<< exit while:");
        }
        loopWatcher.popLoop();
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
        // 和exitParameter同样的原因改成进入时就定义
        String name = ctx.id.name;
        if (null != currentScope.resolveWithin(name)) {
            die("Symbol Check: AST.VariableDeclaration", "Variable " + name + " has been declared!");
        }
        Type type = getType(ctx.type);
        VariableSymbol variableSymbol = new VariableSymbol(name, type);
        currentScope.define(variableSymbol);

        ctx.scope = currentScope;
    }

    @Override
    public void enterParameter(Parameter ctx) {
        // 定义参数，原本是exit时做的。
        // 但现在没有primary，还没exitParameter就会触发exitIdentifier
        String name = ctx.id.name;
        Type type = getType(ctx.type);
        VariableSymbol variableSymbol = new VariableSymbol(name, type);
        currentScope.define(variableSymbol);
    }

    @Override
    public void exitParameterList(ParameterList ctx) {
        ctx.scope = currentScope;
    }

    // 👇 验证变量、函数是否存在

    @Override
    public void exitIdentifier(Identifier ctx) {
        String identifier = ctx.name;
        Symbol symbol = currentScope.resolve(identifier);
        if (null == symbol) {
            if (currentScope.hasProcedure(identifier)) {
                ctx.scope = currentScope;
                ctx.isProc = true;
            } else {
                die("Symbol Check: AST.Identifier", "<variable " + identifier + "> not found in " + currentScope.getScopeName());
            }
        } else {
            ctx.symbol = symbol;
        }
    }

    @Override
    public void enterCallExpression(CallExpression ctx) {
        String identifier = ctx.callee.name;
        if (currentScope.hasProcedure(identifier)) {
            ctx.scope = currentScope;
        } else {
            die("Symbol Check: AST.CallExpression", "<function " + identifier + "> not found in " + currentScope.getScopeName());
        }
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
                die("Symbol Check: LoopWatcher", "<break> not in a loop");
            }
        }

        public void addContinue() {
            if (loopCounter < 1) {
                die("Symbol Check: LoopWatcher", "<continue> not in a loop");
            }
        }
    }
}
