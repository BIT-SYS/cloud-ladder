import java.util.HashMap;
import java.util.List;


interface IRInstruction {

}

class TripleInstruction implements IRInstruction {
  public String op;
  public String arg1;
  public String arg2;
}

class QuadrupleInstruction implements IRInstruction {
  public String op;
  public String arg1;
  public String arg2;
  public String result;
}

public class AST2IR extends ASTBaseListener {
  public List<IRInstruction> iR;
  public HashMap<Node, IRInstruction> indexMap;


}
