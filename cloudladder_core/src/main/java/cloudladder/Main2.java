package cloudladder;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.knowledge.RelationShip;
import cloudladder.core.knowledge.implementation.MethodImplementation;
import cloudladder.core.knowledge.implementation.MethodImplementationUtil;
import cloudladder.core.object.*;
import cloudladder.core.vm.CLVM;
import cloudladder.std.Loader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) throws ClassNotFoundException, CLRuntimeError {
//        URL file = Main2.class.getClassLoader().getResource("cloudladder_dynamic_test-1.0-SNAPSHOT.jar");
//        System.out.println(file);
//        URLClassLoader classLoader = new URLClassLoader(
//                new URL[] {file},
//                Main2.class.getClassLoader()
//        );
//        System.out.println(classLoader);
//
//        Class c = Class.forName("org.example.DynamicTest", true, classLoader);
//
//        CLDict dict = Loader.loadClass(c);
//        System.out.println(dict);

        String serverBase = "http://localhost:8080";
        MethodImplementation implementation = MethodImplementationUtil.getImplementation(serverBase);
        CLHTTPFunction function = (CLHTTPFunction) implementation.buildFunction();
        System.out.println(function);
    }
}
