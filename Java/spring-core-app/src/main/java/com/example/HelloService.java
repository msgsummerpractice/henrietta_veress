package com.example;

import org.springframework.stereotype.Component;

@Component
public class HelloService {
    
    public String getMessage () {
        return "Hello, Spring!";
    }
}
