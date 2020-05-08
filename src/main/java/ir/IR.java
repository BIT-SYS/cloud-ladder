package ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class IR {
  public List<IRNode> IRs;
  List<Label> tempLabelList;
  public HashMap<String, Label> labelMap;

  public IR(){
    IRs = new ArrayList<>();
    tempLabelList = new ArrayList<>();
    labelMap = new HashMap<>();
  }

  // get unique Label
  public Label getLabel(int i) {
    String si = Integer.toString(i);
    if (labelMap.containsKey(si)){
      return labelMap.get(si);
    } else {
      Label l = new Label(i);
      labelMap.put(si, l);
      return l;
    }

  }

  public void emitLabel(int i) {
    String si = Integer.toString(i);
    if ( labelMap.containsKey(si)) {
      // already has this label
      tempLabelList.add(labelMap.get(si));
    } else {
      Label l = new Label(i);
      labelMap.put(Integer.toString(i), l);
      tempLabelList.add(l);
    }
  }

  public void emit(IRNode ir) {
    // 建立双向引用
    ir.labels = tempLabelList;
    ir.labels.forEach(l -> l.iRNode = ir);
    tempLabelList = new ArrayList<>();
    if (IRs.size() > 0) {
      IRNode last_ir  = IRs.get(IRs.size()-1);
      last_ir.setNext(ir);
    }
    IRs.add(ir);
  }

  public IRNodeInterface getFirst() {
    return IRs.get(0);
  }

  @Override
  public String toString() {

    StringBuilder ret = new StringBuilder(String.format("Operation Count: %d, Labels Count: %d\n", IRs.size(), labelMap.size()));
    int indentLevel = 0;
    for (int i = 0; i < IRs.size(); i++) {
      IRNode ir = IRs.get(i);

      switch (ir.getOp()){
        case LazyExecutionStart:
          indentLevel +=1;
          break;
        default:
      }
      String leading_spaces = String.join("", Collections.nCopies(indentLevel, "  "));
      ret.append(leading_spaces);
      ret.append(ir.toString());
      ret.append("\n");

      switch (ir.getOp()){
        case LazyExecutionEnd:
          indentLevel -=1;
          break;
        default:
      }
    }
    return ret.toString();
  }
}