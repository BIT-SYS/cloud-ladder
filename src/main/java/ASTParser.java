import IR.*;
import symboltable.Scope;
import symboltable.SimpleType;
import symboltable.Symbol;
import symboltable.Type;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Label {
  static int label = 0;
}

class Temp extends ExpressionNode {
  static int indexOfAll = 0;
  int index;

  Temp() {
    index = indexOfAll++;
  }

  @Override
  public String toString() {
    return "t" + index;
  }
}

class ASTWalker {
  public void walk(ASTBaseListener astListener, Node node) {
//    ASTParser.printMethodInfo(node);
    this.enterRule(astListener, node);
    try {

      node.getChildren().forEach(
              n -> {
                if (n == null) {
                  return;
                }
                walk(astListener, n);
              }
      );
    } catch (NullPointerException e) {

      e.printStackTrace();
      System.out.println(node.getClass().getName());
      System.exit(1);
    }
    this.exitRule(astListener, node);
  }

  protected void enterRule(ASTBaseListener astListener, Node node) {

    Method enterMethod = this.getMethod("enter", astListener, node);
    try {
      enterMethod.invoke(astListener, node);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  protected void exitRule(ASTBaseListener astListener, Node node) {
    Method enterMethod = this.getMethod("exit", astListener, node);
    try {
      enterMethod.invoke(astListener, node);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private Method getMethod(String prefix, ASTBaseListener astListener, Node node) {
    String nodeClassName = node.getClass().getName();
    String methodName = prefix + nodeClassName;
    Class<?>[] paramTypes = {node.getClass()};
//    astListener.getClass().getDeclaredMethod()
    try {
      return astListener.getClass().getMethod(methodName, paramTypes);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    // never touched
    return null;
  }
}

interface HaveConditionAndBlock {
  ExpressionNode getCondition();

  Block getBlock();
}

/**
 * Base Node.
 */
//interface Node {
//  List<Node> getChildren();
//}

abstract class Node {
  // ScopePointer
  public Scope scope;
  public Symbol symbol;
  public Type evalType;
  static public IR ir;

  //
  int newLabel() {
    return ++Label.label;
  }


  void emit(String s) {
    System.out.println("\t" + s);
  }

  public String gen(int before, int after) {
    System.out.println(this.getClass().getName());
    return "";
  }

  public void register(IRNode n) {
    ir.emit(n);
  }

  abstract List<Node> getChildren();
}

class ScopePointer {
}

class Program extends Node {
  public Block statements;

  Program(Block bl) {
    statements = bl;
    ir = new IR();
  }

  @Override
  public String gen(int before, int after) {
    // never used params
    // TODO
    statements.gen(0, 0);
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(statements);
    }};
  }
}

class RangeListInitializer extends ExpressionNode {
  public ExpressionNode start;
  public ExpressionNode end;
  public boolean exclusiveEnd;

  @Override
  public ExpressionNode reduce() {
    ExpressionNode _start = start.reduce();
    ExpressionNode _end = end.reduce();
    Temp t = new Temp();
    ir.emit(new BuildListIR(t, _start, _end));
    return t;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public String gen(int before, int after) {
    return gen().toString();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(start);
      add(end);
    }};
  }
}

class Assign extends ExpressionNode {
  public Identifier lvalue;
  public ExpressionNode rvalue;

  Assign(String lvalue, ExpressionNode rvalue) {
    this.lvalue = new Identifier(lvalue);
    this.rvalue = rvalue;
  }

  @Override
  public String gen(int before, int after) {
    String l = lvalue.gen().toString();
    String r = rvalue.gen().toString();
    ir.emit(new AssignIR(l, r));
    emit(lvalue.gen() + " = " + rvalue.gen());
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(lvalue);
      add(rvalue);
    }};
  }
}

class ValuesListInitializer extends ExpressionNode {
  public List<ExpressionNode> values;

  @Override
  public List<Node> getChildren() {
    return values.stream().map(en -> (Node) en).collect(Collectors.toList());
  }
}


