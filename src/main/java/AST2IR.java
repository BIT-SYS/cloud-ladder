import java.util.HashMap;
import java.util.List;

enum IROperator {
  Assign,

}

interface IRNode {

}

class TripleNode implements IRNode {
  public IROperator op;
  public String arg1;
  public String arg2;
}

class QuadrupleNode implements IRNode {
  public IROperator op;
  public String arg1;
  public String arg2;
  public String result;

  QuadrupleNode() {
  }

  QuadrupleNode(IROperator op, String arg1, String arg2, String result) {
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.result = result;
  }
}

public class AST2IR extends ASTBaseListener {
  public List<IRNode> iR;
  public HashMap<Node, IRNode> indexMap;

  @Override
  public void enterAssign(Assign node) {
//    iR.add(new QuadrupleNode(IROperator.Assign, node.rvalue, null, node.lvalue.name ));

  }
}
