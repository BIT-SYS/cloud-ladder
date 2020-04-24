import symboltable.Scope;
import symboltable.SimpleType;
import symboltable.Symbol;
import symboltable.Type;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
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
    return "t" + Integer.toString(index);
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

  //
  int newLabel() {
    return ++Label.label;
  }

  void emitLabel(int i) {
    System.out.print("L" + i + ":");
  }

  void emit(String s) {
    System.out.println("\t" + s);
  }

  public String gen(int before, int after) {
    System.out.println(this.getClass().getName());
    return "";
  }

  abstract List<Node> getChildren();
}

class ScopePointer {
}

class Program extends Node {
  public Block statements;

  Program(Block bl) {
    statements = bl;
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
    return statements.getChildren();
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
    emit(t.toString() + " = [" + start.toString() + "," + end.toString() + "]");
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
    return new ArrayList<Node>() {{
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
    emit(lvalue.gen() + " = " + rvalue.gen());
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
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
  public List<Node> getChildren() {
    return new ArrayList<Node>() {
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
    before = newLabel();
    after = newLabel();
    emitLabel(before);
    for (int i = 0; i < statements.size(); i++) {
      statements.get(i).gen(before, after);
      if (i != statements.size() - 1) {
        before = newLabel();
      }
    }
    emitLabel(after);
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
    for (Node n : ifelses) {
      after = newLabel();
      n.gen(0, after);
      emitLabel(after);
    }
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(ifelses);
  }
}

class IfBlock extends Node {
  public ExpressionNode condition;
  // may be null
  public Block statements;

  IfBlock(Block bl) {
    this.statements = bl;
  }


  @Override
  public String gen(int before, int after) {
    int label = newLabel();
    String conditionLabel = condition.gen(before, label);
    emitLabel(label);
    int blockAfter = newLabel();
    emit("ifnot " + conditionLabel + " goto L" + blockAfter);
    statements.gen(label, after);
    emit("goto L" + after);
    emitLabel(blockAfter);
    return "";
  }

  @Override
  public List<Node> getChildren() {
    List<Node> l = statements.getChildren();
    return new ArrayList<Node>() {{
      add(condition);
      addAll(l);
    }};
  }
}

class ElifBlock extends Node {
  public ExpressionNode condition;
  public Block statements;

  ElifBlock(Block bl) {
    this.statements = bl;
  }

  @Override
  public String gen(int before, int after) {
    int label = newLabel();
    String conditionLabel = condition.gen(before, label);
    emitLabel(label);
    int blockAfter = newLabel();
    emit("ifnot " + conditionLabel + " goto L" + blockAfter);
    statements.gen(label, after);
    emit("goto L" + after);
    emitLabel(blockAfter);
    return "";
  }

  @Override
  public List<Node> getChildren() {
    List<Node> l = statements.getChildren();
    return new ArrayList<Node>() {
      {
        add(condition);
        addAll(l);
      }
    };

  }
}

class ElseBlock extends Node {
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
    return statements.getChildren();
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

    emitLabel(before);

    emit("iffalse " + condition.gen(0, 0) + " goto L" + after);
    statements.gen(before, after);

    emitLabel(after);
    return "";

  }

  @Override
  public List<Node> getChildren() {
    List<Node> l = statements.getChildren();
    return new ArrayList<Node>() {{
      add(condition);
      addAll(l);
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
    emit(len.toString() + " = call " + expr + ".size");
    emit(t.toString() + " = 0");
    emitLabel(before);
    // if false then jump
    emit(for_id.toString() + " = " + expr + ".get " + t.toString());
    emit("iffalse " + t.toString() + " < " + len.toString() + " goto L" + after);
    // execuete
    statements.gen(before, after);


    // +1 and jump
    emit(t.toString() + " = " + t.toString() + " + 1");
    emit("goto L"+ before);
    emitLabel(after);
    return "";
  }

  @Override
  public List<Node> getChildren() {
    List<Node> l = statements.getChildren();
    return new ArrayList<Node>() {{
      add(for_id);
      add(for_expr);
      addAll(l);
    }};
  }
}

class Parameter extends Node {
  public String type;
  public Identifier id;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(id);
    }};
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
    emit(t + " = call " + l.toString() + ".get " + r.toString());
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
    emit("Define " + id.toString());

    body.gen(0, 0);

    emit("EndDefine " + id.toString());
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
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
    emit(id.gen() + " = " + expr.gen());
    return "";
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
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

  public void jumping(int i, int after) {
    System.out.println("jump");
  }
}

class CallExpression extends ExpressionNode {
  // identifier or MemberExpression
  public FunctionIdentifier callee;
  public List<ExpressionNode> arguments;


  @Override
  public ExpressionNode reduce() {

    List<ExpressionNode> args = arguments.stream().map(ExpressionNode::reduce).collect(Collectors.toCollection(ArrayList::new));
    Temp t = new Temp();
    String args_str = String.join(
            " ",
            args.stream().map(ExpressionNode::toString).collect(Collectors.toCollection((ArrayList::new)))
    );
    emit(t.toString() + " = call " + callee + " " + args_str);
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
    return new ArrayList<Node>() {{
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
    emit(temp.toString() + " = call " + object_reduced.toString() + "." + property.toString());
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
    return new ArrayList<Node>() {
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
    emit(t.toString() + " = " + reducedExpr.toString());
    return t;
  }

  BinaryExpression(ExpressionNode left, ExpressionNode right, String op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
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