class LambdaExpression extends ExpressionNode {

  public ParameterList parameters;
  public String retType;
  public Block body;

  @Override
  public ExpressionNode reduce() {
    Temp t = new Temp();
    ir.emit(new LazyExecutionStartIR(t.toString(), retType, parameters.parameters.stream().map(Objects::toString).collect(Collectors.toList())));
    int before = newLabel();
    int after = newLabel();
    ir.emitLabel(before);
    body.gen(before, after);
    ir.emitLabel(after);
    ir.emit(new LazyExecutionEndIR());
    return t;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public String gen(int before, int after) {

    return gen().toString();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {
      {
        add(parameters);
        add(body);
      }
    };
  }
}

class Break extends Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

  @Override
  public String gen(int before, int after) {
    ir.emit(new BreakIR(ir.getLabel(after)));
    emit("goto L" + after);
    return "";
  }
}

class Continue extends Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

  @Override
  public String gen(int before, int after) {
    ir.emit(new ContinueIR(ir.getLabel(before)));
    emit("goto L" + before);
    return "";
  }
}

// so what's the difference between Block and Program
class Block extends Node {

  public List<Node> statements;

  Block() {
    statements = new ArrayList<>();
  }

  @Override
  public String gen(int before, int after) {
    for (Node statement : statements) {
      statement.gen(before, after);
    }
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(statements);
  }
}

class IfElseBlock extends Node {
  public List<Node> ifelses = new ArrayList<>();

  @Override
  public String gen(int before, int after) {
    // TODO 作用域 和 标签的冲突
    int last = newLabel();
    for (Node n : ifelses) {
      int middle = newLabel();
      if (n instanceof HaveConditionAndBlock) {
        HaveConditionAndBlock n1 = (HaveConditionAndBlock) n;
        String condition = n1.getCondition().gen(0, 0);
        JumpIfNotTrueIR j = new JumpIfNotTrueIR(condition, ir.getLabel(middle));
        ir.emit(j);
        n1.getBlock().gen(before, after);
        ir.emit(new JumpIR(ir.getLabel(last)));
      } else {
        System.err.println("ERROR AST");
      }
      ir.emitLabel(middle);
    }
    ir.emitLabel(last);
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(ifelses);
  }
}

class IfBlock extends Node implements HaveConditionAndBlock {
  public ExpressionNode condition;
  // may be null
  public Block statements;

  IfBlock(Block bl) {
    this.statements = bl;
  }


  @Override
  public String gen(int before, int after) {
    int label = newLabel();
    String reduced_condition = condition.gen(before, label);
    ir.emitLabel(label);
    int blockAfter = newLabel();
    ir.emit(new JumpIfNotTrueIR(reduced_condition, ir.getLabel(blockAfter)));
    statements.gen(label, after);
    ir.emit(new JumpIR(ir.getLabel(after)));
    ir.emitLabel(blockAfter);
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(condition);
      add(statements);
    }};
  }

  @Override
  public ExpressionNode getCondition() {
    return condition;
  }

  @Override
  public Block getBlock() {
    return statements;
  }
}

class ElifBlock extends Node implements HaveConditionAndBlock {
  public ExpressionNode condition;
  public Block statements;

  ElifBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public String gen(int before, int after) {
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {
      {
        add(condition);
        add(statements);
      }
    };

  }

  @Override
  public ExpressionNode getCondition() {
    return condition;
  }

  @Override
  public Block getBlock() {
    return statements;
  }
}

class ElseBlock extends Node implements HaveConditionAndBlock {
  public Block statements;

  ElseBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public String gen(int before, int after) {
    statements.gen(0, after);
    return "";
  }

  @Override
  List<Node> getChildren() {
    return new ArrayList<>() {{
      add(statements);
    }};
  }

  @Override
  public ExpressionNode getCondition() {
    return new Literal("true", new SimpleType("Boolean"));
  }

  @Override
  public Block getBlock() {
    return statements;
  }
}

