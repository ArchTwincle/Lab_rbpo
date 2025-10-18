package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "Greeting from GreetingController: Hello, " + name + "!";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to our Spring Boot application!";
    }
}