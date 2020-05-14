package ast;

import ir.Value;
import ir.VariableDeclarationIR;
import org.antlr.v4.runtime.ParserRuleContext;
import symboltable.Symbol;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclaration extends Node {
  public String type;
  public Identifier id;
  public ExpressionNode expr;

  VariableDeclaration(ParserRuleContext ctx) {
    super(ctx);
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    // ! ERROR: warning: TODO
//    Symbol s = scope.resolve(id.toString());
    // code below print: ===>< null null
//    System.out.println(String.format("===>< %s %s\n", evalType, symbol));
    VariableDeclarationIR vdir = new VariableDeclarationIR(new Value(id.gen()),new Value(expr.gen()));
    Utils.setDebugInfo(vdir,this);
    ir.emit(vdir);
    return null;
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(id);
      add(expr);
    }};
  }
}
