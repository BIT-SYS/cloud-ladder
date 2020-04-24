package IR;

import java.util.ArrayList;
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
    IRs.add(ir);
  }
}