class WhileBlock extends Node {
  public ExpressionNode condition;
  public Block statements;

  WhileBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public String gen(int before, int after) {
    before = newLabel();
    after = newLabel();

    ir.emitLabel(before);
    String reduced_condition = condition.gen(0, 0);
    JumpIfNotTrueIR jintir = new JumpIfNotTrueIR(reduced_condition, ir.getLabel(after));
    ir.emit(jintir);
    statements.gen(before, after);

    ir.emit(new JumpIR(ir.getLabel(before)));
    ir.emitLabel(after);
    return "";

  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(condition);
      add(statements);
    }};
  }
}

class ForBlock extends Node {
  public Identifier for_id;
  public ExpressionNode for_expr;
  public String iter_type;
  public Block statements;

  ForBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public String gen(int before, int after) {
    before = newLabel();
    after = newLabel();
    String expr = for_expr.gen(0, 0);
    Temp t = new Temp();
    Temp len = new Temp();
    // get expr len
    ir.emit(new CallExprIR("size", expr, len, new ArrayList<>()));

    ir.emit(new VariableDeclarationIR(t,new SimpleType("Number"),new Literal("0", new SimpleType("Number"))));
    ir.emitLabel(before);
    // if false then jump
    ir.emit(new CallExprIR("get", expr, for_id, new ArrayList<>() {{
      add(t);
    }}));
    LogicExpression condition = new LogicExpression(t, len, "<");
    String condition_reduced = condition.gen(0, 0);
    ir.emit(new JumpIfNotTrueIR(condition_reduced, ir.getLabel(after)));

    // execuete
    statements.gen(before, after);


    // +1 and jump
    BinaryExpression b = new BinaryExpression(t, new Literal("1", new SimpleType("Number")), "+");
    String b_reduced = b.gen(0, 0);
    ir.emit(new AssignIR(t, b_reduced));
    ir.emit(new JumpIR(ir.getLabel(before)));
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(for_id);
      add(for_expr);
      add(statements);
    }};
  }
}

class Parameter extends Node {
  public String type;
  public Identifier id;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(id);
    }};
  }

  @Override
  public String toString() {
    return String.format("%s %s", type, id.toString());
  }
}

class ParameterList extends Node {
  public List<Parameter> parameters;

  ParameterList(List<Parameter> ps) {
    this.parameters = ps;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(parameters);
  }
}

class IndexExpression extends BinaryExpression {

  IndexExpression(ExpressionNode left, ExpressionNode right, String op) {
    super(left, right, op);
  }

  @Override
  public ExpressionNode gen() {
    ExpressionNode l = left.reduce();
    ExpressionNode r = right.reduce();
    Temp t = new Temp();
    ir.emit(new CallExprIR("get",l.toString(),t,new ArrayList<>(){{add(r);}}));
    return t;
  }

  @Override
  public String gen(int before, int after) {
    return gen().toString();
  }
}

class ProcedureDefinition extends Node {
  public ParameterList parameters;
  public String returnType;
  // function name
  public Identifier id;
  public Block body;

  @Override
  public String gen(int before, int after) {
    ir.emit(new LazyExecutionStartIR(id.toString(), returnType, parameters.parameters.stream().map(Objects::toString).collect(Collectors.toList())));

    body.gen(0, 0);

    ir.emit(new LazyExecutionEndIR());
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(parameters);
      add(body);
    }};
  }
}


class VariableDeclaration extends Node {
  public String type;
  public Identifier id;
  public ExpressionNode expr;

  @Override
  public String gen(int before, int after) {
    System.out.println(id);
    System.out.println(id.toString());
    // ! ERROR: warning: TODO
    Symbol s = scope.resolve(id.toString());
    VariableDeclarationIR vdir = new VariableDeclarationIR(id.gen(),s.type, expr.gen());
    register(vdir);
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(id);
      add(expr);
    }};
  }
}

class ExpressionNode extends Node {

