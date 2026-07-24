package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component  // this creates a bean automatically, so i dont need to use @Bean anymore
public class Greeter {
    
    private IGreetingService greetingService;

    @Autowired
    public Greeter(
        @Qualifier("germanGreetingService")
        IGreetingService greetingService) {
            this.greetingService = greetingService;
        }

    public void greet() {
        System.out.println(greetingService.getMessage());
    }
}
