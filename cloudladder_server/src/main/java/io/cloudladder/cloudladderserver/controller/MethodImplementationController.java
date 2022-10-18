package io.cloudladder.cloudladderserver.controller;

import cloudladder.core.knowledge.implementation.HTTPMethodImplementation;
import cloudladder.core.knowledge.implementation.JarMethodImplementation;
import cloudladder.core.knowledge.implementation.MethodImplementation;
import com.google.gson.Gson;
import io.cloudladder.cloudladderserver.dto.CommonReturn;
import io.cloudladder.cloudladderserver.dto.CreateImplementationBody;
import io.cloudladder.cloudladderserver.mapper.MethodImplementationMapper;
import io.cloudladder.cloudladderserver.model.ModelMethodImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController()
@RequestMapping("/method-implementation")
public class MethodImplementationController {
    @Autowired
    private MethodImplementationMapper methodImplementationMapper;

    @PostMapping("/create-method")
    public CommonReturn createImplementation(@RequestBody CreateImplementationBody body) {
        String scope = body.getScope();
        String name = body.getName();
        String from = body.getFrom();
        String type = body.getType();
        ArrayList<String> paramNames = body.getParamNames();
        boolean catchAll = body.isCatchAll();

        HashMap<String, Object> data = body.getData();
        data.put("paramNames", paramNames);
        data.put("catchAll", catchAll);

        Gson gson = new Gson();
        String dataJson = gson.toJson(data);

        methodImplementationMapper.createMethodImplementation(scope, name, from, type, dataJson);
        return CommonReturn.ok();
    }

    // return as ["jar" | "http", MethodImplementation]
    @GetMapping("/get-method")
    public ArrayList<Object> getImplementation(
            @RequestParam(name = "scope", required = false) String scope,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "from") String from
    ) {
        System.out.println(scope);
        System.out.println(name);
        System.out.println(from);
        ArrayList<Object> ret = new ArrayList<>();
        if (scope == null) {
            ModelMethodImplementation modelMethodImplementation = this.methodImplementationMapper.getMethodImplementationWithoutScope(name, from);
            MethodImplementation impl = modelMethodImplementation.toMethodImplementation();
            String type = modelMethodImplementation.type;
            ret.add(type);
            ret.add(impl);
        } else {
            ModelMethodImplementation modelMethodImplementation = this.methodImplementationMapper.getMethodImplementation(scope, name, from);
            MethodImplementation impl = modelMethodImplementation.toMethodImplementation();
            String type = modelMethodImplementation.type;
            ret.add(type);
            ret.add(impl);
        }
        return ret;

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

//        JarMethodImplementation implementation = new JarMethodImplementation(
//                "alias",
//                false,
//                "http://localhost:8080/package/cloudladder_dynamic_test/1.0-SNAPSHOT",
//                "org.example.DynamicTest",
//                "createNumber"
//        );
//
////        implementation.outputType = "number";
//        ArrayList<Object> ret = new ArrayList<>();
//        ret.add("jar");
//        ret.add(implementation);
//        return ret;
    }
}