  public ExpressionNode reduce() {
    return this;
  }

  public ExpressionNode gen() {
    System.out.println("unimplemented gen: " + this.getClass().getName());
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }

}

class CallExpression extends ExpressionNode {
  // identifier or MemberExpression
  public FunctionIdentifier callee;
  public List<ExpressionNode> arguments;


  @Override
  public ExpressionNode reduce() {
    return reduce(null);
  }

  public ExpressionNode reduce(ExpressionNode caller) {
    List<ExpressionNode> args = arguments.stream().map(ExpressionNode::reduce).collect(Collectors.toCollection(ArrayList::new));
    Temp t = new Temp();
    CallExprIR ceir = new CallExprIR(callee.toString(), caller, t, args.stream().map(i -> (Object) i).collect(Collectors.toList()));
    ir.emit(ceir);
    return t;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public String gen(int before, int after) {
    return gen().toString();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(callee);
      addAll(arguments);
    }};
  }
}

class MemberExpression extends ExpressionNode {
  // TODO 求值顺序
  public ExpressionNode object;
  public ExpressionNode property;

  @Override
  public ExpressionNode reduce() {
    ExpressionNode object_reduced = object.reduce();
    Temp temp = new Temp();
    // TODO call
    if (property instanceof CallExpression) {
      CallExpression proc = (CallExpression) property;
      proc.reduce(object_reduced);
//      CallExprIR ceir = new CallExprIR(proc.callee.toString(), object_reduced, temp,
//              proc.arguments.stream().map(e -> (Object) e).collect(Collectors.toList()));
//      ir.emit(ceir);
    }
    return temp;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public String gen(int before, int after) {
    return gen().toString();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {
      {
        add(object);
        add(property);
      }
    };
  }
}

class Identifier extends ExpressionNode {
  public String name;

  Identifier(CLParserParser.IdContext ctx) {
    name = ctx.getText();
  }

  Identifier(String name) {
    this.name = name;
  }

  @Override
  public ExpressionNode gen() {
    return this;
  }

  @Override
  public String gen(int before, int after) {
    return gen().toString();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}

class FunctionIdentifier extends ExpressionNode {

  public String name;

  FunctionIdentifier(CLParserParser.IdContext ctx) {
    name = ctx.getText();
  }

  FunctionIdentifier(String name) {
    this.name = name;
  }

  @Override
  public ExpressionNode reduce() {
    return this;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}

class Literal extends ExpressionNode {

  public String raw;

  Literal(String raw, Type type) {
    this.raw = raw;
    evalType = type;
  }

  @Override
  public ExpressionNode gen() {
    return this;
  }

  @Override
  public String gen(int before, int after) {
    return this.toString();
  }

  @Override
  public String toString() {
    return raw;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}

class ArithmeticExpression extends BinaryExpression {
  ArithmeticExpression(ExpressionNode left, ExpressionNode right, String op) {
    super(left, right, op);
  }

  @Override
  public ExpressionNode gen() {
    return new ArithmeticExpression(left.reduce(), right.reduce(), op);
  }


  @Override
  public String gen(int before, int after) {
    return reduce().toString();
  }

  @Override
  public String toString() {
    return left.toString() + " " + op + " " + right.toString();
  }
}

class LogicExpression extends BinaryExpression {

  LogicExpression(ExpressionNode left, ExpressionNode right, String op) {
    super(left, right, op);
  }

  @Override
  public ExpressionNode gen() {
    return new ArithmeticExpression(left.reduce(), right.reduce(), op);
  }

  @Override
  public String gen(int before, int after) {
    return reduce().toString();
  }

}

class BinaryExpression extends ExpressionNode {
  public ExpressionNode left;

  public String op;
  public ExpressionNode right;


  @Override
  public ExpressionNode reduce() {
    ExpressionNode reducedExpr = gen();
    Temp t = new Temp();
    if (reducedExpr instanceof BinaryExpression) {
      BinaryExpression be = (BinaryExpression) reducedExpr;
      BinaryExprIR beir = new BinaryExprIR(be.op, be.left, be.right, t);
      ir.emit(beir);
    }
    return t;
  }

