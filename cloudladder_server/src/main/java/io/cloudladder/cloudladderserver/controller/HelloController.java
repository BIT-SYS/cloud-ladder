package io.cloudladder.cloudladderserver.controller;

import cloudladder.core.knowledge.implementation.MethodImplementation;
import io.cloudladder.cloudladderserver.mapper.MethodImplementationMapper;
import io.cloudladder.cloudladderserver.model.ModelMethodImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    private MethodImplementationMapper methodImplementationMapper;

    @GetMapping("")
    public String helloWorld() {
        return "Hello world";
    }

    @GetMapping("/method-implementation")
    public MethodImplementation helloMethodImplementation(
            @RequestParam(name = "scope", required = false) String scope,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "from") String from) {
//        System.out.println(scope);
//        System.out.println(name);
//        System.out.println(from);
        if (scope == null || scope.isBlank()) {
            ModelMethodImplementation implementation = this.methodImplementationMapper.getMethodImplementationWithoutScope(name, from);
            System.out.println(implementation);
            return implementation.toMethodImplementation();
        } else {
            return this.methodImplementationMapper.getMethodImplementation(scope, name, from).toMethodImplementation();
        }
    }
}
