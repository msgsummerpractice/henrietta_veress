package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Printer {
    
    private HelloService helloService;

    @Autowired
    public Printer(HelloService helloService) {
        this.helloService = helloService;
    }

    public void print() {
        System.out.println(helloService.getMessage());
    }
}