  BinaryExpression(ExpressionNode left, ExpressionNode right, String op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>() {{
      add(left);
      add(right);
    }};
  }
}


public class ASTParser extends CLParserBaseVisitor<Node> {

  @Override
  public Node visitVariableDecl(CLParserParser.VariableDeclContext ctx) {
    VariableDeclaration vd = new VariableDeclaration();
    String name = ctx.IDENTIFIER().getText();
    String type = ctx.typeType().getText();
    Identifier id = new Identifier(name);
    vd.type = type;
    vd.expr = (ExpressionNode) visit(ctx.expression());
    vd.id = id;
    return vd;
  }

  @Override
  public Node visitIntegerLiteral(CLParserParser.IntegerLiteralContext ctx) {
    return new Literal(ctx.getText(), new SimpleType("Number"));
  }

  @Override
  public Node visitFloatLiteral(CLParserParser.FloatLiteralContext ctx) {
    return new Literal(ctx.getText(), new SimpleType("Number"));
  }

  @Override
  public Node visitString(CLParserParser.StringContext ctx) {
    return new Literal(ctx.getText(), new SimpleType("String"));
  }

  @Override
  public Node visitBool(CLParserParser.BoolContext ctx) {
    return new Literal(ctx.getText(), new SimpleType("Boolean"));
  }

  @Override
  public Node visitRangeListInitializer(CLParserParser.RangeListInitializerContext ctx) {
    RangeListInitializer rli = new RangeListInitializer();
    rli.start = (ExpressionNode) visit(ctx.expression(0));
    rli.end = (ExpressionNode) visit(ctx.expression(1));
    rli.exclusiveEnd = !ctx.op.getText().equals("..");
    return rli;
  }

  @Override
  public Node visitIndex(CLParserParser.IndexContext ctx) {
    return new IndexExpression((ExpressionNode) visit(ctx.expression(0)), (ExpressionNode) visit(ctx.expression(1)), "[]");
  }

  @Override
  public Node visitValuesListInitializer(CLParserParser.ValuesListInitializerContext ctx) {
    ValuesListInitializer vli = new ValuesListInitializer();

    vli.values = ctx.expression().stream().map(e -> (ExpressionNode) visit(e)).collect(Collectors.toCollection(ArrayList::new));

    return vli;
  }

  @Override
  public Node visitAssign(CLParserParser.AssignContext ctx) {
    return new Assign(ctx.assignment().IDENTIFIER().getText(), (ExpressionNode) visit(ctx.assignment().expression()));
  }


  @Override
  public Node visitProcedureDecl(CLParserParser.ProcedureDeclContext ctx) {
    return visit(ctx.procedureDeclaration());
  }

  @Override
  public Node visitBlock(CLParserParser.BlockContext ctx) {

    Block bl = new Block();
    ctx.statement().forEach(
            n -> {
//              System.out.println(n.getText());
              Node ret = visit(n);
              if (ret != null)
                bl.statements.add(ret);
            }
    );

    return bl;
  }

  @Override
  public Node visitIfBlock(CLParserParser.IfBlockContext ctx) {
    IfElseBlock ieb = new IfElseBlock();
    IfBlock ib = new IfBlock((Block) visit(ctx.block(0)));
    ib.condition = (ExpressionNode) visit(ctx.expression(0));
    ieb.ifelses.add(ib);

    int number_of_elif = ctx.ELIF().size();
    int number_of_else = ctx.ELSE() == null ? 0 : 1;

    List<ElifBlock> lib = ctx.block().subList(1, 1 + number_of_elif).stream().map(b -> new ElifBlock((Block) visit(b))).collect(
            Collectors.toCollection(ArrayList::new)
    );
    IntStream.range(0, lib.size()).forEach(i -> lib.get(i).condition = (ExpressionNode) visit(ctx.expression(i + 1)));

    ieb.ifelses.addAll(lib);

    if (number_of_else > 0) {
      ElseBlock eb = new ElseBlock((Block) visit(ctx.block(ctx.block().size() - 1)));
      ieb.ifelses.add(eb);
    }

    return ieb;
  }

