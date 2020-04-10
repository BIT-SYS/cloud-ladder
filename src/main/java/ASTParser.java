import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
interface Node {
  List<Node> getChildren();
}

class Program implements Node {
  public List<Node> statement;

  Program() {
    statement = new ArrayList<>();
  }

  @Override
  public List<Node> getChildren() {
    return statement;
  }
}

class RangeListInitializer implements ExpressionNode {
  public ExpressionNode start;
  public ExpressionNode end;
  public boolean exclusiveEnd;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(start);
      add(end);
    }};
  }
}

class Assign implements ExpressionNode {
  public Identifier lvalue;
  public ExpressionNode rvalue;

  Assign(String lvalue, ExpressionNode rvalue) {
    this.lvalue = new Identifier(lvalue);
    this.rvalue = rvalue;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(lvalue);
      add(rvalue);
    }};
  }
}

class ValuesListInitializer implements ExpressionNode {
  public List<ExpressionNode> values;

  @Override
  public List<Node> getChildren() {
    return values.stream().map(en -> (Node) en).collect(Collectors.toList());
  }
}

class LambdaExpression implements ExpressionNode {

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

class Break implements Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}

class Continue implements  Node {
  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}

// so what's the difference between Block and Program
class Block implements Node {

  public List<Node> statement;

  Block() {
    statement = new ArrayList<>();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>(statement);
  }
}

class IfBlock extends Block {
  public ExpressionNode condition;
  // may be null
  public Block alternative;

  IfBlock(Block bl) {
    this.statement = bl.statement;
  }

  @Override
  public List<Node> getChildren() {
    List<Node> l = super.getChildren();
    return new ArrayList<Node>() {{
      add(condition);
      addAll(l);
      add(alternative);
    }};
  }
}

class ElifBlock extends Block {
  public ExpressionNode condition;

  ElifBlock(Block bl) {
    this.statement = bl.statement;
  }

  @Override
  public List<Node> getChildren() {
    List<Node> l = super.getChildren();
    return new ArrayList<Node>() {
      {
        add(condition);
        addAll(l);
      }
    };

  }
}
class WhileBlock extends Block {
 public ExpressionNode condition;
 WhileBlock(Block bl) {this.statement = bl.statement;}

  @Override
  public List<Node> getChildren() {
    List<Node> l = super.getChildren();
    return new ArrayList<Node>() {{
      add(condition);
      addAll(l);
    }};
  }
}

class ForBlock extends Block {
  public Identifier for_id;
  public ExpressionNode for_expr;
  public String iter_type;

  ForBlock(Block bl) {
    this.statement = bl.statement;
  }

  @Override
  public List<Node> getChildren() {
    List<Node> l = super.getChildren();
    return new ArrayList<Node>() {{
      add(for_id);
      add(for_expr);
      addAll(l);
    }};
  }
}

class Parameter implements Node {
  public String type;
  public Identifier id;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(id);
    }};
  }
}

class ParameterList implements Node {
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

  IndexExpression(Node left, Node right, String op) {
    super(left, right, op);
  }
}

class ProcedureDefinition implements Node {
  public ParameterList parameters;
  public String returnType;
  // function name
  public Identifier id;
  public Block body;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(parameters);
      add(body);
    }};
  }
}


class VariableDeclaration implements Node {
  public String type;
  public Identifier id;
  public Node expr;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(id);
      add(expr);
    }};
  }
}

interface ExpressionNode extends Node {

}

class CallExpression implements ExpressionNode {
  // identifier or MemberExpression
  public Identifier callee;
  public List<ExpressionNode> arguments;

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(callee);
      addAll(arguments);
    }};
  }
}

class MemberExpression implements ExpressionNode {
  public ExpressionNode object;
  public ExpressionNode property;

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

class Identifier implements ExpressionNode {
  public String name;

  Identifier(CLParserParser.IdContext ctx) {
    name = ctx.getText();
  }

  Identifier(String name) {
    this.name = name;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}

class Literal implements ExpressionNode {

  public String raw;

  Literal() {

  }

  Literal(CLParserParser.IntegerLiteralContext ctx) {
    raw = ctx.DECIMAL_LITERAL().getText();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<>();
  }
}

class BinaryExpression implements ExpressionNode {
  public Node left;

  public String op;
  public Node right;


  BinaryExpression(Node left, Node right, String op) {
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
    vd.expr = visit(ctx.expression());
    vd.id = id;
    return vd;
  }

  @Override
  public Node visitIntegerLiteral(CLParserParser.IntegerLiteralContext ctx) {
    return new Literal(ctx);
  }

  @Override
  public Node visitString(CLParserParser.StringContext ctx) {
    Literal l = new Literal();
    l.raw = ctx.getText();
    return l;
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
    return new IndexExpression(visit(ctx.expression(0)), visit(ctx.expression(1)), "[]");
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
              bl.statement.add(ret);
            }
    );

    return bl;
  }

  @Override
  public Node visitIfBlock(CLParserParser.IfBlockContext ctx) {
    IfBlock ib = new IfBlock((Block) visit(ctx.block(0)));
    ib.condition = (ExpressionNode) visit(ctx.expression(0));
    int number_of_elif = ctx.ELIF().size();
    int number_of_else = ctx.ELSE() == null ? 0 : 1;

    List<ElifBlock> lib = ctx.block().subList(1, 1 + number_of_elif).stream().map(b -> new ElifBlock((Block) visit(b))).collect(
            Collectors.toCollection(ArrayList::new)
    );
    IntStream.range(0, lib.size()).forEach(i -> lib.get(i).condition = (ExpressionNode) visit(ctx.expression(i + 1)));

    if (number_of_else > 0) {
      ib.alternative = (Block) visit(ctx.block(ctx.block().size() - 1));
    }

    return ib;
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
    fb.iter_type = ctx.typeType().getText();
    return fb;
  }

  @Override
  public Node visitProcedureDeclaration(CLParserParser.ProcedureDeclarationContext ctx) {

    ProcedureDefinition pd = new ProcedureDefinition();
    pd.id =new Identifier(ctx.IDENTIFIER().getText());
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
    ce.callee = new Identifier(ctx.IDENTIFIER().getText());
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
    Node left = visit(ctx.expression(0));
    Node right = visit(ctx.expression(1));
    return new BinaryExpression(left, right, ctx.bop.getText());

  }

  @Override
  public Node visitMulDivMod(CLParserParser.MulDivModContext ctx) {
    Node left = visit(ctx.expression(0));
    Node right = visit(ctx.expression(1));
//    System.out.println(left.toString() + right);
    return new BinaryExpression(left, right, ctx.bop.getText());
  }

  @Override
  public Node visitPartialEqual(CLParserParser.PartialEqualContext ctx) {
    Node left = visit(ctx.expression(0));
    Node right = visit(ctx.expression(1));
//    System.out.println("print partial");
//    System.out.println(ctx.bop.getText());
    return new BinaryExpression(left, right, ctx.bop.getText());
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
    Node left = visit(ctx.expression(0));
    Node right = visit(ctx.expression(1));
    return new BinaryExpression(left, right, ctx.bop.getText());
  }

  @Override
  public Node visitProgram(CLParserParser.ProgramContext ctx) {
    Program p = new Program();
    ctx.statement().forEach(
            (n) -> {
              if (n == null)
                return;
              // must return Node, not `null`
              Node v = visit(n);
              if (v != null)
                p.statement.add(v);
            }
    );

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
