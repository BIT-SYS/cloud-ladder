//package cloudladder.utils;
//
//import cloudladder.core.runtime.env.CLRtEnvironment;
//
//import java.lang.reflect.Method;
//
//public class CLMiscUtils {
//    public static void executeStdFile(String className, String methodName, CLRtEnvironment env) {
//        try {
//            Class clazz = Class.forName(className);
//            Method method = clazz.getMethod(methodName, CLRtEnvironment.class);
//            method.invoke(null, env);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getDefaultPackageName(String path) {
//        String temp = path;
//        if (path.endsWith(".cl")) {
//            temp = path.replaceAll("\\.cl$", "");
//        }
//        String[] sp = temp.split("[/\\.\\\\]");
//        return sp[sp.length - 1];
//    }
//}