  @Override
  public Node visitWhileBlock(CLParserParser.WhileBlockContext ctx) {
    WhileBlock wb = new WhileBlock((Block) visit(ctx.block()));
    wb.condition = (ExpressionNode) visit(ctx.expression());
    return wb;
  }


  @Override
  public Node visitForBlock(CLParserParser.ForBlockContext ctx) {
    ForBlock fb = new ForBlock((Block) visit(ctx.block()));
    fb.for_id = new Identifier(ctx.IDENTIFIER().getText());
    fb.for_expr = (ExpressionNode) visit(ctx.expression());
    if (ctx.typeType() != null)
      fb.iter_type = ctx.typeType().getText();
    return fb;
  }

  @Override
  public Node visitProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {

    ProcedureDefinition pd = new ProcedureDefinition();
    pd.id = new Identifier(ctx.IDENTIFIER().getText());
    pd.returnType = ctx.typeType().getText();
    pd.parameters = new ParameterList(ctx.parameterList().parameter().stream()
            .map(this::visit).map(s -> (Parameter) s).collect(Collectors.toCollection((ArrayList::new))));

    pd.body = (Block) visit(ctx.procedureBody().block());
    return pd;
  }

  @Override
  public Node visitParens(CLParserParser.ParensContext ctx) {
    return visit(ctx.expression());

  }

  @Override
  public Node visitParameter(CLParserParser.ParameterContext ctx) {

    Parameter p = new Parameter();
    p.type = ctx.typeType().getText();
    p.id = new Identifier(ctx.IDENTIFIER().getText());
    return p;
  }

  @Override
  public Node visitExpr(CLParserParser.ExprContext ctx) {
    return visit(ctx.expression());
  }

  @Override
  public Node visitId(CLParserParser.IdContext ctx) {
    return new Identifier(ctx);
  }

  @Override
  public Node visitMember(CLParserParser.MemberContext ctx) {
    MemberExpression me = new MemberExpression();
    me.object = (ExpressionNode) visit(ctx.expression());
    if (ctx.IDENTIFIER() != null) {
      // member is Identifier
      me.property = new Identifier(ctx.IDENTIFIER().getText());
    } else {
      // member is Cal
      me.property = (ExpressionNode) visit(ctx.procedureCall());
    }
    return me;
  }


  @Override
  public Node visitProcedure(CLParserParser.ProcedureContext ctx) {
    return visit(ctx.procedureCall());
  }

  @Override
  public Node visitProcedureCall(CLParserParser.ProcedureCallContext ctx) {
    CallExpression ce = new CallExpression();
    ce.callee = new FunctionIdentifier(ctx.IDENTIFIER().getText());
    if (ctx.expressionList() != null)
      ce.arguments = ctx.expressionList().expression().stream().map(this::visit).map(e -> (ExpressionNode) e).collect(Collectors.toCollection(ArrayList::new));
    else
      ce.arguments = new ArrayList<>();
    return ce;
  }

  @Override
  public Node visitLambdaParameter(CLParserParser.LambdaParameterContext ctx) {
    Parameter p = new Parameter();
    p.id = new Identifier(ctx.IDENTIFIER().getText());
    p.type = ctx.typeType().getText();

    return p;

  }

  @Override
  public Node visitLambdaParameterList(CLParserParser.LambdaParameterListContext ctx) {
    return new ParameterList(ctx.lambdaParameter().stream().map(p -> (Parameter) visit(p)).collect(Collectors.toCollection(ArrayList::new)));
  }

