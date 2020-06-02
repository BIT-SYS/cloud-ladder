package ast;

import grammar.CLParserParser;
import ir.BuildListIR;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

public class RangeListInitializer extends ExpressionNode {
  public ExpressionNode start;
  public ExpressionNode end;
  public boolean exclusiveEnd;

  public RangeListInitializer(ParserRuleContext ctx) {
    this.ctx = ctx;
  }

  @Override
  public ExpressionNode reduce() {
    ExpressionNode _start = start.gen();
    ExpressionNode _end = end.gen();
//    System.out.println(evalType);
    Temp t = new Temp();
    BuildListIR buildListIR = new BuildListIR(t, evalType, _start, _end);
    Utils.setDebugInfo(buildListIR,this);
    ir.emit(buildListIR);
    return t;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return gen();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {{
      add(start);
      add(end);
    }};
  }
}


