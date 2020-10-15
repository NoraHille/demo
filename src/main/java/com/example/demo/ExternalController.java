package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/external")

@RestController
public class ExternalController {


    @GetMapping("/hello")
    String testMethod() {
        return "Hello";
    }


}