  @Override
  public Node visitLambda(CLParserParser.LambdaContext ctx) {
    LambdaExpression le = new LambdaExpression();
    le.retType = ctx.typeType().getText();
    le.parameters = (ParameterList) visit(ctx.lambdaParameterList());
    le.body = (Block) visit(ctx.block());
    return le;
  }

  @Override
  public Node visitAddSub(CLParserParser.AddSubContext ctx) {
    ExpressionNode left = visitAndGetExpresionNode(ctx.expression(0));
    ExpressionNode right = visitAndGetExpresionNode(ctx.expression(1));
    return new ArithmeticExpression(left, right, ctx.bop.getText());

  }

  private ExpressionNode visitAndGetExpresionNode(CLParserParser.ExpressionContext ctx) {
    return (ExpressionNode) visit(ctx);
  }

  @Override
  public Node visitMulDivMod(CLParserParser.MulDivModContext ctx) {
    ExpressionNode left = visitAndGetExpresionNode(ctx.expression(0));
    ExpressionNode right = visitAndGetExpresionNode(ctx.expression(1));
//    System.out.println(left.toString() + right);
    return new ArithmeticExpression(left, right, ctx.bop.getText());
  }

  @Override
  public Node visitPartialEqual(CLParserParser.PartialEqualContext ctx) {
    ExpressionNode left = visitAndGetExpresionNode(ctx.expression(0));
    ExpressionNode right = visitAndGetExpresionNode(ctx.expression(1));
    return new LogicExpression(left, right, ctx.bop.getText());
  }

  @Override
  public Node visitCompare(CLParserParser.CompareContext ctx) {
    ExpressionNode left = visitAndGetExpresionNode(ctx.expression(0));
    ExpressionNode right = visitAndGetExpresionNode(ctx.expression(1));
    return new LogicExpression(left, right, ctx.bop.getText());
  }

  @Override
  public Node visitBreak(CLParserParser.BreakContext ctx) {
    return new Break();
  }

  @Override
  public Node visitContinue(CLParserParser.ContinueContext ctx) {
    return new Continue();
  }

  @Override
  public Node visitLogic(CLParserParser.LogicContext ctx) {
    ExpressionNode left = visitAndGetExpresionNode(ctx.expression(0));
    ExpressionNode right = visitAndGetExpresionNode(ctx.expression(1));
    return new LogicExpression(left, right, ctx.bop.getText());
  }

  @Override
  public Node visitProgram(CLParserParser.ProgramContext ctx) {
    Block bl = new Block();
    ctx.statement().forEach(
            (n) -> {
              if (n == null)
                return;
              // must return Node, not `null`
              Node v = visit(n);
              if (v != null)
                bl.statements.add(v);
            }
    );

    Program p = new Program(bl);
//    Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//    System.out.println(g.toJson(p));
    ASTWalker astWalker = new ASTWalker();
    ASTListenerTester astBaseListener = new ASTListenerTester();
    astWalker.walk(astBaseListener, p);
//    printMethodInfo(p);
    return p;

  }


  public static void printMethodInfo(Object o) {
    System.out.println("=================");
    System.out.print("print Method of ");
    Class<?> clazz = o.getClass();
    System.out.println(o.getClass().getName());

    Method[] declaredMethods = clazz.getDeclaredMethods();
    for (int i = 0; i < declaredMethods.length; i++) {
      Method method = declaredMethods[i];
      System.out.print((i + 1) + " ");
      System.out.print(Modifier.toString(method.getModifiers()) + " ");
      System.out.print(method.getReturnType().getSimpleName());
      System.out.print(" " + method.getName());
      System.out.print("(");
      Class<?>[] parameterTypes = method.getParameterTypes();

      for (int j = 0; j < parameterTypes.length; j++) {
        Class<?> parameterType = parameterTypes[j];
        if (j == parameterTypes.length - 1) {
          System.out.print(parameterType.getSimpleName() + " arg" + j);
        } else {
          System.out.print(parameterType.getSimpleName() + " arg" + j + ",");
        }
      }
      System.out.println(");");

    }
    System.out.println("=================");
  }


}
