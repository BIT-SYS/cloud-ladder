package AST;

import Grammar.*;
import symboltable.SimpleType;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
      // member is AST.Identifier
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
              // must return AST.Node, not `null`
              Node v = visit(n);
              if (v != null)
                bl.statements.add(v);
            }
    );

    Program p = new Program(bl);
//    Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//    System.out.println(g.toJson(p));
    ASTWalker astWalker = new ASTWalker();
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
