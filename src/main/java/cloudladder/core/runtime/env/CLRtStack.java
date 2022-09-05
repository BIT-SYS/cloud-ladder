//package cloudladder.core.runtime.env;
//
//import lombok.Getter;
//
//import java.util.ArrayList;
//
//@Getter
//public class CLRtStack {
//    private final ArrayList<CLRtStackFrame> st;
//
//    public CLRtStack() {
//        this.st = new ArrayList<>();
//    }
//
//    public void push(CLRtStackFrame frame) {
//        this.st.add(frame);
//    }
//
//    public CLRtStackFrame top() {
//        return st.get(st.size() - 1);
//    }
//
//    public CLRtStackFrame pop() {
//        CLRtStackFrame temp = st.get(st.size() - 1);
//        st.remove(st.size() - 1);
//
//        return temp;
//    }
//
//    public int size() {
//        return st.size();
//    }
//}
