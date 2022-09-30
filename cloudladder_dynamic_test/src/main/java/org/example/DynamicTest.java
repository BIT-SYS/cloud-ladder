package org.example;

import cloudladder.core.object.CLNumber;
import cloudladder.core.object.CLObject;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;

@CLStdLibAnnotation(name = "dynamicTest")
public class DynamicTest {
    @CLBuiltinFuncAnnotation(params = {}, name = "dynamicTest")
    public static CLObject createNumber(CLRtFrame frame) {
        return CLNumber.getNumberObject(100);
    }

//    public static String injectClassName() {
//        return "org.example.DynamicTest";
//    }
}
