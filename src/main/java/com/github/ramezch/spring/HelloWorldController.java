package com.github.ramezch.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class HelloWorldController {
    @GetMapping("hello")
    public String getHello() {
        return "Hello, World!";
    }
    @GetMapping("hello/{name}")
    public String getHello(@PathVariable String name) {
        return "Hello, " + name;
    }
}
