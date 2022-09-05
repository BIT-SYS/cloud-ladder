//package cloudladder.core.runtime.data;
//
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import cloudladder.core.runtime.env.CLRtScope;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//
//public abstract class CLFunction extends CLData {
//    @Getter
//    private final ArrayList<String> params;
//
//    @Getter
//    @Setter
//    private String name;
//
//    @Setter
//    @Getter
//    private CLRtScope parentScope = null;
//
//    public CLFunction() {
//        params = new ArrayList<>();
//    }
//
//    public void addParam(String param) {
//        params.add(param);
//    }
//
//    public abstract void execute(CLRtEnvironment env);
//
//    @Override
//    public String toString() {
//        return "f[" + this.name + "]";
//    }
//}
