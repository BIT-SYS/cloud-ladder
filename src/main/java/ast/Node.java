package ast;

import ir.IR;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import symboltable.Scope;
import symboltable.Symbol;
import symboltable.Type;

import java.util.List;


interface DebugInfo {
  public String getSourceCode();
  public Integer getLineNumber();
}

interface GenerateIR {
  public int newLabel();
  public ExpressionNode gen(int before, int after);
}

interface Listener {
  List<Node> getChildren();
}

public abstract class Node implements  GenerateIR,Listener, DebugInfo {
  // AST.ScopePointer
  public Scope scope;
  public Symbol symbol;
  public Type evalType;
  public ParserRuleContext ctx;
  static public IR ir;

  public int newLabel() {
    return ++Label.label;
  }

  Node(){};
  Node(ParserRuleContext ctx) {
    this.ctx = ctx;
  }

  public ExpressionNode gen(int before, int after) {
    System.out.println(this.getClass().getName());
    return null;
  }

  @Override
  public String getSourceCode() {
    int a = ctx.start.getStartIndex();
    int b = ctx.stop.getStopIndex();
    CharStream cs = ctx.start.getInputStream();
    return cs.getText(new Interval(a, b));
  }

  public String getSourceCodeLastLine() {
    String code = getSourceCode();
    code = code.substring(code.lastIndexOf('\n')).replace("\n","");
    return code;
  }
  public String getSourceCodeFirstLine() {
    String code = getSourceCode();
    code = code.substring(0, code.indexOf('\n')).replace("\n","");
    return code;
  }


  @Override
  public Integer getLineNumber() {
    return ctx.start.getLine();
  }
}
