package io.cloudladder.cloudladderserver.controller;

import cloudladder.core.knowledge.implementation.HTTPMethodImplementation;
import cloudladder.core.knowledge.implementation.JarMethodImplementation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController()
@RequestMapping("/method-implementation")
public class MethodImplementationController {
    @GetMapping("/get-method")
    public ArrayList<Object> getImplementation() {
//        HTTPMethodImplementation implementation = new HTTPMethodImplementation("hello", false);
//        implementation.method = "GET";
//        implementation.argConverter = null;
//        implementation.url = "http://localhost:8080/hello";
//        implementation.bodyComposer = "bodyComposer";
//
//        ArrayList<Object> ret = new ArrayList<>();
//        ret.add("http");
//        ret.add(implementation);
//        return ret;

        JarMethodImplementation implementation = new JarMethodImplementation(
                "alias",
                false,
                "http://localhost:8080/package/cloudladder_dynamic_test/1.0-SNAPSHOT",
                "org.example.DynamicTest",
                "createNumber"
        );

//        implementation.outputType = "number";
        ArrayList<Object> ret = new ArrayList<>();
        ret.add("jar");
        ret.add(implementation);
        return ret;
    }
}
